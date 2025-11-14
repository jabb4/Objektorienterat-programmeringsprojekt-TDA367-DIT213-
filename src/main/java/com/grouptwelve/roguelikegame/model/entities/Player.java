package com.grouptwelve.roguelikegame.model.entities;

import javafx.scene.paint.Color;

public class Player extends Entity {

    public Player(double x, double y) {
        super("Player", x, y, 100, 30, 100, 20);
        this.speed = 5; // What is a good speed value?
    }

    @Override
    public void attack(Entity target) {
        System.out.println(name + " attacks " + target.getName() + " for " + attackMultiplier + " damage!");
        target.takeDamage(attackMultiplier);
    }

    public void draw(javafx.scene.canvas.GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillOval(x, y, size, size);
    }

    public int getSize() {
        return size;
    }
}

