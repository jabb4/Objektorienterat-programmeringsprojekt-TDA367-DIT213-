package com.grouptwelve.roguelikegame.model.UpgradesPackage.WeaponUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;

public class DamageUpgrade implements UpgradeInterface {

    private final double extraDamage;

    public DamageUpgrade(double extraDamage) {
        this.extraDamage = extraDamage;
    }

    @Override
    public void apply(Player player) {
        player.getWeapon().addDamage(extraDamage);
    }

    @Override
    public String getName() {
        return "Weapon Damage +" + extraDamage;
    }
}

