package Weapons;

import EffectsPackage.EffectInterface;
import EntitiesPackage.Entities;

public class Sword extends Weapon {

    public Sword() {
        super(10, 2); // damage, range
    }

    @Override
    public void attack(Entities attacker, Entities target) {
        target.takeDamage(damage);

        // Apply effects
        for (EffectInterface e : effects) {
            e.apply(target);
        }
    }
}
