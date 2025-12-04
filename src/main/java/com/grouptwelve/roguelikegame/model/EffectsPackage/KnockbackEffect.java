package com.grouptwelve.roguelikegame.model.EffectsPackage;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entity;

public class KnockbackEffect extends Effects {

    private double dirX;
    private double dirY;

    public KnockbackEffect() {
        // No strength here anymore — weapon handles that!
    }

    public void setDirection(double x, double y) {
        double len = Math.sqrt(x * x + y * y);
        if (len != 0) {
            dirX = x / len;
            dirY = y / len;
        } else {
            dirX = 0;
            dirY = 0;
        }
    }

    @Override
    public void apply(Entity target) {
        double strength = target.getWeapon().getKnockbackStrength();
        target.applyKnockback(dirX, dirY, strength);
    }
}
