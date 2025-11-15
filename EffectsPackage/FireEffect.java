package EffectsPackage;

import EntitiesPackage.Entities;

public class FireEffect extends Effects {

    private double burnDamage;

    public FireEffect(double burnDamage) {
        this.burnDamage = burnDamage;
    }

    @Override
    public void apply(Entities target) {
        target.takeDamage(burnDamage);
        System.out.println(target.getName() + " is burning!");
    }
}
