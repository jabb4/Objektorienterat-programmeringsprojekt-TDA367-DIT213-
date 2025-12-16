package com.grouptwelve.roguelikegame.model.upgrades.weapons;

import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.upgrades.logic.FlatAttributeUpgrade;

public class WeaponCritChanceFlatUpgrade extends FlatAttributeUpgrade {

    public WeaponCritChanceFlatUpgrade(double amount) {
        super(amount);
    }

    @Override
    public void apply(Entity entity) {
        entity.addWeaponCritChance(amount);
    }

    @Override
    public String getName() {
        return "+ " + (int)(amount * 100) + "% Crit Chance";
    }
}
