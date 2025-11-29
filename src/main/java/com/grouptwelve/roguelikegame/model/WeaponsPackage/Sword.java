package com.grouptwelve.roguelikegame.model.WeaponsPackage;

import com.grouptwelve.roguelikegame.model.EffectsPackage.HitEffect;
import com.grouptwelve.roguelikegame.model.EffectsPackage.KnockbackEffect;

public class Sword extends Weapon {
    private static final int SWORD_KNOCKBACK_STRENGTH = 2500; // Pixels per second
    private static final double HIT_FLASH_DURATION = 0.15; // 150ms white flash

    public Sword() {
        super(10, 30); // damage, range
        effects.add(new KnockbackEffect(SWORD_KNOCKBACK_STRENGTH));
        effects.add(new HitEffect(HIT_FLASH_DURATION));
    }
}
