package com.grouptwelve.roguelikegame.model.upgrades.effects;

import com.grouptwelve.roguelikegame.model.effects.FireEffect;
import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

public class FireEffectAddToWeapon implements UpgradeInterface {
    private final double dps;
    private final double duration;

    public FireEffectAddToWeapon(double dps, double duration) {
        this.dps = dps;
        this.duration = duration;
    }

    @Override
    public void apply(Player player) {
        player.getWeapon().addEffect(new FireEffect(dps, duration));
    }

    @Override
    public String getName() {
        return "Add Fire Effect (" + (int)dps + " DPS for " + (int)duration + "s)";
    }
}



