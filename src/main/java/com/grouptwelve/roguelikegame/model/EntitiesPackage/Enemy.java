package com.grouptwelve.roguelikegame.model.EntitiesPackage;

import javafx.scene.shape.Rectangle;

public abstract class Enemy extends Entity {
    protected Rectangle hpBar;

    public Enemy(String name, double x, double y, double hp, int size, double maxHP) {
        super(name, x, y, hp, size, maxHP);
        this.speed = 50; // Default
    }

    public Rectangle getHpBar() { return hpBar; }
    public void setHpBar(Rectangle hpBar) { this.hpBar = hpBar; }
}

