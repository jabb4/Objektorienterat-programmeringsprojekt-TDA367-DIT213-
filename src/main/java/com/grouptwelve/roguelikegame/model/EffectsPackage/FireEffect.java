package com.grouptwelve.roguelikegame.model.EffectsPackage;

import com.grouptwelve.roguelikegame.model.EffectsPackage.ActiveEffectPackage.DamageOverTime;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entity;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;

public class FireEffect extends Effects {

    private double dps;
    private double duration;

    public FireEffect(double dps, double duration) {
        this.dps = dps;
        this.duration = duration;
    }

    public void increaseDPS(double amount) {
        this.dps += amount;
    }

    @Override
    public void apply(Entity target) {
        if (target instanceof Player)
            return;//kunde bränna sig själv lol

        target.addEffect(new DamageOverTime(dps, duration));
    }
}


