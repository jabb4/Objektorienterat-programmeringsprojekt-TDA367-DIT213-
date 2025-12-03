package com.grouptwelve.roguelikegame.model.EffectsPackage;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entity;

public class KnockbackEffect extends Effects {

    private double strength;
    private double dirX;
    private double dirY;

    public KnockbackEffect(double strength) {
        this.strength = strength;
    }

    public void setDirection(double x, double y) {
        double len = Math.sqrt(x * x + y * y);
        if (len != 0) {
            this.dirX = x / len;
            this.dirY = y / len;
        } else {
            this.dirX = 0;
            this.dirY = 0;
        }
    }

    @Override
    public void apply(Entity target) {
        target.applyKnockback(dirX, dirY, strength);
    }

    public void increaseStrength(double amount) {
        this.strength += amount;
    }

    public double getStrength() {
        return strength;
    }
}
