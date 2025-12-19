package com.grouptwelve.roguelikegame.model.effects.active;

import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.entities.Entity;

/**
 * Active effect that deals damage continuously over time.
 */
public class DamageOverTime extends ActiveEffect {

    private double dps;

    /**
     * Creates a damage-over-time effect.
     *
     * @param dps      damage per second
     * @param duration duration in seconds
     */
    public DamageOverTime(double dps, double duration) {
        super(duration);
        this.dps = dps;
    }

    @Override
    public void update(Entity target, double deltaTime) {
        target.takeDamage(new CombatResult(dps * deltaTime, false));
        duration -= deltaTime;
    }

    /**
     * Refreshes this effect when reapplied.
     * <p>
     * The higher DPS and longer duration are kept.
     * If you fire attack and then upgrade, the better dps or duration takes over if attack again
     * @param newDps      new damage per second
     * @param newDuration new duration in seconds
     */
    public void refresh(double newDps, double newDuration) {
        this.dps = Math.max(this.dps, newDps);
        this.duration = Math.max(this.duration, newDuration);
    }
}
