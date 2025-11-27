package com.grouptwelve.roguelikegame.model.Weapons;

import com.grouptwelve.roguelikegame.model.EffectsPackage.KnockbackEffect;

public class Sword extends Weapon {

    public Sword() {
        super(10, 30); // damage, range
        effects.add(new KnockbackEffect(2500));
    }
}
