package com.grouptwelve.roguelikegame.model.EntitiesPackage;

public class Enemy extends Entity {

    public Enemy(String name, double x, double y, double hp, int size, double maxHP) {
        super(name, x, y, hp, size, maxHP);
        this.speed = 50; // Default
    }
}

