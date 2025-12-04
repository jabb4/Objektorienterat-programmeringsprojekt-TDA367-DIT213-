package com.grouptwelve.roguelikegame.model.upgrades.weapons;

import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.upgrades.logic.FlatAttributeUpgrade;

public class WeaponCritMultiplierFlatUpgrade extends FlatAttributeUpgrade {

    public WeaponCritMultiplierFlatUpgrade(double amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.getWeapon().addCritMultiplier(amount);
    }

    @Override
    public String getName() {
        return "+ " + amount + " Crit Multiplier";
    }
}
