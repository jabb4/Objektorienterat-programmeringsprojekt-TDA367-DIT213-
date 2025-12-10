package com.grouptwelve.roguelikegame.model.constraints;

import com.grouptwelve.roguelikegame.model.entities.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages and applies world-level constraints to entities.
 * 
 * Constraints are rules that the world enforces on entities.
 * The ConstraintSystem is called each frame after entity updates
 * to ensure all constraints are satisfied.
 */
public class ConstraintSystem {
    private final List<EntityConstraint> constraints = new ArrayList<>();

    /**
     * Adds a constraint to the system.
     *
     * @param constraint The constraint to add
     */
    public void addConstraint(EntityConstraint constraint) {
        constraints.add(constraint);
    }

    /**
     * Removes a constraint from the system.
     *
     * @param constraint The constraint to remove
     */
    public void removeConstraint(EntityConstraint constraint) {
        constraints.remove(constraint);
    }

    /**
     * Applies all constraints to a single entity.
     *
     * @param entity The entity to constrain
     */
    public void apply(Entity entity) {
        for (EntityConstraint constraint : constraints) {
            constraint.apply(entity);
        }
    }

    /**
     * Applies all constraints to multiple entities.
     *
     * @param entities The entities to constrain
     */
    public void applyAll(Iterable<? extends Entity> entities) {
        for (Entity entity : entities) {
            apply(entity);
        }
    }
}
