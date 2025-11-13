package com.grouptwelve.roguelikegame.model;

import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.entities.enemies.Enemy;

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
        this.enemies = new ArrayList<>();
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
