package com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entities;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.EntityFactory;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Object pool for managing Enemy instances to improve memory efficiency.
 */
public class EnemyPool {
    private static final EnemyPool instance = new EnemyPool();
    private EnemyPool() {}
    public static EnemyPool getInstance() {
        return instance;
    }

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

    public void returnEnemy(Enemy enemy){
        Entities type = enemy.getType();
        LinkedList<Enemy> availableEnemies =  pool.get(type);
        availableEnemies.add(enemy);
    }

}
