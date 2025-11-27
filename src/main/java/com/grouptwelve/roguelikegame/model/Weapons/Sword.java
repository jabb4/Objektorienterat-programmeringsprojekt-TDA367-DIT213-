package com.grouptwelve.roguelikegame.model.Weapons;

import com.grouptwelve.roguelikegame.model.EffectsPackage.KnockbackEffect;

public class Sword extends Weapon {
    private static final int SWORD_KNOCKBACK_STRENGTH = 2500; // Pixels per second

    public Sword() {
        super(10, 30); // damage, range
        effects.add(new KnockbackEffect(SWORD_KNOCKBACK_STRENGTH));
    }
}
