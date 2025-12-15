package com.grouptwelve.roguelikegame.model.events.output.listeners;

import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.entities.enemies.Enemy;

public interface EntityDeathListener {
    void onEntityDeath(Entity entity);
}
