package com.grouptwelve.roguelikegame.model.effects;

import com.grouptwelve.roguelikegame.model.effects.active.DamageOverTime;
import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.entities.Player;

/**
 * Effect that applies burning damage over time to a target.
 */
public class FireEffect implements EffectInterface {

    private double dps;
    private double duration;

    /**
     * Creates a fire effect.
     *
     * @param dps      damage per second
     * @param duration duration in seconds
     */
    public FireEffect(double dps, double duration) {
        this.dps = dps;
        this.duration = duration;
    }

    /**
     * Increases the damage per second.
     *
     * @param amount additional DPS
     */
    public void increaseDmg(double amount) {
        this.dps += amount;
    }

    /**
     * Increases the duration of the effect.
     *
     * @param amount additional duration in seconds
     */
    public void increaseDuration(double amount) {
        this.duration += amount;
    }

    @Override
    public void apply(Entity target) {
        if (target instanceof Player) return;

        DamageOverTime dot = target.getActiveEffect(DamageOverTime.class);

        if (dot != null) {
            dot.refresh(dps, duration);
        } else {
            target.addEffect(new DamageOverTime(dps, duration));
        }
    }
}



