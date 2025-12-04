package com.grouptwelve.roguelikegame.model.upgrades.weapons;

import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.upgrades.logic.FlatAttributeUpgrade;

public class WeaponDamageFlatUpgradeFlat extends FlatAttributeUpgrade {

    public WeaponDamageFlatUpgradeFlat(double amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.getWeapon().addDamage(amount);
    }

    @Override
    public String getName() {
        return "+ " + amount + " Weapon Damage";
    }
}
