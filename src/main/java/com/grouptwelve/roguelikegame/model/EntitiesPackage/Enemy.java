package com.grouptwelve.roguelikegame.model.EntitiesPackage;

public abstract class Enemy extends Entity {
    public Enemy(String name, double x, double y, double hp, int size, double maxHP) {
        super(name, x, y, hp, size, maxHP);
        this.velocity.setMaxSpeed(50); // Default enemy velocity
    }

    // TODO: override update method and move enemy AI into there
}

