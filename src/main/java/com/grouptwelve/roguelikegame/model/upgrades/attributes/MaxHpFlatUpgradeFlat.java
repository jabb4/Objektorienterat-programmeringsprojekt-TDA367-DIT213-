package com.grouptwelve.roguelikegame.model.upgrades.attributes;

import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.upgrades.logic.FlatAttributeUpgrade;

public class MaxHpFlatUpgradeFlat extends FlatAttributeUpgrade {

    public MaxHpFlatUpgradeFlat(double amount) {
        super(amount);
    }

    @Override
    public void apply(Entity entity) {
        entity.setMaxHP(entity.getMaxHP() + amount);
        entity.setHp(entity.getHp() + amount); // also heal the flat amount
    }

    @Override
    public String getName() {
        return "+ " + (int)amount + " Max HP";
    }
}
