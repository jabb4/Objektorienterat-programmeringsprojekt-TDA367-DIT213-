package com.grouptwelve.roguelikegame.model.upgrades.weapons;

import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.upgrades.logic.PercentAttributeUpgrade;

public class RangePercentUpgrade extends PercentAttributeUpgrade {

    public RangePercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Entity entity) {
        double extra = entity.getWeaponRange() * percent;
        entity.addWeaponRange(extra);
    }

    @Override
    public String getName() {
        return "Range +" + (int)(percent * 100) + "%";
    }
}
