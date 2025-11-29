package com.grouptwelve.roguelikegame.model.UpgradesPackage;

import com.grouptwelve.roguelikegame.model.WeaponsPackage.Weapon;

public class RangeUpgrade implements UpgradeInterface {

    private final double extraRange;

    public RangeUpgrade(double extraRange) {
        this.extraRange = extraRange;
    }

    @Override
    public void applyTo(Weapon weapon) {
        weapon.addRange(extraRange);
    }
}
