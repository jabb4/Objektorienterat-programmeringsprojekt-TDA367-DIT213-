package com.grouptwelve.roguelikegame.model;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.*;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies.EnemyPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Core game model containing all game state and logic.
 */
public class Game {
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
     * called by controller when attack key is pressed
     */
    public void playerAttack()
    {
        player.attack();
    }
    /**
     * sets the direction of player to input
     *
     * @param dx Horizontal direction (-1, 0, or 1)
     * @param dy Vertical direction (-1, 0, or 1)
     */

    public void movePlayer(int dx, int dy) {
        player.setDir(dx, dy);
    }
    
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
