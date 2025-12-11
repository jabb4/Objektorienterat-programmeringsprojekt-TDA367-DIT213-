package com.grouptwelve.roguelikegame.model.effects.active;

import com.grouptwelve.roguelikegame.model.entities.Entity;

public abstract class ActiveEffect {
    protected double duration;

    public ActiveEffect(double duration) {
        this.duration = duration;
    }

    public abstract void update(Entity target, double deltaTime);

    public boolean isFinished() {
        return duration <= 0;
    }
}

