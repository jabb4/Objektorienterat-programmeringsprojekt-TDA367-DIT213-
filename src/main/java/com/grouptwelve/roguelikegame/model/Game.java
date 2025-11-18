package com.grouptwelve.roguelikegame.model;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies.Goblin;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies.Troll;
import com.grouptwelve.roguelikegame.model.Weapons.CombatManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Core game model containing all game state and logic.
 */
public class Game {
    private Player player;
    private List<Enemy> enemies;
    private double gameTime;
    
    public Game() {
        // Initialize game state
        this.player = new Player(400, 300);
        Goblin testGob = new Goblin(700, 100);
        Troll testTroll = new Troll(100, 100);
        this.enemies = new ArrayList<>(List.of((Enemy) testGob, (Enemy) testTroll));
        CombatManager.getInstance().addEnemy(testGob);
        CombatManager.getInstance().addEnemy(testTroll);
        this.gameTime = 0;
    }
    
    /**
     * Updates the game state by one frame.
     * 
     * @param deltaTime Time elapsed since last update (in seconds)
     */
    public void update(double deltaTime) {
        gameTime += deltaTime;

        // TODO: Add enemy AI here
        // NOTE: use for loop to access each enemy in the enemies list

        //dont want to get() in for loop each time so do it before
        double playerX = player.getX() ;
        double playerY = player.getY() ;
        for (Enemy enemy : enemies)
        {
            if(!enemy.getAliveStatus()) continue;
            double dx =  ((playerX - enemy.getX()));
            double dy =  ((playerY - enemy.getY()));
            double distance =  Math.sqrt(dx*dx + dy*dy);

            //normalize
            dx /= distance;
            dy /= distance;




            enemy.move(dx, dy, deltaTime);
        }

    }
    

    public void playerAttack()
    {
        System.out.println();
        player.attack();
    }
    /**
     * Moves the player based on input direction.
     *
     * @param dx Horizontal direction (-1, 0, or 1)
     * @param dy Vertical direction (-1, 0, or 1)
     * @param deltaTime Time elapsed since last update (in seconds)
     */
    public void movePlayer(int dx, int dy, double deltaTime) {
        player.move(dx, dy, deltaTime);
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public List<Enemy> getEnemies() {
        return enemies;
    }

    public double getGameTime() {
        return gameTime;
    }
}
