package com.grouptwelve.roguelikegame.model;

import com.grouptwelve.roguelikegame.model.combat.CombatManager;
import com.grouptwelve.roguelikegame.model.constraints.BoundsConstraint;
import com.grouptwelve.roguelikegame.model.constraints.ConstraintSystem;
import com.grouptwelve.roguelikegame.model.entities.*;
import com.grouptwelve.roguelikegame.model.entities.enemies.Enemy;
import com.grouptwelve.roguelikegame.model.entities.enemies.EnemyPool;
import com.grouptwelve.roguelikegame.model.events.output.events.EntityDeathEvent;
import com.grouptwelve.roguelikegame.model.events.output.events.EntityHitEvent;
import com.grouptwelve.roguelikegame.model.events.output.listeners.EntityHitListener;
import com.grouptwelve.roguelikegame.model.statistics.GameStatistics;
import com.grouptwelve.roguelikegame.model.events.output.events.UpgradeEvent;
import com.grouptwelve.roguelikegame.model.events.output.events.XpChangeEvent;
import com.grouptwelve.roguelikegame.model.events.output.publishers.ChooseBuffPublisher;
import com.grouptwelve.roguelikegame.model.events.output.publishers.EntityPublisher;
import com.grouptwelve.roguelikegame.model.events.input.GameEventListener;
import com.grouptwelve.roguelikegame.model.events.input.MovementEvent;
import com.grouptwelve.roguelikegame.model.events.output.publishers.LevelUpPublisher;
import com.grouptwelve.roguelikegame.model.events.output.listeners.EntityDeathListener;
import com.grouptwelve.roguelikegame.model.events.output.listeners.LevelUpListener;
import com.grouptwelve.roguelikegame.model.events.output.publishers.XpPublisher;
import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;
import com.grouptwelve.roguelikegame.model.upgrades.logic.UpgradeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Core game model containing all game state and logic.
 */
public class Game implements GameEventListener, LevelUpListener, EntityDeathListener, EntityHitListener, GameDrawInfo {

    private final Random rand = new Random();

    private double gameTime;
    private final GameWorld world;
    private final ConstraintSystem constraintSystem;
    private final Player player;
    private final List<Enemy> enemiesAlive;
    private final CombatManager combatManager;
    private final EntityPublisher entityPublisher;
    private final XpPublisher xpPublisher;
    private final ChooseBuffPublisher chooseBuffPublisher;
    private final LevelUpPublisher levelUpPublisher;
    private final UpgradeInterface[] upgrades =  new UpgradeInterface[3];
    private final GameStatistics statistics = new GameStatistics();

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
     * constructor that initialise game state
     * @param entityPublisher -gives publisher to entities
     * @param chooseBuffPublisher -uses itself
     * @param levelUpPublisher - used by player
     * @param xpPublisher - uses itself
     */
    public Game(EntityPublisher entityPublisher, ChooseBuffPublisher chooseBuffPublisher, LevelUpPublisher levelUpPublisher, XpPublisher xpPublisher) {
        this.entityPublisher = entityPublisher;
        this.chooseBuffPublisher = chooseBuffPublisher;
        this.levelUpPublisher = levelUpPublisher;
        this.xpPublisher = xpPublisher;

        levelUpPublisher.subscribeLevelUp(this);
        entityPublisher.subscribeEntityDeath(this);
        entityPublisher.subscribeEntityHit(this);

        // Initialize world and constraint system
        this.world = new GameWorld(WORLD_WIDTH, WORLD_HEIGHT);
        this.constraintSystem = new ConstraintSystem();
        this.constraintSystem.addConstraint(new BoundsConstraint(world));

        // Initialize game state
        this.gameTime = 0;
        this.player = new Player(world.getWidth() / 2, world.getHeight() / 2);
        this.player.setLevelUpPublisher(levelUpPublisher);
        this.player.setEntityPublisher(entityPublisher);

        // Initialize combat system
        this.enemiesAlive = new ArrayList<>();
        this.combatManager = new CombatManager(player, () -> enemiesAlive);
        entityPublisher.subscribeAttack(this.combatManager);
    }

