package com.grouptwelve.roguelikegame.model.UpgradesPackage.AttributeUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic.AttributeUpgrade;

public class MaxHpFlatUpgrade extends AttributeUpgrade {

    public MaxHpFlatUpgrade(double amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.setMaxHP(player.getMaxHP() + amount);
        player.setHp(player.getHp() + amount); // also heal the flat amount
    }

    @Override
    public String getName() {
        return "+ " + amount + " Max HP";
    }
}
