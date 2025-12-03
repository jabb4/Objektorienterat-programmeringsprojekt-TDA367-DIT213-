package com.grouptwelve.roguelikegame.model.UpgradesPackage.EffectsUpgrades;

import com.grouptwelve.roguelikegame.model.EffectsPackage.FireEffect;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;

public class FireEffectUpgrade implements UpgradeInterface {
    private final double dps;
    private final double duration;

    public FireEffectUpgrade(double dps, double duration) {
        this.dps = dps;
        this.duration = duration;
    }

    @Override
    public void apply(Player player) {
        player.getWeapon().addEffect(new FireEffect(dps, duration));
    }

    @Override
    public String getName() {
        return "Add Fire Effect (" + dps + " DPS for " + duration + "s)";
    }
}



