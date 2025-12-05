package com.grouptwelve.roguelikegame.model.upgrades.attributes;

import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.upgrades.logic.PercentAttributeUpgrade;

public class SpeedPercentUpgrade extends PercentAttributeUpgrade {

    public SpeedPercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Player player) {
        double extra = player.getMoveSpeed() * percent;
        player.increaseMoveSpeed(extra);
    }

    @Override
    public String getName() {
        return "+ " + (int)(percent * 100) + "% Move Speed";
    }
}
