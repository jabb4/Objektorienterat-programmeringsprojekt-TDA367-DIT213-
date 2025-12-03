package com.grouptwelve.roguelikegame.model.UpgradesPackage.WeaponUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic.PercentAttributeUpgrade;

public class WeaponDamagePercentUpgrade extends PercentAttributeUpgrade {

    public WeaponDamagePercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Player player) {
        double extra = player.getWeapon().getDamage() * percent;
        player.getWeapon().addDamage(extra);
    }

    @Override
    public String getName() {
        return "+ " + (percent * 100) + "% Weapon Damage";
    }
}
