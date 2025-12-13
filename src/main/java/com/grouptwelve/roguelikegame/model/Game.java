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
    private final UpgradeInterface[] upgrades;

    // World dimensions
    private static final int WORLD_WIDTH = 1280;
    private static final int WORLD_HEIGHT = 720;

    // Spawning configuration
    private static final int ENEMY_BASE_SPAWN_RATE = 5;
    private static final int ENEMY_BASE_MAX_SPAWN_AMOUNT = 3;
    private static final int ENEMY_SPAWN_RATE_MAX_TIME = 300;
    private static final int SPAWN_MARGIN = 20;
    private static final double SPAWN_RATE_SCALING_FACTOR = 4.0;
    private static final int SPAWN_AMOUNT_INCREASE_INTERVAL = 60;
    private static final int LATE_GAME_SPAWN_MULTIPLIER = 2;

    // Enemy spawning state
    private int lastEnemySpawnTime = 0;
    private int enemySpawnRate = ENEMY_BASE_SPAWN_RATE;
    private int enemyMaxSpawnAmount = ENEMY_BASE_MAX_SPAWN_AMOUNT;

    /**
     * Creates a new Game instance with an event publisher.
     *
     * @param eventPublisher Publisher for game events (visual feedback, etc.)
     */
    public Game(GameEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.upgrades = new UpgradeInterface[3];

        // Initialize world and constraint system
        this.world = new GameWorld(WORLD_WIDTH, WORLD_HEIGHT);
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
     * check if enemy is alive and then handles that evnt otherwise it enemhy should move towards player
     */
    private void updateEnemies(double deltaTime) {
        double playerX = player.getX();
        double playerY = player.getY();


        for (Enemy enemy : enemiesAlive) {
            if(!enemy.getAliveStatus())
            {
                player.gainXP(enemy.getXpValue());
                eventPublisher.onEnemyDeath(enemy.getX(), enemy.getY(), enemy.getXpValue());
                EnemyPool.getInstance().returnEnemy(enemy);
                enemiesAlive.remove(enemy);
            }
            else
            {
                enemy.velocityAlgorithm(playerX, playerY, enemiesAlive);
                enemy.update(deltaTime);
            }

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
                int spawnX;
                int spawnY;
                if (rand.nextBoolean()) {
                    if (rand.nextBoolean()) {
                        spawnY = (int) (world.getHeight() - SPAWN_MARGIN);
                    } else {
                        spawnY = SPAWN_MARGIN;
                    }
                    spawnX = SPAWN_MARGIN + rand.nextInt((int) world.getWidth() - 2 * SPAWN_MARGIN);
                } else {
                    if (rand.nextBoolean()) {
                        spawnX = (int) (world.getWidth() - SPAWN_MARGIN);
                    } else {
                        spawnX = SPAWN_MARGIN;
                    }
                    spawnY = SPAWN_MARGIN + rand.nextInt((int) world.getHeight() - 2 * SPAWN_MARGIN);
                }

                Enemy enemy = EnemyPool.getInstance().borrowRandomEnemy(spawnX, spawnY);
                enemy.setAttackListener(this);  // Register this Game as the attack listener
                enemiesAlive.add(enemy);
            }
            this.lastEnemySpawnTime = (int) this.gameTime;
            this.enemySpawnRate = Math.max(1, Math.min(ENEMY_BASE_SPAWN_RATE, ENEMY_BASE_SPAWN_RATE - (int) (SPAWN_RATE_SCALING_FACTOR * (int) this.gameTime / ENEMY_SPAWN_RATE_MAX_TIME)));
            if ((int) this.gameTime <= ENEMY_SPAWN_RATE_MAX_TIME) {
                this.enemyMaxSpawnAmount = (int) (ENEMY_BASE_MAX_SPAWN_AMOUNT + this.gameTime / SPAWN_AMOUNT_INCREASE_INTERVAL);
            } else {
                this.enemyMaxSpawnAmount = (int) (ENEMY_BASE_MAX_SPAWN_AMOUNT + (double) ENEMY_SPAWN_RATE_MAX_TIME / SPAWN_AMOUNT_INCREASE_INTERVAL + ((this.gameTime - ENEMY_SPAWN_RATE_MAX_TIME) / SPAWN_AMOUNT_INCREASE_INTERVAL) * LATE_GAME_SPAWN_MULTIPLIER);
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

    public void resetPlayer() {
        player.revive();
        player.setX(400);
        player.setY(300);
        // player.getVelocity().reset();
    }

    public void reset() {
        this.gameTime = 0;
        this.lastEnemySpawnTime = 0;

        // Reset the player
        resetPlayer();

        // Clear enemies
        enemiesAlive.clear();

        this.enemiesAlive.add(EnemyPool.getInstance().borrowEnemy(Enemies.GOBLIN, 10,20));
        this.gameTime = 0;
    }
}
