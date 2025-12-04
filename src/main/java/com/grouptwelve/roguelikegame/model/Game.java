package com.grouptwelve.roguelikegame.model;

import com.grouptwelve.roguelikegame.model.combat.CombatManager;
import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.effects.EffectInterface;
import com.grouptwelve.roguelikegame.model.entities.*;
import com.grouptwelve.roguelikegame.model.entities.enemies.EnemyPool;
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

        // Initialize game state
        LoadEntities.load();
        this.player = (Player) EntityFactory.getInstance().createEntity(Entities.PLAYER, 400, 300);
        this.enemiesAlive = new ArrayList<>();
        this.gameTime = 0;
        
        // Initialize combat system
        this.combatManager = new CombatManager(player, () -> enemiesAlive, eventPublisher);
        
        // Set up player to notify this Game when attacking
        player.setAttackListener(this);
        player.setLevelUpListener(this);

        // Spawn initial enemy
        Enemy initialEnemy = EnemyPool.getInstance().borrowEnemy(Entities.GOBLIN, 10, 20);
        initialEnemy.setAttackListener(this);
        enemiesAlive.add(initialEnemy);
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
            enemy.setTargetPos(playerX, playerY);
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
                int spawnX = rand.nextInt(400);
                int spawnY = rand.nextInt(400);
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
}
