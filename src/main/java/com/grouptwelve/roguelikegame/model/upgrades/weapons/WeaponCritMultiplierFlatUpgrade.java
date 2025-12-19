package com.grouptwelve.roguelikegame.model.upgrades.weapons;

import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.upgrades.logic.FlatAttributeUpgrade;

public class WeaponCritMultiplierFlatUpgrade extends FlatAttributeUpgrade {

    public WeaponCritMultiplierFlatUpgrade(double amount) {
        super(amount);
    }

    @Override
    public void apply(Entity entity) {
        entity.addWeaponCritMultiplier(amount);
    }

    @Override
    public String getName() {
        return String.format("+ %.1f Crit Damage", amount);
    }

}
