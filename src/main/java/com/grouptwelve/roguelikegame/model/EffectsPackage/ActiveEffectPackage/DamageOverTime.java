package com.grouptwelve.roguelikegame.model.EffectsPackage.ActiveEffectPackage;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entity;

public class DamageOverTime extends ActiveEffect {

    private double dps;

    public DamageOverTime(double dps, double duration) {
        super(duration);
        this.dps = dps;
    }

    @Override
    public void update(Entity target, double deltaTime) {
        target.takeDamage(dps * deltaTime);
        duration -= deltaTime;
    }
}
