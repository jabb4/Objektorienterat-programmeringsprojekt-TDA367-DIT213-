package com.grouptwelve.roguelikegame.model.UpgradesPackage.EffectsUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic.PercentAttributeUpgrade;

public class KnockbackPercentUpgrade extends PercentAttributeUpgrade {

    public KnockbackPercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Player player) {
        double extra = player.getWeapon().getKnockbackStrength() * percent;
        player.getWeapon().addKnockback(extra);
    }

    @Override
    public String getName() {
        return "+ " + (percent * 100) + "% Knockback";
    }
}
