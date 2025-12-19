package com.grouptwelve.roguelikegame.model.upgrades.logic;

import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

/**
 * Base class for upgrades that apply a percentage-based modifier.
 * <p>
 * Percent values are represented as decimals (e.g. 0.05 = 5%).
 */
public abstract class PercentAttributeUpgrade implements UpgradeInterface {
    protected final double percent;

    public PercentAttributeUpgrade(double percent) {
        this.percent = percent;
    }
}
