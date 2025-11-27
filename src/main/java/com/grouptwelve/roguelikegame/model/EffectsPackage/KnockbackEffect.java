package com.grouptwelve.roguelikegame.model.EffectsPackage;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entity;

/**
 * Effect that applies knockback to an entity, pushing them away from the attack source.
 * The direction must be set before applying the effect.
 */
public class KnockbackEffect implements EffectInterface {

    private double knockbackStrength;
    private double directionX;
    private double directionY;

    /**
     * Creates a new knockback effect with the specified strength.
     *
     * @param strength The strength/speed of the knockback force
     */
    public KnockbackEffect(double strength) {
        this.knockbackStrength = strength;
        this.directionX = 0;
        this.directionY = 0;
    }

    /**
     * Sets the direction of the knockback. The direction will be normalized automatically.
     *
     * @param dirX Normalized x component of direction
     * @param dirY Normalized y component of direction
     */
    public void setDirection(double dirX, double dirY) {
        double length = Math.sqrt(dirX * dirX + dirY * dirY);
        if (length != 0) {
            this.directionX = dirX / length;
            this.directionY = dirY / length;
        } else {
            this.directionX = 0;
            this.directionY = 0;
        }
    }

    @Override
    public void apply(Entity target) {
        target.applyKnockback(directionX, directionY, knockbackStrength);
    }

    public double getKnockbackStrength() {
        return knockbackStrength;
    }

    public void setKnockbackStrength(double strength) {
        this.knockbackStrength = strength;
    }
}
