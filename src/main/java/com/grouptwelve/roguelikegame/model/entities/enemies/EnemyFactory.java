package com.grouptwelve.roguelikegame.model.entities.enemies;

import com.grouptwelve.roguelikegame.model.weapons.Club;
import com.grouptwelve.roguelikegame.model.weapons.Sword;

import java.util.HashMap;

public class EnemyFactory {
    private static final EnemyFactory instance = new EnemyFactory();

    // Goblin stats
    private static final int GOBLIN_HP = 30;
    private static final int GOBLIN_SIZE = 5;
    private static final int GOBLIN_MAX_HP = 30;
    private static final int GOBLIN_SPEED = 70;
    private static final int GOBLIN_XP_VALUE = 20;
    private static final double GOBLIN_WIND_UP_TIME = 0.3;
    private static final int GOBLIN_ATTACK_RANGE = 25;

    // Troll stats
    private static final int TROLL_HP = 70;
    private static final int TROLL_SIZE = 15;
    private static final int TROLL_MAX_HP = 70;
    private static final int TROLL_SPEED = 50;
    private static final int TROLL_XP_VALUE = 40;
    private static final double TROLL_WIND_UP_TIME = 0.5;
    private static final int TROLL_ATTACK_RANGE = 40;

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
                GOBLIN_HP,
                GOBLIN_SIZE,
                GOBLIN_MAX_HP,
                GOBLIN_SPEED,
                GOBLIN_XP_VALUE,
                new Sword(),
                GOBLIN_WIND_UP_TIME,
                GOBLIN_ATTACK_RANGE
        ));
        enemyRegistry.put(Enemies.TROLL,new Enemy(
                "Troll",
                Enemies.TROLL,
                0,
                0,
                TROLL_HP,
                TROLL_SIZE,
                TROLL_MAX_HP,
                TROLL_SPEED,
                TROLL_XP_VALUE,
                new Club(),
                TROLL_WIND_UP_TIME,
                TROLL_ATTACK_RANGE
        ));
    }

    public Enemy createEnemy(Enemies type, double x, double y){
        Enemy enemy = enemyRegistry.get(type);
        if (enemy == null){
            throw new IllegalArgumentException("enemy 2 type '" + type + "' is not registered in the enemy registry.");
        }
        return new Enemy(enemy, x, y);
    }
}
