package com.grouptwelve.roguelikegame.model.upgrades.effects;

import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

public class KnockbackUpgrade implements UpgradeInterface {

    private final double extraStrength;

    public KnockbackUpgrade(double amount) {
        this.extraStrength = amount;
    }

    @Override
    public void apply(Entity entity) {
        entity.addWeaponKnockbackStrength(extraStrength);
    }

    @Override
    public String getName() {
        return "Knockback +" + (int)extraStrength;
    }
}
