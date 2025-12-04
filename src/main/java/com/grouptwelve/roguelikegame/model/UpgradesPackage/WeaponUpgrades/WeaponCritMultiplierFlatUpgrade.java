package com.grouptwelve.roguelikegame.model.UpgradesPackage.WeaponUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic.FlatAttributeUpgrade;

public class WeaponCritMultiplierFlatUpgrade extends FlatAttributeUpgrade {

    public WeaponCritMultiplierFlatUpgrade(double amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.getWeapon().addCritMultiplier(amount);
    }

    @Override
    public String getName() {
        return "+ " + amount + " Crit Multiplier";
    }
}
