package com.grouptwelve.roguelikegame.model.upgrades.weapons;

import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.upgrades.logic.FlatAttributeUpgrade;

public class WeaponCritChanceFlatUpgrade extends FlatAttributeUpgrade {

    public WeaponCritChanceFlatUpgrade(double amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.getWeapon().addCritChance(amount);
    }

    @Override
    public String getName() {
        return "+ " + (amount * 100) + "% Crit Chance";
    }
}
