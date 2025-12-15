package com.grouptwelve.roguelikegame.model.events.output.listeners;

import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.entities.Entity;

/**
 * Implement this to be notified when an entity takes damage from an attack.
 */
public interface EntityHitListener {
    /**
     * Called when an entity is hit by an attack.
     *
     * @param entity the entity that was hit
     * @param combatResult contains the damage dealt
     */
    void onEntityHit(Entity entity, CombatResult combatResult);
}
