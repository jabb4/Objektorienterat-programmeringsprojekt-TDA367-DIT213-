package com.grouptwelve.roguelikegame.model.events.output.listeners;

import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.events.output.events.EntityHitEvent;

/**
 * Implement this to be notified when an entity takes damage from an attack.
 */
public interface EntityHitListener {
    /**
     * Called when an entity is hit by an attack.
     *
     * @param entityHitEvent contains entity and damage info
     */
    void onEntityHit(EntityHitEvent entityHitEvent);
}
