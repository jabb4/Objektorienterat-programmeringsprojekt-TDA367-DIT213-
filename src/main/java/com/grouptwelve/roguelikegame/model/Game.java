package com.grouptwelve.roguelikegame.model;

import com.grouptwelve.roguelikegame.model.combat.CombatManager;
import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.constraints.BoundsConstraint;
import com.grouptwelve.roguelikegame.model.constraints.ConstraintSystem;
import com.grouptwelve.roguelikegame.model.effects.EffectInterface;
import com.grouptwelve.roguelikegame.model.entities.*;
import com.grouptwelve.roguelikegame.model.entities.enemies.Enemy;
import com.grouptwelve.roguelikegame.model.entities.enemies.EnemyPool;
import com.grouptwelve.roguelikegame.model.entities.enemies.Enemies;
import com.grouptwelve.roguelikegame.model.events.LevelUpListener;
import com.grouptwelve.roguelikegame.model.events.input.AttackEvent;
import com.grouptwelve.roguelikegame.model.events.input.GameEventListener;
import com.grouptwelve.roguelikegame.model.events.input.MovementEvent;
import com.grouptwelve.roguelikegame.model.events.output.AttackListener;
import com.grouptwelve.roguelikegame.model.events.output.GameEventPublisher;
import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;
import com.grouptwelve.roguelikegame.model.upgrades.logic.UpgradeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Core game model containing all game state and logic.
 *
 * Implements AttackListener to handle combat resolution when entities attack.
 * This follows the Observer pattern - entities notify Game when they attack,
 * and Game handles the combat logic.
 */
public class Game implements GameEventListener, AttackListener, LevelUpListener {

    private final Random rand = new Random();

    private double gameTime;
    private final GameWorld world;
    private final ConstraintSystem constraintSystem;
    private final Player player;
    private final List<Enemy> enemiesAlive;
    private final CombatManager combatManager;
    private final GameEventPublisher eventPublisher;
    private UpgradeInterface[] upgrades;

    // Enemy spawning
    private int lastEnemySpawnTime = 0;
    private final int enemyBaseSpawnRate = 5;
    private final int enemyBaseMaxSpawnAmount = 3;
    private int enemySpawnRate = enemyBaseSpawnRate;
    private int enemyMaxSpawnAmount = enemyBaseMaxSpawnAmount;
    private final int enemySpawnRateMaxTime = 300; // The time (in seconds) the game has run until enemies spawns every second

    /**
     * Creates a new Game instance with an event publisher.
     *
     * @param eventPublisher Publisher for game events (visual feedback, etc.)
     */
    public Game(GameEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.upgrades = new UpgradeInterface[3];

        // Initialize world and constraint system
        this.world = new GameWorld(1280, 720);
        this.constraintSystem = new ConstraintSystem();
        this.constraintSystem.addConstraint(new BoundsConstraint(world));

        // Initialize game state
        this.gameTime = 0;
        this.player = new Player(world.getWidth() / 2, world.getHeight() / 2);


        // Initialize combat system
        this.enemiesAlive = new ArrayList<>();
        this.combatManager = new CombatManager(player, () -> enemiesAlive, eventPublisher);

        // Set up player to notify this Game when attacking
        player.setAttackListener(this);
        player.setLevelUpListener(this);
    }

    // ==================== AttackListener Implementation ====================

    /**
     * Called when any entity (player or enemy) performs an attack.
     * Resolves combat and publishes visual events.
     */
    @Override
    public void onEntityAttacked(Entity attacker, double x, double y, double range, CombatResult result, List<EffectInterface> effects) {
        // Determine if attacker is friendly (player) or enemy
        boolean isFriendly = attacker instanceof Player;

        // Get the attacker's knockback strength
        double knockbackStrength = attacker.getWeapon().getKnockbackStrength();

        // Resolve combat through the combat manager
        combatManager.attack(isFriendly, x, y, range, result, effects, knockbackStrength);

        // Publish attack visual event for the view
        if (eventPublisher != null) {
            eventPublisher.onAttackVisual(x, y, range);
        }
    }

    /**
     * called by only player when level up. publishes event
     * @param level of player
     */
    @Override
    public void onLevelUp(int level)
    {
        for(int i = 0; i < upgrades.length; i++)
        {
            upgrades[i] = UpgradeRegistry.randomUpgrade();
        }
        eventPublisher.onPlayerLevelUp(level, upgrades);
    }
    // ==================== GameEventListener Implementation ====================

