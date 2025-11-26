package com.grouptwelve.roguelikegame.model;

/**
 * Represents the velocity of an entity.
 */
public class Velocity {
    private double x;
    private double y;
    private double maxSpeed;

    public Velocity(double maxSpeed) {
        this.x = 0;
        this.y = 0;
        this.maxSpeed = maxSpeed;
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

    // TODO: add(dx, dy) for knockback, scale(factor) for slowing effects or speed buffs

    // ==================== Getters ====================

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getMaxSpeed() {
        return this.maxSpeed;
    }

    // ==================== Setters ====================

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
