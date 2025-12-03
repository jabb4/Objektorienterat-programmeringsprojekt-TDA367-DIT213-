package com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic;

import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;

public abstract class AttributeUpgrade implements UpgradeInterface {
    protected final double amount;

    public AttributeUpgrade(double amount) {
        this.amount = amount;
    }
}
