package com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemies;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Enemy;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.EntityFactory;

import java.util.HashMap;
import java.util.LinkedList;

public class EnemyPool {
    private static final EnemyPool instance = new EnemyPool();
    private EnemyPool() {}
    public static EnemyPool getInstance() {
        return instance;
    }

    private final HashMap<String, LinkedList<Enemy>> pool = new HashMap<>();


    public Enemy borrowEnemy(String name, double x, double y) {
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
        String name = enemy.getName();
        LinkedList<Enemy> availableEnemies =  pool.get(name);
        availableEnemies.add(enemy);
    }

}
