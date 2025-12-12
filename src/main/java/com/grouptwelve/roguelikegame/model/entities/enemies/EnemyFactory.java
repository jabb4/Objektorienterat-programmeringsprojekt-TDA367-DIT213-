package com.grouptwelve.roguelikegame.model.entities.enemies;

import com.grouptwelve.roguelikegame.model.weapons.Club;
import com.grouptwelve.roguelikegame.model.weapons.Sword;

import java.util.HashMap;

public class EnemyFactory {
    private static final EnemyFactory instance = new EnemyFactory();
    private EnemyFactory() {}
    public static EnemyFactory getInstance() {
        return instance;
    }

    private static final HashMap<Enemies, Enemy> enemyRegistry = new HashMap<>();

    static {
        enemyRegistry.put(Enemies.GOBLIN,new Enemy(
                "Goblin",
                Enemies.GOBLIN,
                0,
                0,
                50,
                5,
                30,
                70,
                20,
                new Sword(),
                0.3,
                20
        ));
        enemyRegistry.put(Enemies.TROLL,new Enemy(
                "Troll",
                Enemies.TROLL,
                0,
                0,
                70,
                15,
                70,
                50,
                40,
                new Club(),
                0.5,
                40
        ));
    }

    public Enemy createEnemy(Enemies type, double x, double y){
        Enemy enemy = enemyRegistry.get(type);
        if (enemy == null){
            throw new IllegalArgumentException("enemy 2 type '" + type + "' is not registered in the enemy registry.");
        }
        return enemy.clone(x,y);
    }
}
