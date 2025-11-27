package com.grouptwelve.roguelikegame.model.Weapons;

import com.grouptwelve.roguelikegame.model.EffectsPackage.KnockbackEffect;

public class Club extends Weapon {

    public Club() {
        super(20, 40); // damage, range
        effects.add(new KnockbackEffect(4000));
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