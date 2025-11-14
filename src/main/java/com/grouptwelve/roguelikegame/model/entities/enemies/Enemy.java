package com.grouptwelve.roguelikegame.model.entities.enemies;

import com.grouptwelve.roguelikegame.model.entities.Entity;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class Enemy extends Entity {

    public Enemy(String name, double x, double y, double hp, int size, double maxHP, double attackDmg) {
        super(name, x, y, hp, size, maxHP, attackDmg);
        this.speed = 2; //Default enemy speed
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(x, y, size, size);
    }

    public int getSize() {
        return size;
    }
}

