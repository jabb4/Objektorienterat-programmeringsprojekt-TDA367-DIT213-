package com.grouptwelve.roguelikegame.model.upgrades.weapons;

import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.upgrades.logic.PercentAttributeUpgrade;

public class WeaponCritChancePercentUpgrade extends PercentAttributeUpgrade {

    public WeaponCritChancePercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Player player) {
        double extra = player.getWeapon().getCritChance() * percent;
        player.getWeapon().addCritChance(extra);
    }

    @Override
    public String getName() {
        return "+ " + (int)(percent * 100) + "% Crit Chance (relative)";
    }
}
