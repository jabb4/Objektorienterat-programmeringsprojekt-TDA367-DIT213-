package com.grouptwelve.roguelikegame.model.effects;

import com.grouptwelve.roguelikegame.model.effects.active.DamageOverTime;
import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.entities.Player;

public class FireEffect extends Effects {

    private double dps;
    private double duration;

    public FireEffect(double dps, double duration) {
        this.dps = dps;
        this.duration = duration;
    }

    public void increaseDmg(double amount) {
        this.dps += amount;
    }
    public void increaseDuration(double amount) {
        this.dps += amount;
    }


    @Override
    public void apply(Entity target) {
        if (target instanceof Player)
            return;//kunde bränna sig själv lol

        target.addEffect(new DamageOverTime(dps, duration));
    }
}


