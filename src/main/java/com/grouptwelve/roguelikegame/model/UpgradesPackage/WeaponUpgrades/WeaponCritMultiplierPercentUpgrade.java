package com.grouptwelve.roguelikegame.model.UpgradesPackage.WeaponUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic.PercentAttributeUpgrade;

public class WeaponCritMultiplierPercentUpgrade extends PercentAttributeUpgrade {

    public WeaponCritMultiplierPercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Player player) {
        double extra = player.getWeapon().getCritMultiplier() * percent;
        player.getWeapon().addCritMultiplier(extra);
    }

    @Override
    public String getName() {
        return "+ " + (percent * 100) + "% Crit Multiplier";
    }
}
