package com.grouptwelve.roguelikegame.model.entities.enemies;

import com.grouptwelve.roguelikegame.model.entities.Entity;

public class Enemy extends Entity {

    public Enemy(String name, double x, double y, double hp, int size, double maxHP, double attackDmg) {
        super(name, x, y, hp, size, maxHP, attackDmg);
        this.speed = 2; //Default enemy speed
    }
}

