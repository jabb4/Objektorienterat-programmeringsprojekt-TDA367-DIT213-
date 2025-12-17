package com.grouptwelve.roguelikegame.model.events.output.events;

import com.grouptwelve.roguelikegame.model.entities.Entity;

/**
 * Event data for entity health stat changes.
 */
public class HealthChangeEvent {
    private final Entity entity;
    private final double hp;
    private final double maxHp;

    public HealthChangeEvent(Entity entity, double hp, double maxHp) {
        this.entity = entity;
        this.hp = hp;
        this.maxHp = maxHp;
    }

    public Entity getEntity() {
        return entity;
    }

    public double getHp() {
        return hp;
    }

    public double getMaxHp() {
        return maxHp;
    }
}
