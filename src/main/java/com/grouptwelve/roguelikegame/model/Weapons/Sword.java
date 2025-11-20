package com.grouptwelve.roguelikegame.model.Weapons;

public class Sword extends Weapon {

    public Sword() {
        super(10, 30); // damage, range
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
