package com.grouptwelve.roguelikegame.model.UpgradesPackage.WeaponUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic.FlatAttributeUpgrade;

public class WeaponDamageFlatUpgradeFlat extends FlatAttributeUpgrade {

    public WeaponDamageFlatUpgradeFlat(double amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.getWeapon().addDamage(amount);
    }

    @Override
    public String getName() {
        return "+ " + amount + " Weapon Damage";
    }
}
