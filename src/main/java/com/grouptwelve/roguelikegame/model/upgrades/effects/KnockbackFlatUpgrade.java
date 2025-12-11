package com.grouptwelve.roguelikegame.model.upgrades.effects;

import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.upgrades.logic.FlatAttributeUpgrade;

public class KnockbackFlatUpgrade extends FlatAttributeUpgrade {

    public KnockbackFlatUpgrade(double amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.getWeapon().addKnockback(amount);
    }

    @Override
    public String getName() {
        return "+ " + (int)amount + " Knockback";
    }
}
