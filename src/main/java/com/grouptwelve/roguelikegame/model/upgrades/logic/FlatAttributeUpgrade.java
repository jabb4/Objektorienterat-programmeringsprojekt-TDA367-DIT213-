package com.grouptwelve.roguelikegame.model.upgrades.logic;

import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

/**
 * Base class for upgrades that apply a flat (absolute) value.
 */
public abstract class FlatAttributeUpgrade implements UpgradeInterface {

    protected final double amount;

    public FlatAttributeUpgrade(double amount) {
        this.amount = amount;
    }
}
