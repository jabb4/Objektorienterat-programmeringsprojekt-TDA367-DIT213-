package com.grouptwelve.roguelikegame.model.upgrades.attributes;

import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.upgrades.logic.PercentAttributeUpgrade;

public class MaxHpPercentUpgrade extends PercentAttributeUpgrade {

    public MaxHpPercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Entity entity) {
        double extra = entity.getMaxHP() * percent;

        entity.setMaxHP(entity.getMaxHP() + extra);
        entity.setHp(entity.getHp() + extra); // heal by the same amount
    }

    @Override
    public String getName() {
        return "+ " + (int) (percent * 100) + "% Max HP";
    }
}
