package com.grouptwelve.roguelikegame.model.entities.enemies;

import com.grouptwelve.roguelikegame.model.entities.Entity;

import javafx.scene.paint.Color;
public class Enemy extends Entity {

    public Enemy(String name, double x, double y, double hp, int size, double maxHP, double attackDmg, Color color) {
        super(name, x, y, hp, size, maxHP, attackDmg, color);
        this.speed = 2; //Default enemy speed
    }

    public int getSize() {
        return size;
    }
}

