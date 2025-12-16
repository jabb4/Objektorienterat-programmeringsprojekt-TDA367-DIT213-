package com.grouptwelve.roguelikegame.model.upgrades;

import com.grouptwelve.roguelikegame.model.entities.Entity;

public interface UpgradeInterface {
    void apply(Entity entity);
    String getName();
}
