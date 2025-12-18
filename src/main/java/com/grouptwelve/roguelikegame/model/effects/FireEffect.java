package com.grouptwelve.roguelikegame.model.effects;

import com.grouptwelve.roguelikegame.model.effects.active.DamageOverTime;
import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.entities.Player;

public class FireEffect implements EffectInterface {

    private double dps;
    private double duration;

    public FireEffect(double dps, double duration) {
        this.dps = dps;
        this.duration = duration;
    }

    public void increaseDmg(double amount) {
        this.dps += amount;
    }

    public void increaseDuration(double amount) {
        this.duration += amount;
    }

    @Override
    public void apply(Entity target) {
        if (target instanceof Player)
            return;

        DamageOverTime dot = target.getActiveEffect(DamageOverTime.class);

        if (dot != null) {
            dot.refresh(dps, duration);
        } else {
            target.addEffect(new DamageOverTime(dps, duration));
        }
    }

}


