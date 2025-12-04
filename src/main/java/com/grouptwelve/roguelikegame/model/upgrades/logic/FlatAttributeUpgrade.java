package com.grouptwelve.roguelikegame.model.upgrades.logic;

import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

public abstract class FlatAttributeUpgrade implements UpgradeInterface {
    protected final double amount;

    public FlatAttributeUpgrade(double amount) {
        this.amount = amount;
    }
}
