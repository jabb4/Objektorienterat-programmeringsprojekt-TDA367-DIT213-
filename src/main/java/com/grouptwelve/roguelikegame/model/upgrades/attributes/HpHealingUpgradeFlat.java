package com.grouptwelve.roguelikegame.model.upgrades.attributes;

import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.upgrades.logic.FlatAttributeUpgrade;

public class HpHealingUpgradeFlat extends FlatAttributeUpgrade {

    public HpHealingUpgradeFlat(double amount) {
        super(amount);
    }

    @Override
    public void apply(Entity entity) {
        entity.heal(amount);

    }

    @Override
    public String getName() {
        return "+ " + (int)amount + " Heal";
    }
}