package com.grouptwelve.roguelikegame.model.UpgradesPackage;

import com.grouptwelve.roguelikegame.model.Weapons.Weapon;

public class DamageUpgrade implements UpgradeInterface {

    private double extraDamage;

    public DamageUpgrade(double extraDamage) {
        this.extraDamage = extraDamage;
    }

    @Override
    public void applyTo(Weapon weapon) {
        weapon.addDamage(extraDamage);
    }
}

