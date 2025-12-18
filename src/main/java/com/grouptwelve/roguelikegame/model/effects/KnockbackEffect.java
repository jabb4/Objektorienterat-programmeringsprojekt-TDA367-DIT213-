package com.grouptwelve.roguelikegame.model.effects;

import com.grouptwelve.roguelikegame.model.entities.Entity;

/**
 * Effect that applies knockback to an entity.
 */
public class KnockbackEffect implements EffectInterface {

    private double dirX;
    private double dirY;
    private double strength;

    /**
     * Sets the knockback strength.
     *
     * @param strength knockback force
     */
    public void setStrength(double strength) {
        this.strength = strength;
    }

    /**
     * Sets and normalizes the knockback direction.
     *
     * @param x x-direction
     * @param y y-direction
     */
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
