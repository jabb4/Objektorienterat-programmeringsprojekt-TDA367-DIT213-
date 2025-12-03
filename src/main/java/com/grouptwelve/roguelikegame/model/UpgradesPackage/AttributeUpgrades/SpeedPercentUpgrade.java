package com.grouptwelve.roguelikegame.model.UpgradesPackage.AttributeUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic.PercentAttributeUpgrade;

public class SpeedPercentUpgrade extends PercentAttributeUpgrade {

    public SpeedPercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Player player) {
        double extra = player.getMoveSpeed() * percent;
        player.increaseMoveSpeed(extra);
    }

    @Override
    public String getName() {
        return "+ " + (percent * 100) + "% Move Speed";
    }
}
