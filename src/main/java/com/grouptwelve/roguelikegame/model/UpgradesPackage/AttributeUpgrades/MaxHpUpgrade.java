package com.grouptwelve.roguelikegame.model.UpgradesPackage.AttributeUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;

public class MaxHpUpgrade implements UpgradeInterface {

    private final double amount; // e.g. +10 max HP

    public MaxHpUpgrade(double amount) {
        this.amount = amount;
    }

    @Override
    public void apply(Player player) {

        // Increase max HP
        double oldMax = player.getMaxHP();
        double newMax = oldMax + amount;
        player.setMaxHP(newMax);

        // Also increase current HP by same amount
        player.setHp(player.getHp() + amount);
    }

    @Override
    public String getName() {
        return "Max HP +" + amount;
    }
}

