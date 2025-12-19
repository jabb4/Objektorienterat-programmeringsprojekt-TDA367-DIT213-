package com.grouptwelve.roguelikegame.model.effects.active;

import com.grouptwelve.roguelikegame.model.entities.Entity;

/**
 * Base class for effects that persist over time.
 */
public abstract class ActiveEffect {

    /**
     * Remaining duration in seconds.
     */
    protected double duration;

    /**
     * Creates an active effect.
     *
     * @param duration effect duration in seconds
     */
    public ActiveEffect(double duration) {
        this.duration = duration;
    }

    /**
     * Updates the effect.
     *
     * @param target    the affected entity
     * @param deltaTime time since last update
     */
    public abstract void update(Entity target, double deltaTime);

    /**
     * Checks whether the effect has expired.
     *
     * @return {@code true} if finished
     */
    public boolean isFinished() {
        return duration <= 0;
    }
}

