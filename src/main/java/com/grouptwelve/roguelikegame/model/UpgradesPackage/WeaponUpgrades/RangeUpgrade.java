package com.grouptwelve.roguelikegame.model.UpgradesPackage.WeaponUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;

public class RangeUpgrade implements UpgradeInterface {

    private final double extraRange;

    public RangeUpgrade(double extraRange) {
        this.extraRange = extraRange;
    }

    @Override
    public void apply(Player player) {
        player.getWeapon().addRange(extraRange);
    }

    @Override
    public String getName() {
        return "Weapon Range +" + extraRange;
    }
}

