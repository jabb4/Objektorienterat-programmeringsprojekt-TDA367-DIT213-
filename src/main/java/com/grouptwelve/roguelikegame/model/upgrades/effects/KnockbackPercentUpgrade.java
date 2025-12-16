package com.grouptwelve.roguelikegame.model.upgrades.effects;

import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.upgrades.logic.PercentAttributeUpgrade;

public class KnockbackPercentUpgrade extends PercentAttributeUpgrade {

    public KnockbackPercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Entity entity) {
        double extra = entity.getWeaponKnockbackStrength() * percent;
        entity.addWeaponKnockbackStrength(extra);
    }

    @Override
    public String getName() {
        return "+ " + (int)(percent * 100) + "% Knockback";
    }
}
