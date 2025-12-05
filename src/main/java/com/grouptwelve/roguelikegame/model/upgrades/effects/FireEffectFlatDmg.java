package com.grouptwelve.roguelikegame.model.upgrades.effects;

import com.grouptwelve.roguelikegame.model.effects.EffectInterface;
import com.grouptwelve.roguelikegame.model.effects.FireEffect;
import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

public class FireEffectFlatDmg implements UpgradeInterface {

    private final double extraBurn;

    public FireEffectFlatDmg(double extraBurn) {
        this.extraBurn = extraBurn;
    }

    @Override
    public void apply(Player player) {
        for (EffectInterface e : player.getWeapon().getEffects()) {
            if (e instanceof FireEffect fire) {
                fire.increaseDmg(extraBurn);
            }

        }
    }

    @Override
    public String getName() {
        return "Fire Effect Damage +" + (int)extraBurn;
    }
}



