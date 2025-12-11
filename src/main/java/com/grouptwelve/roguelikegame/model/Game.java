package com.grouptwelve.roguelikegame.model;

<<<<<<< HEAD
import com.grouptwelve.roguelikegame.model.EntitiesPackage.*;
import com.grouptwelve.roguelikegame.model.EventsPackage.AttackEvent;
import com.grouptwelve.roguelikegame.model.EventsPackage.EnemyDeathEvent;
import com.grouptwelve.roguelikegame.model.EventsPackage.GameEventListener;
import com.grouptwelve.roguelikegame.model.EventsPackage.MovementEvent;
import com.grouptwelve.roguelikegame.model.WeaponsPackage.CombatManager;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies.EnemyPool;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies.Goblin;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies.Troll;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
=======
import com.grouptwelve.roguelikegame.model.combat.CombatManager;
import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.constraints.BoundsConstraint;
import com.grouptwelve.roguelikegame.model.constraints.ConstraintSystem;
import com.grouptwelve.roguelikegame.model.effects.EffectInterface;
import com.grouptwelve.roguelikegame.model.entities.*;
import com.grouptwelve.roguelikegame.model.entities.enemies.Enemy;
import com.grouptwelve.roguelikegame.model.entities.enemies.EnemyPool;
import com.grouptwelve.roguelikegame.model.events.LevelUpListener;
import com.grouptwelve.roguelikegame.model.events.input.AttackEvent;
import com.grouptwelve.roguelikegame.model.events.input.GameEventListener;
import com.grouptwelve.roguelikegame.model.events.input.MovementEvent;
import com.grouptwelve.roguelikegame.model.events.output.AttackListener;
import com.grouptwelve.roguelikegame.model.events.output.GameEventPublisher;
import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;
import com.grouptwelve.roguelikegame.model.upgrades.logic.UpgradeRegistry;
>>>>>>> 3e28f0b68b466e2b8b11b7c8469cc8e5680e4fef

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

    private final GameWorld world;
    private final ConstraintSystem constraintSystem;
    private final Player player;
    private final List<Enemy> enemiesAlive;
    private final CombatManager combatManager;
    private final GameEventPublisher eventPublisher;

    private double gameTime;
    private int lastEnemySpawnTime = 0;
    private final Random rand = new Random();
    private final int enemyBaseSpawnRate = 5;
    private final int enemyMaxSpawnAmount = 3;
    private UpgradeInterface[] upgrades;

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
        LoadEntities.load();
<<<<<<< HEAD
        this.player = (Player) EntityFactory.getInstance().createEntity(Entities.PLAYER, 400, 300);
        Goblin testGob = (Goblin) EntityFactory.getInstance().createEntity(Entities.GOBLIN, 700, 100);
        Troll testTroll = (Troll) EntityFactory.getInstance().createEntity(Entities.TROLL, 100, 100);
        this.enemiesAlive = new ArrayList<>(List.of((Enemy) testGob, (Enemy) testTroll));
        // CombatManager.getInstance().addEnemy(testGob);
        // CombatManager.getInstance().addEnemy(testTroll);
=======
        this.player = (Player) EntityFactory.getInstance().createEntity(Entities.PLAYER, world.getWidth() / 2, world.getHeight() / 2);
        this.enemiesAlive = new ArrayList<>();
        this.enemiesAlive.add(EnemyPool.getInstance().borrowEnemy(Entities.GOBLIN, 10,20));
>>>>>>> 3e28f0b68b466e2b8b11b7c8469cc8e5680e4fef
        this.gameTime = 0;

        // Initialize combat system
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
     */
    private void spawnEnemies() {
        if ((int) gameTime != lastEnemySpawnTime && (int) gameTime % enemyBaseSpawnRate == 0) {
            for (int i = 0; i <= rand.nextInt(enemyMaxSpawnAmount); i++) {
                int margin = 20;
                int spawnX = margin + rand.nextInt((int) world.getWidth() - 2 * margin);
                int spawnY = margin + rand.nextInt((int) world.getHeight() - 2 * margin);
                Enemy enemy = EnemyPool.getInstance().borrowRandomEnemy(spawnX, spawnY);
                enemy.setAttackListener(this);  // Register this Game as the attack listener
                enemiesAlive.add(enemy);
            }
            lastEnemySpawnTime = (int) gameTime;
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
        player.getVelocity().reset();
    }

    public void reset() {
        this.gameTime = 0;
        this.lastEnemySpawnTime = 0;

        // Reset the player
        resetPlayer();

        // Clear enemies
        enemiesAlive.clear();

        // Spawn initial test enemies again
        Goblin testGob = (Goblin) EntityFactory.getInstance().createEntity(Entities.GOBLIN, 700, 100);
        testGob.setHpBar(new Rectangle(200, 5, Color.RED));
        Troll testTroll = (Troll) EntityFactory.getInstance().createEntity(Entities.TROLL, 100, 100);
        testTroll.setHpBar(new Rectangle(200, 5, Color.RED));
        enemiesAlive.add(testGob);
        enemiesAlive.add(testTroll);
    }
}
