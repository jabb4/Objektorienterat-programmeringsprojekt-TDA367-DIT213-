package com.grouptwelve.roguelikegame.model.EntitiesPackage;

public abstract class Enemy extends Entity {
    public Enemy(String name, Entities type, double x, double y, double hp, int size, double maxHP) {
        super(name, type, x, y, hp, size, maxHP);
        this.speed = 50; // Default
    }
}

