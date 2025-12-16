package com.grouptwelve.roguelikegame.model.upgrades.weapons;

import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.upgrades.logic.PercentAttributeUpgrade;

public class WeaponDamagePercentUpgrade extends PercentAttributeUpgrade {

    public WeaponDamagePercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Entity entity) {
        double extra = entity.getWeaponDamage() * percent;
        entity.addWeaponDamage(extra);
    }

    @Override
    public String getName() {
        return "+ " + (int)(percent * 100) + "% Weapon Damage";
    }
}
