package com.grouptwelve.roguelikegame.model.events.output.listeners;

import com.grouptwelve.roguelikegame.model.events.output.events.EntityDeathEvent;

/**
 * Implement this to be notified when any entity dies.
 */
public interface EntityDeathListener {
    /**
     * Called when an entity dies.
     *
     * @param event contains information about the entity that died
     */
    void onEntityDeath(EntityDeathEvent event);
}
