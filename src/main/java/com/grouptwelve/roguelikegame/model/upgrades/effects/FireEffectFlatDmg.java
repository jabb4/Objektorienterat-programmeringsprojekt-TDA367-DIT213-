package com.grouptwelve.roguelikegame.model.upgrades.effects;

import com.grouptwelve.roguelikegame.model.effects.EffectInterface;
import com.grouptwelve.roguelikegame.model.effects.FireEffect;
import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

/**
 * Upgrade that increases the damage per second of an existing fire effect.
 * <p>
 * Requires the entity to already have a fire effect.
 */
public class FireEffectFlatDmg implements UpgradeInterface {

    private final double extraBurn;

    /**
     * Creates a fire damage upgrade.
     *
     * @param extraBurn additional DPS to add
     */
    public FireEffectFlatDmg(double extraBurn) {
        this.extraBurn = extraBurn;
    }

    @Override
    public void apply(Entity entity) {
        for (EffectInterface e : entity.getWeaponEffects()) {
            if (e instanceof FireEffect fire) {
                fire.increaseDmg(extraBurn);
            }
        }
    }

    @Override
    public boolean isAvailable(Entity entity) {
        return entity.hasWeaponEffect(FireEffect.class);
    }

    @Override
    public String getName() {
        return "+ " + (int)extraBurn + " Fire DPS";
    }
}




