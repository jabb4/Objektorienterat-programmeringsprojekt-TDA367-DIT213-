package com.grouptwelve.roguelikegame.model.entities.enemies;

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
    private final HashMap<Enemies, LinkedList<Enemy>> pool = new HashMap<>();

    /**
     * Borrows an enemy from the pool or creates a new one if none are available.
     * @param type The enemy type (e.g., "Goblin", "Troll")
     * @param x The x-coordinate for the enemy
     * @param y The y-coordinate for the enemy
     * @return An enemy instance positioned at the specified coordinates
     */
    public Enemy borrowEnemy(Enemies type, double x, double y) {
        LinkedList<Enemy> availableEnemies =  pool.get(type);
        if(availableEnemies == null){
            pool.put(type,new LinkedList<>());
            return (Enemy) EnemyFactory.getInstance().createEnemy(type,x,y);
        }
        if (availableEnemies.isEmpty()){
            return (Enemy) EnemyFactory.getInstance().createEnemy(type,x,y);
        }
        Enemy enemy = availableEnemies.remove();
        enemy.revive();
        enemy.setX(x);
        enemy.setY(y);
        return enemy;
    }
    /**
     * Borrows a random enemy from the pool or creates a new one if none are available.
     * @param x The x-coordinate for the enemy.
     * @param y The y-coordinate for the enemy.
     * @return An enemy instance of a random type, positioned at the specified coordinates.
     * */
    public Enemy borrowRandomEnemy(double x, double y) {
        int i = rand.nextInt(Enemies.values().length);
        Enemies enemyType = Enemies.values()[i];

        return borrowEnemy(enemyType, x, y);
    }

    public void returnEnemy(Enemy enemy){
        Enemies type = enemy.getType();
        LinkedList<Enemy> availableEnemies =  pool.get(type);
        availableEnemies.add(enemy);
    }

}
