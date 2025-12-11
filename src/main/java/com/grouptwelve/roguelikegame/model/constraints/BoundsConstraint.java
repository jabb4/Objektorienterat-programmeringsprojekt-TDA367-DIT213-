package com.grouptwelve.roguelikegame.model.constraints;

import com.grouptwelve.roguelikegame.model.GameWorld;
import com.grouptwelve.roguelikegame.model.entities.Entity;

/**
 * Constrains entities to stay within the world boundaries.
 * 
 * This constraint clamps entity positions so that the entire entity
 * remains visible within the world bounds.
 * Entities that would move outside the boundary are stopped at the edge.
 */
public class BoundsConstraint implements EntityConstraint {
    private final GameWorld world;

    /**
     * Creates a new BoundsConstraint for the given world.
     *
     * @param world The game world defining the boundaries
     */
    public BoundsConstraint(GameWorld world) {
        this.world = world;
    }

    @Override
    public void apply(Entity entity) {
        double clampedX = world.clampX(entity.getX(), entity.getSize());
        double clampedY = world.clampY(entity.getY(), entity.getSize());
        entity.setX(clampedX);
        entity.setY(clampedY);
    }
}
