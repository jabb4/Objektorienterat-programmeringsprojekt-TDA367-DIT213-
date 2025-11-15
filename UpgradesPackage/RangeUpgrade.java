package UpgradesPackage;

import Weapons.Weapon;

public class RangeUpgrade implements UpgradeInterface {

    private double extraRange;

    public RangeUpgrade(double extraRange) {
        this.extraRange = extraRange;
    }

    @Override
    public void applyTo(Weapon weapon) {
        weapon.addRange(extraRange);
    }
}
