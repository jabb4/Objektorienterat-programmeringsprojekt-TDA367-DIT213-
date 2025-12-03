package com.grouptwelve.roguelikegame.model.UpgradesPackage.WeaponUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic.AttributeUpgrade;

public class RangeFlatUpgrade extends AttributeUpgrade {

    public RangeFlatUpgrade(double amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.getWeapon().addRange(amount);
    }

    @Override
    public String getName() {
        return "Range +" + amount;
    }
}
