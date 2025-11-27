package com.grouptwelve.roguelikegame.model.Weapons;

import com.grouptwelve.roguelikegame.model.EffectsPackage.KnockbackEffect;

public class Club extends Weapon {
    private static final int KNOCKBACK_STRENGTH = 4000; // Pixels per second

    public Club() {
        super(20, 40); // damage, range
        effects.add(new KnockbackEffect(KNOCKBACK_STRENGTH));
    }
/*
    @Override
    public void attack(Entity attacker, Entity target) {
        target.takeDamage(damage);

        // Apply effects
        for (EffectInterface e : effects) {
            e.apply(target);
        }
    }

 */
}