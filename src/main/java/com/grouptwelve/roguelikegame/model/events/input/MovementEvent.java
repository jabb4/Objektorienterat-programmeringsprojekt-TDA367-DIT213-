package com.grouptwelve.roguelikegame.model.events.input;

/**
 * Event data for player movement input.
 */
public class MovementEvent {
    private int dy;
    private int dx;

    /**
     * Creates a new movement event.
     *
     * @param dx the horizontal movement direction
     * @param dy the vertical movement direction
     */
    public MovementEvent(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // ==================== Getters ====================

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}

