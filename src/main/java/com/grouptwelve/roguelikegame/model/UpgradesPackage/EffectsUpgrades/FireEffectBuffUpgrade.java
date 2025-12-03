package com.grouptwelve.roguelikegame.model.UpgradesPackage.EffectsUpgrades;

import com.grouptwelve.roguelikegame.model.EffectsPackage.EffectInterface;
import com.grouptwelve.roguelikegame.model.EffectsPackage.FireEffect;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;

public class FireEffectBuffUpgrade implements UpgradeInterface {

    private final double extraBurn;

    public FireEffectBuffUpgrade(double extraBurn) {
        this.extraBurn = extraBurn;
    }

    @Override
    public void apply(Player player) {
        for (EffectInterface e : player.getWeapon().getEffects()) {
            if (e instanceof FireEffect fire)
            {
                fire.increaseDamage(extraBurn);
            }
        }
    }

    @Override
    public String getName() {
        return "Fire Effect Damage +" + extraBurn;
    }
}



