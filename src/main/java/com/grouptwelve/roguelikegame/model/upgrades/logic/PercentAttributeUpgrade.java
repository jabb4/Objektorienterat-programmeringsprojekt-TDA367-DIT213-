package com.grouptwelve.roguelikegame.model.upgrades.logic;

import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

public abstract class PercentAttributeUpgrade implements UpgradeInterface {
    protected final double percent; // 0.05 = 5%

    public PercentAttributeUpgrade(double percent) {
        this.percent = percent;
    }
}

