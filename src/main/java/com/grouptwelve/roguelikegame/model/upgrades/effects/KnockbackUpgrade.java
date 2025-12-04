package com.grouptwelve.roguelikegame.model.upgrades.effects;

import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

public class KnockbackUpgrade implements UpgradeInterface {

    private final double extraStrength;

    public KnockbackUpgrade(double amount) {
        this.extraStrength = amount;
    }

    @Override
    public void apply(Player player) {
        player.getWeapon().addKnockback(extraStrength);
    }

    @Override
    public String getName() {
        return "Knockback +" + extraStrength;
    }
}
