package com.grouptwelve.roguelikegame.model.events.output.events;

import com.grouptwelve.roguelikegame.model.entities.Entity;

/**
 * Event data for entity death.
 */
public class EntityDeathEvent {
    private final Entity entity;

    /**
     * Creates a new entity death event.
     *
     * @param entity the entity that died
     */
    public EntityDeathEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