    @Override
    public void onChooseBuff(int level) {
        upgrades[level].apply(player);
        System.out.println(upgrades[level].toString() +" was choosen" );
    }

    @Override
    public void onMovement(MovementEvent event) {
        setPlayerMovement(event.getDx(), event.getDy());
    }

    @Override
    public void onAttack(AttackEvent event) {
        // Player attacks - entity handles combat via AttackListener callback
        player.attack();
    }

    // ==================== Game Logic ====================

    /**
     * Updates the game state by one frame.
     * 
     * @param deltaTime Time elapsed since last update (in seconds)
     */
    public void update(double deltaTime) {
        gameTime += deltaTime;

        player.update(deltaTime);
        updateEnemies(deltaTime);

        // Apply world constraints (bounds, etc.)
        constraintSystem.apply(player);
        constraintSystem.applyAll(enemiesAlive);

        spawnEnemies();
    }

    /**
     * Updates all enemies.
     * Attacks are handled via AttackListener callbacks when enemies attack.
     */
    private void updateEnemies(double deltaTime) {
        double playerX = player.getX();
        double playerY = player.getY();

        for (Enemy enemy : enemiesAlive) {
            enemy.velocityAlgorithm(playerX, playerY, enemiesAlive);
            enemy.update(deltaTime);
            // Enemy attack() is called by enemy's internal state machine
            // When it attacks, onEntityAttacked() is called automatically
        }
    }

    /**
     * Spawns enemies periodically based on game time.
     * Increases enemy spawn rate and spawn max amount when the game is run for longer in order to make the game more difficult the longer you play.
     * When this.gametime has reached this.enemySpawnRateMaxTime enemies will spawn every second
     * Every 60 seconds up until this.gametime has reached this.enemySpawnRateMaxTime, one more (this.enemyBaseMaxSpawnAmount + 1) enemy will be able to spawn at the same time.
     * After this.gametime has reached this.enemySpawnRateMaxTime the amount of enemies that can spawn at the same time will increase with plus two for every second.
     */
    private void spawnEnemies() {
        if ((int) this.gameTime != this.lastEnemySpawnTime && ((int) this.gameTime - this.lastEnemySpawnTime >= this.enemySpawnRate || this.lastEnemySpawnTime == 0)) {
            for (int i = 0; i <= rand.nextInt(this.enemyMaxSpawnAmount); i++) {
                int margin = 20;
                int spawnX;
                int spawnY;
                if (rand.nextBoolean()){
                    if (rand.nextBoolean()){
                        spawnY = (int) (world.getHeight() - margin);
                    } else spawnY = margin;
                    spawnX = margin + rand.nextInt((int) world.getWidth() - 2 * margin);
                }
                else {
                    if (rand.nextBoolean()){
                        spawnX = (int) (world.getWidth() - margin);
                    } else spawnX = margin;
                    spawnY = margin + rand.nextInt((int) world.getHeight() - 2 * margin);
                }


                Enemy enemy = EnemyPool.getInstance().borrowRandomEnemy(spawnX, spawnY);
                enemy.setAttackListener(this);  // Register this Game as the attack listener
                enemiesAlive.add(enemy);
            }
            this.lastEnemySpawnTime = (int) this.gameTime;
            this.enemySpawnRate = Math.max(1, Math.min(this.enemyBaseSpawnRate, this.enemyBaseSpawnRate - (int) (4.0 * (int) this.gameTime / this.enemySpawnRateMaxTime)));
            if ((int) this.gameTime <= this.enemySpawnRateMaxTime){
                this.enemyMaxSpawnAmount = (int) (this.enemyBaseMaxSpawnAmount + this.gameTime / 60);
            }
            else {
                this.enemyMaxSpawnAmount = (int) (this.enemyBaseMaxSpawnAmount + (double) this.enemySpawnRateMaxTime / 60  + ((this.gameTime - this.enemySpawnRateMaxTime) / 60)*2);
            }
        }
    }

    /**
     * Sets player movement direction based on input.
     *
     * @param dx Horizontal direction (-1, 0, or 1)
     * @param dy Vertical direction (-1, 0, or 1)
     */
    public void setPlayerMovement(int dx, int dy) {
        player.setMovementDirection(dx, dy);
    }

    // ==================== Getters ====================

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemiesAlive;
    }

    public double getGameTime() {
        return gameTime;
    }
}
