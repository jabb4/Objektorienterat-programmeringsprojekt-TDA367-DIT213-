package com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic;

import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;

public abstract class FlatAttributeUpgrade implements UpgradeInterface {
    protected final double amount;

    public FlatAttributeUpgrade(double amount) {
        this.amount = amount;
    }
}
