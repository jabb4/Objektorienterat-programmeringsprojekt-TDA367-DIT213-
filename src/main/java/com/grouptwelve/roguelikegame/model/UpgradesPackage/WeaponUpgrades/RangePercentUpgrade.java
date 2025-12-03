package com.grouptwelve.roguelikegame.model.UpgradesPackage.WeaponUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic.PercentAttributeUpgrade;

public class RangePercentUpgrade extends PercentAttributeUpgrade {

    public RangePercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Player player) {
        double extra = player.getWeapon().getRange() * percent;
        player.getWeapon().addRange(extra);
    }

    @Override
    public String getName() {
        return "Range +" + (percent * 100) + "%";
    }
}
