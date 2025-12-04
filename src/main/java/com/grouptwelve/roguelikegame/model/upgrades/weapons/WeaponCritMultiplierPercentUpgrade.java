package com.grouptwelve.roguelikegame.model.upgrades.weapons;

import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.upgrades.logic.PercentAttributeUpgrade;

public class WeaponCritMultiplierPercentUpgrade extends PercentAttributeUpgrade {

    public WeaponCritMultiplierPercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Player player) {
        double extra = player.getWeapon().getCritMultiplier() * percent;
        player.getWeapon().addCritMultiplier(extra);
    }

    @Override
    public String getName() {
        return "+ " + (percent * 100) + "% Crit Multiplier";
    }
}
