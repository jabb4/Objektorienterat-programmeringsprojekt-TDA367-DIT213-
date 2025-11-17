package com.grouptwelve.roguelikegame.model.EffectsPackage;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entitity;

public class FireEffect extends Effects {

    private double burnDamage;

    public FireEffect(double burnDamage) {
        this.burnDamage = burnDamage;
    }

    @Override
    public void apply(Entitity target) {
        target.takeDamage(burnDamage);
        System.out.println(target.getName() + " is burning!");
    }
}
