package com.grouptwelve.roguelikegame.model;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies.Goblin;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.EntityFactory;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.LoadEntities;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.*;

import com.grouptwelve.roguelikegame.model.EventsPackage.AttackEvent;
import com.grouptwelve.roguelikegame.model.EventsPackage.GameEventListener;
import com.grouptwelve.roguelikegame.model.EventsPackage.MovementEvent;
import com.grouptwelve.roguelikegame.model.Weapons.CombatManager;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.*;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies.EnemyPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Core game model containing all game state and logic.
 */
public class Game implements GameEventListener {
    private final Player player;
    private final List<Enemy> enemiesAlive;
    private double gameTime;

    private static final Game instance = new Game();
    public static Game getInstance() {
        return instance;
    }

    private Game() {
        // Initialize game state
        LoadEntities.load();
        this.player = (Player) EntityFactory.getInstance().createEntity(Entities.PLAYER, 400, 300);
        this.enemiesAlive = new ArrayList<>();
        this.enemiesAlive.add(EnemyPool.getInstance().borrowEnemy(Entities.GOBLIN, 10,20));
        this.enemiesAlive.add(EnemyPool.getInstance().borrowEnemy(Entities.TROLL, 300,500));
        this.enemiesAlive.add(EnemyPool.getInstance().borrowEnemy(Entities.GOBLIN, 500,20));
        this.enemiesAlive.add(EnemyPool.getInstance().borrowEnemy(Entities.TROLL, 40,500));
        this.enemiesAlive.add(EnemyPool.getInstance().borrowEnemy(Entities.GOBLIN, 90,87));
        this.enemiesAlive.add(EnemyPool.getInstance().borrowEnemy(Entities.TROLL, 100,100));
        this.gameTime = 0;
    }

    // ==================== Game Event Handlers ====================

    @Override
    public void onMovement(MovementEvent event) {
        setPlayerMovement(event.getDx(), event.getDy());
    }

    @Override
    public void onAttack(AttackEvent event) {
        playerAttack();
    }

    // TODO: Add other event handlers when features are added
    // onPlayerLevelUp();

    // ==================== Game Logic ====================

    /**
     * Updates the game state by one frame.
     * 
     * @param deltaTime Time elapsed since last update (in seconds)
     */
    public void update(double deltaTime) {
        gameTime += deltaTime;

        player.update(deltaTime);
        double playerX = player.getX() ;
        double playerY = player.getY() ;
        for (Enemy enemy : enemiesAlive)
        {
            enemy.setTargetPos(playerX, playerY);
            enemy.update(deltaTime);
        }
    }

    /**
     * Triggers a player attack.
     */
    public void playerAttack()
    {
        player.attack();
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

    // TODO: Add more game actions

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
