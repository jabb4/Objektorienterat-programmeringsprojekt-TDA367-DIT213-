package com.grouptwelve.roguelikegame.model.UpgradesPackage.WeaponUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic.FlatAttributeUpgrade;

public class WeaponCritChanceFlatUpgrade extends FlatAttributeUpgrade {

    public WeaponCritChanceFlatUpgrade(double amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.getWeapon().addCritChance(amount);
    }

    @Override
    public String getName() {
        return "+ " + (amount * 100) + "% Crit Chance";
    }
}
