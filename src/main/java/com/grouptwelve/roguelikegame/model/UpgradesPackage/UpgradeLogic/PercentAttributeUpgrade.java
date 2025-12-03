package com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic;

import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;

public abstract class PercentAttributeUpgrade implements UpgradeInterface {
    protected final double percent; // 0.05 = 5%

    public PercentAttributeUpgrade(double percent) {
        this.percent = percent;
    }
}

