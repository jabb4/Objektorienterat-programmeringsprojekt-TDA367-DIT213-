package com.grouptwelve.roguelikegame.model;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies.Goblin;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.EntityFactory;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.LoadEntities;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies.Troll;
import com.grouptwelve.roguelikegame.model.EventsPackage.AttackEvent;
import com.grouptwelve.roguelikegame.model.EventsPackage.GameEventListener;
import com.grouptwelve.roguelikegame.model.EventsPackage.MovementEvent;
import com.grouptwelve.roguelikegame.model.Weapons.CombatManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Core game model containing all game state and logic.
 */
public class Game implements GameEventListener {
    private Player player;
    private List<Enemy> enemies;
    private double gameTime;
    
    public Game() {
        // Initialize game state
        LoadEntities.load();
        this.player = (Player) EntityFactory.getInstance().createEntity("Player", 400, 300);
        Goblin testGob = (Goblin) EntityFactory.getInstance().createEntity("Goblin", 700, 100);
        Troll testTroll = (Troll) EntityFactory.getInstance().createEntity("Troll", 100, 100);
        this.enemies = new ArrayList<>(List.of((Enemy) testGob, (Enemy) testTroll));
        CombatManager.getInstance().addEnemy(testGob);
        CombatManager.getInstance().addEnemy(testTroll);
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

        // Updates player states
        player.update(deltaTime);

        // Enemy AI
        // TODO: Move this to Enemy.update()
        double playerX = player.getX() ;
        double playerY = player.getY() ;
        for (Enemy enemy : enemies)
        {
            if(!enemy.getAliveStatus()) continue;
            enemy.setTargetPos(playerX, playerY);

            enemy.update(deltaTime);
        }
    }

    /**
     * Triggers a player attack.
     */
    public void playerAttack()
    {
        System.out.println();
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
        return enemies;
    }

    public double getGameTime() {
        return gameTime;
    }
}
