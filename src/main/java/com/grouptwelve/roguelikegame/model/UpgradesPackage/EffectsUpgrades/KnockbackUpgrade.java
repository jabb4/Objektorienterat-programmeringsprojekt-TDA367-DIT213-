package com.grouptwelve.roguelikegame.model.UpgradesPackage.EffectsUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;

public class KnockbackUpgrade implements UpgradeInterface {

    private final double extraStrength;

    public KnockbackUpgrade(double amount) {
        this.extraStrength = amount;
    }

    @Override
    public void apply(Player player) {
        player.getWeapon().addKnockback(extraStrength);
    }

    @Override
    public String getName() {
        return "Knockback +" + extraStrength;
    }
}
