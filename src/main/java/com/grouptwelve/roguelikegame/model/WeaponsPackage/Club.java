package com.grouptwelve.roguelikegame.model.WeaponsPackage;

import com.grouptwelve.roguelikegame.model.EffectsPackage.HitEffect;
import com.grouptwelve.roguelikegame.model.EffectsPackage.KnockbackEffect;

public class Club extends Weapon {
    private static final double CLUB_DAMAGE = 20;
    private static final double CLUB_RANGE = 40;
    private static final double CLUB_COOLDOWN = 0.6; // Attack cooldown in seconds
    private static final int KNOCKBACK_STRENGTH = 4000; // Pixels per second
    private static final double HIT_FLASH_DURATION = 0.15; // 150ms white flash

    public Club() {
        super(CLUB_DAMAGE, CLUB_RANGE, CLUB_COOLDOWN);
        effects.add(new KnockbackEffect(KNOCKBACK_STRENGTH));
        effects.add(new HitEffect(HIT_FLASH_DURATION));
    }
}