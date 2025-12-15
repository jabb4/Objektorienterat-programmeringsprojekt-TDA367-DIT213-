package com.grouptwelve.roguelikegame.model.events.output.listeners;

import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.entities.enemies.Enemy;

/**
 * Implement this to be notified when any entity dies.
 */
public interface EntityDeathListener {
    /**
     * Called when an entity dies.
     *
     * @param entity the entity that died
     */
    void onEntityDeath(Entity entity);
}
