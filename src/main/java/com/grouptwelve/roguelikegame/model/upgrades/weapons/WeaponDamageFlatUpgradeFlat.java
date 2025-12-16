package com.grouptwelve.roguelikegame.model.upgrades.weapons;

import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.upgrades.logic.FlatAttributeUpgrade;

public class WeaponDamageFlatUpgradeFlat extends FlatAttributeUpgrade {

    public WeaponDamageFlatUpgradeFlat(double amount) {
        super(amount);
    }

    @Override
    public void apply(Entity entity) {
        entity.addWeaponDamage(amount);
    }

    @Override
    public String getName() {
        return "+ " + (int)amount + " Weapon Damage";
    }
}
