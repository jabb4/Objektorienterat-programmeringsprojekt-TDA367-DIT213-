package com.grouptwelve.roguelikegame.model.constraints;

import com.grouptwelve.roguelikegame.model.entities.Entity;

/**
 * Represents a world-level rule that can be applied to entities.
 * 
 * Constraints are applied every frame by the ConstraintSystem.
 * Unlike Effects (which are entity-owned and often temporary),
 * Constraints are world-owned and represent persistent rules
 * that the environment enforces on all entities.
 */
public interface EntityConstraint {
    
    /**
     * Applies this constraint to the given entity.
     *
     * @param entity The entity to constrain
     */
    void apply(Entity entity);
}
