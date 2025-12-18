package com.grouptwelve.roguelikegame.model.effects;

import com.grouptwelve.roguelikegame.model.entities.Entity;

/**
 * Represents a one-time effect applied to an entity on hit.
 */
public interface EffectInterface {

    /**
     * Applies this effect to the target entity.
     *
     * @param target the entity affected
     */
    void apply(Entity target);

    /**
     * Returns the display name of the effect.
     *
     * @return the effect name
     */
    default String getName() {
        return getClass().getSimpleName();
    }
}
