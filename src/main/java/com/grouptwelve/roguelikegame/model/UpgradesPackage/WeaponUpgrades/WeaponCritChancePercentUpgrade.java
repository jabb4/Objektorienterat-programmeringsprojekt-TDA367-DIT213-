package com.grouptwelve.roguelikegame.model.UpgradesPackage.WeaponUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic.PercentAttributeUpgrade;

public class WeaponCritChancePercentUpgrade extends PercentAttributeUpgrade {

    public WeaponCritChancePercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Player player) {
        double extra = player.getWeapon().getCritChance() * percent;
        player.getWeapon().addCritChance(extra);
    }

    @Override
    public String getName() {
        return "+ " + (percent * 100) + "% Crit Chance (relative)";
    }
}
