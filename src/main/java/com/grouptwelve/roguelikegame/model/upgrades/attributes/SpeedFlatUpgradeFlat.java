package com.grouptwelve.roguelikegame.model.upgrades.attributes;

import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.upgrades.logic.FlatAttributeUpgrade;

public class SpeedFlatUpgradeFlat extends FlatAttributeUpgrade {

    public SpeedFlatUpgradeFlat(double amount) {
        super(amount);
    }

    @Override
    public void apply(Entity entity) {
        entity.increaseMoveSpeed(amount);
    }

    @Override
    public String getName() {
        return "+ " + (int)amount + " Move Speed";
    }
}
