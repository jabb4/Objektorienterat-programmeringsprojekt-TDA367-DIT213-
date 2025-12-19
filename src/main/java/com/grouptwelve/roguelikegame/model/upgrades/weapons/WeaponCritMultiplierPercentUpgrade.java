package com.grouptwelve.roguelikegame.model.upgrades.weapons;

import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.upgrades.logic.PercentAttributeUpgrade;

public class WeaponCritMultiplierPercentUpgrade extends PercentAttributeUpgrade {

    public WeaponCritMultiplierPercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Entity entity) {
        double extra = entity.getWeaponCritMultiplier() * percent;
        entity.addWeaponCritMultiplier(extra);
    }

    @Override
    public String getName() {
        return "+ " + (int)(percent * 100) + "% Crit Damage";
    }
}
