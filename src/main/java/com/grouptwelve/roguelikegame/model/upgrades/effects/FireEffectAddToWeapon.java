package com.grouptwelve.roguelikegame.model.upgrades.effects;

import com.grouptwelve.roguelikegame.model.effects.FireEffect;
import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

/**
 * Upgrade that adds a fire damage effect to the entity's weapon.
 * <p>
 * This upgrade can only be applied once.
 */
public class FireEffectAddToWeapon implements UpgradeInterface {

    private final double dps;
    private final double duration;

    /**
     * Creates a fire effect upgrade.
     *
     * @param dps      damage per second
     * @param duration effect duration in seconds
     */
    public FireEffectAddToWeapon(double dps, double duration) {
        this.dps = dps;
        this.duration = duration;
    }

    @Override
    public void apply(Entity entity) {
        entity.addWeaponEffect(new FireEffect(dps, duration));
    }

    @Override
    public boolean isAvailable(Entity entity) {
        return !entity.hasWeaponEffect(FireEffect.class);
    }

    @Override
    public String getName() {
        return "Fire Effect: " + (int)dps + " Damage per second for " + (int)duration + "s";
    }
}



