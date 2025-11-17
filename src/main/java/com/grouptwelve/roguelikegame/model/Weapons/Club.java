package com.grouptwelve.roguelikegame.model.Weapons;

public class Club extends Weapon {

    public Club() {
        super(20, 3); // damage, range
    }
/*
    @Override
    public void attack(Entitity attacker, Entitity target) {
        target.takeDamage(damage);

        // Apply effects
        for (EffectInterface e : effects) {
            e.apply(target);
        }
    }

 */
}