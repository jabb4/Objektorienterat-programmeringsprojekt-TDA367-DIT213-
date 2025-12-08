package com.grouptwelve.roguelikegame.model;

/**
 * Represents the game world's spatial dimensions and provides
 * utility methods for bounds checking and clamping.
 * 
 * The world has a fixed size that is independent of the window size.
 * Entities are constrained to stay within these bounds.
 */
public class GameWorld {
    private final double width;
    private final double height;

    /**
     * Creates a new GameWorld with the specified dimensions.
     *
     * @param width  The width of the world in pixels
     * @param height The height of the world in pixels
     */
    public GameWorld(double width, double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the width of the world.
     *
     * @return The world width in pixels
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the height of the world.
     *
     * @return The world height in pixels
     */
    public double getHeight() {
        return height;
    }

    /**
     * Clamps an X coordinate so an entity with the given radius stays fully within bounds.
     *
     * @param x          The X coordinate to clamp
     * @param entitySize The radius of the entity
     * @return The clamped X coordinate
     */
    public double clampX(double x, int entitySize) {
        return Math.max(entitySize, Math.min(x, width - entitySize));
    }

    /**
     * Clamps a Y coordinate so an entity with the given radius stays fully within bounds.
     *
     * @param y          The Y coordinate to clamp
     * @param entitySize The radius of the entity
     * @return The clamped Y coordinate
     */
    public double clampY(double y, int entitySize) {
        return Math.max(entitySize, Math.min(y, height - entitySize));
    }
}
