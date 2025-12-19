package com.grouptwelve.roguelikegame.model.upgrades.effects;

import com.grouptwelve.roguelikegame.model.effects.EffectInterface;
import com.grouptwelve.roguelikegame.model.effects.FireEffect;
import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.upgrades.logic.FlatAttributeUpgrade;

/**
 * Upgrade that increases the duration of an existing fire effect.
 */
public class FireEffectFlatDuration extends FlatAttributeUpgrade {

    /**
     * Creates a fire duration upgrade.
     *
     * @param amount duration increase in seconds
     */
    public FireEffectFlatDuration(double amount) {
        super(amount);
    }

    @Override
    public void apply(Entity entity) {
        for (EffectInterface e : entity.getWeaponEffects()) {
            if (e instanceof FireEffect fire) {
                fire.increaseDuration(amount);
            }
        }
    }

    @Override
    public boolean isAvailable(Entity entity) {
        return entity.hasWeaponEffect(FireEffect.class);
    }

    @Override
    public String getName() {
        return "+ " + (int)amount + "s Fire Duration";
    }
}
