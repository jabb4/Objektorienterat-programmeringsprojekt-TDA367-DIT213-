package com.grouptwelve.roguelikegame.model.effects;

import com.grouptwelve.roguelikegame.model.entities.Entity;

/**
 * Visual effect that causes an entity to flash white when hit.
 * Duration controls how long the flash lasts.

public class HitEffect extends Effects {
    private final double duration;

    public HitEffect(double duration) {
        this.duration = duration;
    }

    @Override
    public void apply(Entity target) {
        target.setHit(true, duration);
    }
}
 */
