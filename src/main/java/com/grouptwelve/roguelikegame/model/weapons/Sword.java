package com.grouptwelve.roguelikegame.model.weapons;

//import com.grouptwelve.roguelikegame.model.effects.HitEffect;
import com.grouptwelve.roguelikegame.model.effects.KnockbackEffect;
/**
 * subclass of weapon with concrete values . Lower power but also lower cooldown
 */
public class Sword extends Weapon {
    private static final double SWORD_DAMAGE = 10;
    private static final double SWORD_RANGE = 30;
    private static final double SWORD_COOLDOWN = 0.3; // Attack cooldown in seconds
    private static final double SWORD_KNOCKBACK = 1000; // Initial knockback velocity in pixels per second


    public Sword() {
        super(SWORD_DAMAGE, SWORD_RANGE, SWORD_COOLDOWN, SWORD_KNOCKBACK);

        effects.add(new KnockbackEffect());
    }

    @Override
    public Weapon copy() {
        return new Sword();
    }
}
