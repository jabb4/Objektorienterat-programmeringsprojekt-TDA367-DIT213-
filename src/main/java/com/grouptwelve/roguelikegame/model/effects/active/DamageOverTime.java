package com.grouptwelve.roguelikegame.model.effects.active;

import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.entities.Entity;

public class DamageOverTime extends ActiveEffect {

    private double dps;

    public DamageOverTime(double dps, double duration) {
        super(duration);
        this.dps = dps;
    }

    @Override
    public void update(Entity target, double deltaTime) {
        target.takeDamage(new CombatResult(dps * deltaTime, false));
        duration -= deltaTime;
    }

    public void refresh(double newDps, double newDuration) {
        this.dps = Math.max(this.dps, newDps);
        this.duration = Math.max(this.duration, newDuration);
    }

}
