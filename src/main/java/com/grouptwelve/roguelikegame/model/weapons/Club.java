package com.grouptwelve.roguelikegame.model.weapons;

import com.grouptwelve.roguelikegame.model.effects.HitEffect;
import com.grouptwelve.roguelikegame.model.effects.KnockbackEffect;

public class Club extends Weapon {
    private static final double CLUB_DAMAGE = 20;
    private static final double CLUB_RANGE = 40;
    private static final double CLUB_COOLDOWN = 0.6; // Attack cooldown in seconds
    private static final double CLUB_KNOCKBACK = 2000; // Pixels per second
    private static final double HIT_FLASH_DURATION = 0.15; // 150ms white flash

    public Club() {
        super(CLUB_DAMAGE, CLUB_RANGE, CLUB_COOLDOWN, CLUB_KNOCKBACK);

        effects.add(new KnockbackEffect());
        effects.add(new HitEffect(HIT_FLASH_DURATION));
    }

    @Override
    public Weapon copy() {
        return new Club();
    }
}