package com.grouptwelve.roguelikegame.model.weapons;

import com.grouptwelve.roguelikegame.model.effects.HitEffect;
import com.grouptwelve.roguelikegame.model.effects.KnockbackEffect;

public class Sword extends Weapon {
    private static final double SWORD_DAMAGE = 10;
    private static final double SWORD_RANGE = 30;
    private static final double SWORD_COOLDOWN = 0.3; // Attack cooldown in seconds
    private static final double SWORD_KNOCKBACK = 1000; // Initial knockback velocity in pixels per second
    private static final double HIT_FLASH_DURATION = 0.15; // Flash in seconds

    public Sword() {
        super(SWORD_DAMAGE, SWORD_RANGE, SWORD_COOLDOWN, SWORD_KNOCKBACK);

        effects.add(new KnockbackEffect());
        effects.add(new HitEffect(HIT_FLASH_DURATION));
    }

    @Override
    public Weapon copy() {
        return new Sword();
    }
}