    // ==================== AttackListener Implementation ====================

    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getObstacle() instanceof Enemy enemy) {
            statistics.incrementKills();
            player.gainXP(enemy.getXpValue());
            EnemyPool.getInstance().returnEnemy(enemy);
            enemiesAlive.remove(enemy);
            xpPublisher.onUpdateXp(new XpChangeEvent(player.getXP(), player.getXPToNext(), player.getLevel()));
        }
    }

    @Override
    public void onEntityHit(EntityHitEvent event) {
        // Track damage dealt by player to entities
        if (event.getObstacle().getObstacleType() != ObstacleType.PLAYER) {
            statistics.addDamage(event.getCombatResult().getDamage());
        }
        // Track damage taken by player from entities
        if (event.getObstacle().getObstacleType() == ObstacleType.PLAYER) {
            statistics.addDamageTaken(event.getCombatResult().getDamage());
        }
    }

    @Override
    public void onLevelUp()
    {
        for(int i = 0; i < upgrades.length; i++)
        {
            upgrades[i] = UpgradeRegistry.randomUpgrade();
        }
        chooseBuffPublisher.onChooseBuff(new UpgradeEvent(upgrades));
    }

    // ==================== GameEventListener Implementation ====================

    @Override
    public void onApplyBuff(int i) {
        if(i >= 0 && i < upgrades.length)
        {
            upgrades[i].apply(player);
        }
    }

    @Override
    public void onMovement(MovementEvent event) {
        player.setMovementDirection(event.getDx(), event.getDy());
    }

    @Override
    public void onAttack() {
        player.attack();
    }

    // ==================== Game Logic ====================

    public void update(double deltaTime) {
        gameTime += deltaTime;

        player.update(deltaTime);
        updateEnemies(deltaTime);

        constraintSystem.apply(player);
        constraintSystem.applyAll(enemiesAlive);

        spawnEnemies();
    }

    /**
     * Updates all enemies.
     * check if enemy is alive and then handles that event otherwise should move towards player
     */
    private void updateEnemies(double deltaTime) {
        double playerX = player.getX();
        double playerY = player.getY();

        // Update only living enemies
        for (int i = enemiesAlive.size()-1; i >= 0; i--) {
            enemiesAlive.get(i).velocityAlgorithm(playerX, playerY, enemiesAlive);
            enemiesAlive.get(i).update(deltaTime);
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
                enemy.setEntityPublisher(entityPublisher);  // Register this Game as the attack listener
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
     * used when player died and want to play again
     * resets game state
     */
    public void reset() {
        this.gameTime = 0;
        this.lastEnemySpawnTime = 0;
        statistics.reset();

        player.revive();
        enemiesAlive.clear();
    }

    /**
     * Finalizes the statistics for the current run.
     * Should be called when the player dies to capture final time and level.
     */
    public void finalizeStatistics() {
        statistics.setTimeSurvived(gameTime);
        statistics.setLevelReached(player.getLevel());
        
        // Console output for testing
        int minutes = (int) (statistics.getTimeSurvived() / 60);
        int seconds = (int) (statistics.getTimeSurvived() % 60);
        System.out.println("========== GAME OVER ==========");
        System.out.println("Time Survived: " + minutes + ":" + String.format("%02d", seconds));
        System.out.println("Level Reached: " + statistics.getLevelReached());
        System.out.println("Damage Dealt: " + (int) statistics.getTotalDamageDealt());
        System.out.println("Damage Taken: " + (int) statistics.getTotalDamageTaken());
        System.out.println("Enemies Killed: " + statistics.getEnemiesKilled());
        System.out.println("--------------------------------");
        System.out.println("TOTAL SCORE: " + statistics.calculateScore());
        System.out.println("================================");
    }

    public void resetPlayerMovement() {
        player.setMovementDirection(0, 0);
    }

    // ==================== Getters ====================

    public Player getPlayer() {
        return player;
    }

    public boolean isPlayerAlive() {
        return player.getAliveStatus();
    }

    public List<Obstacle> getEnemies() {
        return new ArrayList<>(enemiesAlive);
    }

    public double getGameTime() {
        return gameTime;
    }

    /**
     * Should be called by the controller on player death to retrieve statistics.
     *
     * @return the current game statistics.
     */
    public GameStatistics getStatistics() {
        return statistics;
    }
}
