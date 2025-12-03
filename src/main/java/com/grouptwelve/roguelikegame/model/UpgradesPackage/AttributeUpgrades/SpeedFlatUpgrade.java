package com.grouptwelve.roguelikegame.model.UpgradesPackage.AttributeUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic.AttributeUpgrade;

public class SpeedFlatUpgrade extends AttributeUpgrade {

    public SpeedFlatUpgrade(double amount) {
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
