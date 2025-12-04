package com.grouptwelve.roguelikegame.model.upgrades.weapons;

import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.upgrades.logic.PercentAttributeUpgrade;

public class RangePercentUpgrade extends PercentAttributeUpgrade {

    public RangePercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Player player) {
        double extra = player.getWeapon().getRange() * percent;
        player.getWeapon().addRange(extra);
    }

    @Override
    public String getName() {
        return "Range +" + (percent * 100) + "%";
    }
}
