package com.grouptwelve.roguelikegame.model.EventsPackage;

/**
 * Event representing a change in player movement intent.
 * Called when the player presses/releases movement keys.
 */
public class MovementEvent {
    private int dy;
    private int dx;
    private boolean isMoving;

    public MovementEvent(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
        this.isMoving = (dx != 0 || dy != 0);
    }

    // ==================== Getters ====================

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public boolean isMoving() {
        return isMoving;
    }
}

