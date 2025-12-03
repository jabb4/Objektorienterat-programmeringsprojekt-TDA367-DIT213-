package com.grouptwelve.roguelikegame.model.UpgradesPackage.AttributeUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic.FlatAttributeUpgrade;

public class SpeedFlatUpgradeFlat extends FlatAttributeUpgrade {

    public SpeedFlatUpgradeFlat(double amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.increaseMoveSpeed(amount);
    }

    @Override
    public String getName() {
        return "+ " + amount + " Move Speed";
    }
}
