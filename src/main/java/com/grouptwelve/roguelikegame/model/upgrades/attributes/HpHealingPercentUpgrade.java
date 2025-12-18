package com.grouptwelve.roguelikegame.model.upgrades.attributes;

import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.upgrades.logic.PercentAttributeUpgrade;

public class HpHealingPercentUpgrade extends PercentAttributeUpgrade {

    public HpHealingPercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Entity entity) {
        entity.heal((entity.getMaxHP() * percent));

    }

    @Override
    public String getName() {
        return "+ " + (int)(percent*100) + "% HP";
    }
}
