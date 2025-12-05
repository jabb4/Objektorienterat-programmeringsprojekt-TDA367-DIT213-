package com.grouptwelve.roguelikegame.model.effects;

import com.grouptwelve.roguelikegame.model.entities.Entity;

public class KnockbackEffect extends Effects {

    private double dirX;
    private double dirY;
    private double strength;

    public KnockbackEffect() {
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public void setDirection(double x, double y) {
        double len = Math.sqrt(x * x + y * y);
        if (len != 0) {
            dirX = x / len;
            dirY = y / len;
        } else {
            dirX = 0;
            dirY = 0;
        }
    }

    @Override
    public void apply(Entity target) {
        target.applyKnockback(dirX, dirY, strength);
    }
}
