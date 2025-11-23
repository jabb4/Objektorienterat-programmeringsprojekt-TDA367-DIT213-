package com.grouptwelve.roguelikegame.model.Weapons;

public class Club extends Weapon {

    public Club() {
        super(20, 40); // damage, range
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