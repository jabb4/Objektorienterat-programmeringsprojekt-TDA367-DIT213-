package com.grouptwelve.roguelikegame.model.upgrades.attributes;

import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.upgrades.logic.PercentAttributeUpgrade;

public class SpeedPercentUpgrade extends PercentAttributeUpgrade {

    public SpeedPercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Entity entity) {
        double extra = entity.getMoveSpeed() * percent;
        entity.increaseMoveSpeed(extra);
    }

    @Override
    public String getName() {
        return "+ " + (int)(percent * 100) + "% Move Speed";
    }
}
