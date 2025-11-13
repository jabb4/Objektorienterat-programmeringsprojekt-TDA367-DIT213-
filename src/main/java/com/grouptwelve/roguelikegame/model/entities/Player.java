package com.grouptwelve.roguelikegame.model.entities;

public class Player extends Entity {

    public Player(double x, double y) {
        super("Player", x, y, 100, 10, 100, 20);
        this.speed = 100; // 100 pixels per second (because of deltaTime)
    }

    @Override
    public void attack(Entity target) {
        System.out.println(name + " attacks " + target.getName() + " for " + attackMultiplier + " damage!");
        target.takeDamage(attackMultiplier);
    }
}

