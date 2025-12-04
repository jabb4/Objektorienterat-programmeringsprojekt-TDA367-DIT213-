package com.grouptwelve.roguelikegame.model.upgrades.effects;

import com.grouptwelve.roguelikegame.model.effects.EffectInterface;
import com.grouptwelve.roguelikegame.model.effects.FireEffect;
import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.upgrades.logic.FlatAttributeUpgrade;

public class FireEffectFlatDuration extends FlatAttributeUpgrade {

    public FireEffectFlatDuration(double amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        for (EffectInterface e : player.getWeapon().getEffects()) {
            if (e instanceof FireEffect fire) {
                fire.increaseDuration(amount);
            }
        }
    }

    @Override
    public String getName() {
        return "+ " + amount + " Fire DPS";
    }
}
