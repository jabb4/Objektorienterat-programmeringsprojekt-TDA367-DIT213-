package com.grouptwelve.roguelikegame.model.EffectsPackage.ActiveEffectPackage;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entity;

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

