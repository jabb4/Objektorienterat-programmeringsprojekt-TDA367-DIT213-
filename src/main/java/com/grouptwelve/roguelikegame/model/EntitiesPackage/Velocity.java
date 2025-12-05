package com.grouptwelve.roguelikegame.model.EntitiesPackage;

/**
 * Represents the velocity of an entity.
 * Handles both intentional movement and knockback.
 */
public class Velocity {
    private double x;
    private double y;
    private double knockbackX = 0;
    private double knockbackY = 0;
    private static final double KNOCKBACK_DECAY = 0.85;
    private static final double KNOCKBACK_THRESHOLD = 5.0;
    private double maxSpeed;
    // private double scaleFactor = 1.0;

    public Velocity(double maxSpeed) {
        this.x = 0;
        this.y = 0;
        this.maxSpeed = maxSpeed;
    }

    /**
     * Updates velocity state each frame. Handles knockback decay.
     *
     * @param deltaTime Time since last update
     */
    public void update(double deltaTime) {
        // Decay knockback over time
        knockbackX *= KNOCKBACK_DECAY;
        knockbackY *= KNOCKBACK_DECAY;

        // Zero out small values for better performance
        if (Math.abs(knockbackX) < KNOCKBACK_THRESHOLD) knockbackX = 0;
        if (Math.abs(knockbackY) < KNOCKBACK_THRESHOLD) knockbackY = 0;
    }

    // ==================== Velocity Manipulation ====================

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void stop() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Applies a knockback force.
     *
     * @param dirX Normalized x direction of knockback
     * @param dirY Normalized y direction of knockback
     * @param strength The strength/speed of the knockback
     */
    public void applyKnockback(double dirX, double dirY, double strength) {
        this.knockbackX = dirX * strength;
        this.knockbackY = dirY * strength;
    }

    // TODO: Add setScaleFactor to apply slowing effects or speed effects

    // ==================== Getters ====================

    /**
     * @return Combined x velocity (intentional + knockback)
     */
    public double getX() {
        return this.x + knockbackX;
    }

    /**
     * @return Combined y velocity (intentional + knockback)
     */
    public double getY() {
        return this.y + knockbackY;
    }

    public double getMaxSpeed() {
        return this.maxSpeed;
    }

    // ==================== Setters ====================

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
