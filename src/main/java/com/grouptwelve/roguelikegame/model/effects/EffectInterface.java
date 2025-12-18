package com.grouptwelve.roguelikegame.model.effects;

import com.grouptwelve.roguelikegame.model.entities.Entity;

public interface EffectInterface {
    void apply(Entity target);

    default String getName() {
        return getClass().getSimpleName();
    }
}

