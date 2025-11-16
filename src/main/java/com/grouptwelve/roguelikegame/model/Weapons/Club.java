package com.grouptwelve.roguelikegame.model.Weapons;

import com.grouptwelve.roguelikegame.model.EffectsPackage.EffectInterface;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entities;

public class Club extends Weapon {

    public Club() {
        super(20, 3); // damage, range
    }
/*
    @Override
    public void attack(Entities attacker, Entities target) {
        target.takeDamage(damage);

        // Apply effects
        for (EffectInterface e : effects) {
            e.apply(target);
        }
    }

 */
}