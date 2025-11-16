package com.grouptwelve.roguelikegame.model.Weapons;

import com.grouptwelve.roguelikegame.model.EffectsPackage.EffectInterface;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;

import java.util.ArrayList;
import java.util.List;

public abstract class Weapon implements WeaponInterface {

    protected double damage;
    protected double range;
    protected List<EffectInterface> effects = new ArrayList<>();

    public Weapon(double damage, double range) {
        this.damage = damage;
        this.range = range;
    }
    @Override
    public void attack(boolean isPlayer, double x, double y)
    {
        CombatManager.getInstance().attack(isPlayer, x, y, range, damage, effects);
    }

    @Override
    public double getDamage() { return damage; }

    public void addDamage(double amount) {
        this.damage += amount;
    }
    @Override
    public double getRange() { return range; }

    public void addRange(double amount) {
        this.range += amount;
    }

    @Override
    public List<EffectInterface> getEffects() { return effects; }

    @Override
    public void addEffect(EffectInterface effect) {
        effects.add(effect);
    }

    @Override
    public void upgrade(UpgradeInterface upgrade) {
        upgrade.applyTo(this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [DMG=" + damage + ", RANGE=" + range + "]";
    }

}
