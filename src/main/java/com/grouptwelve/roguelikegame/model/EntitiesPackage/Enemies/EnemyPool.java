package com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entities;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.EntityFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 * Object pool for managing Enemy instances to improve memory efficiency.
 */
public class EnemyPool {
    private static final EnemyPool instance = new EnemyPool();
    private EnemyPool() {}
    public static EnemyPool getInstance() {
        return instance;
    }

    private final Random rand = new Random();
    private final HashMap<Entities, LinkedList<Enemy>> pool = new HashMap<>();

    /**
     * Borrows an enemy from the pool or creates a new one if none are available.
     * @param name The enemy type name (e.g., "Goblin", "Troll")
     * @param x The x-coordinate for the enemy
     * @param y The y-coordinate for the enemy
     * @return An enemy instance positioned at the specified coordinates
     */
    public Enemy borrowEnemy(Entities name, double x, double y) {
        LinkedList<Enemy> availableEnemies =  pool.get(name);
        if(availableEnemies == null){
            pool.put(name,new LinkedList<>());
            return (Enemy) EntityFactory.getInstance().createEntity(name,x,y);
        }
        if (availableEnemies.isEmpty()){
            return (Enemy) EntityFactory.getInstance().createEntity(name,x,y);
        }
        Enemy enemy = availableEnemies.remove();
        enemy.revive();
        enemy.setX(x);
        enemy.setY(y);
        return enemy;
    }
    /**
     * Borrows a random enemy from the pool or creates a new one if none are available.
     * The method assumes that the PLAYER entity is at index 0 in the Entities enum and will not be selected.
     * @param x The x-coordinate for the enemy.
     * @param y The y-coordinate for the enemy.
     * @return An enemy instance of a random type, positioned at the specified coordinates.
     * */
    public Enemy borrowRandomEnemy(double x, double y) {
        int i = rand.nextInt(Entities.values().length-1)+1;
        Entities enemyType =  Entities.values()[i];

        return borrowEnemy(enemyType, x, y);
    }

    public void returnEnemy(Enemy enemy){
        Entities type = enemy.getType();
        LinkedList<Enemy> availableEnemies =  pool.get(type);
        availableEnemies.add(enemy);
    }

}
