package com.grouptwelve.roguelikegame.model.UpgradesPackage.AttributeUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic.PercentAttributeUpgrade;

public class MaxHpPercentUpgrade extends PercentAttributeUpgrade {

    public MaxHpPercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Player player) {
        double extra = player.getMaxHP() * percent;

        player.setMaxHP(player.getMaxHP() + extra);
        player.setHp(player.getHp() + extra); // heal by the same amount
    }

    @Override
    public String getName() {
        return "+ " + (percent * 100) + "% Max HP";
    }
}
