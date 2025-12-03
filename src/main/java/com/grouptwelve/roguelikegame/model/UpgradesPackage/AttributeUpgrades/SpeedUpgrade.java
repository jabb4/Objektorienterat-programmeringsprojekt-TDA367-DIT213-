package com.grouptwelve.roguelikegame.model.UpgradesPackage.AttributeUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;

public class SpeedUpgrade implements UpgradeInterface {

    private final double amount; // e.g., +20 speed

    public SpeedUpgrade(double amount) {
        this.amount = amount;
    }

    @Override
    public void apply(Player player) {
        player.increaseMoveSpeed(amount);
    }

    @Override
    public String getName() {
        return "Move Speed +" + amount;
    }
}
