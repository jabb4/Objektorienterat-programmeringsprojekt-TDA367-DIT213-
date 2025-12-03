package com.grouptwelve.roguelikegame.model.UpgradesPackage.EffectsUpgrades;

import com.grouptwelve.roguelikegame.model.EffectsPackage.EffectInterface;
import com.grouptwelve.roguelikegame.model.EffectsPackage.FireEffect;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic.AttributeUpgrade;

public class FireEffectFlatDuration extends AttributeUpgrade {

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
