package com.grouptwelve.roguelikegame.model.upgrades.effects;

import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.upgrades.logic.PercentAttributeUpgrade;

public class KnockbackPercentUpgrade extends PercentAttributeUpgrade {

    public KnockbackPercentUpgrade(double percent) {
        super(percent);
    }

    @Override
    public void apply(Player player) {
        double extra = player.getWeapon().getKnockbackStrength() * percent;
        player.getWeapon().addKnockback(extra);
    }

    @Override
    public String getName() {
        return "+ " + (percent * 100) + "% Knockback";
    }
}
