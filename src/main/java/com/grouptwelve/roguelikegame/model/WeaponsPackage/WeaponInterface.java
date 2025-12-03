package com.grouptwelve.roguelikegame.model.WeaponsPackage;

import com.grouptwelve.roguelikegame.model.EffectsPackage.EffectInterface;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;

import java.util.List;

public interface WeaponInterface {
    void attack(boolean isPlayer, double x, double y);
    double getDamage();
    double getRange();
    void addRange(double amount);
    List<EffectInterface> getEffects();
    void addEffect(EffectInterface effect);
    //void upgrade(UpgradeInterface upgrade);
}
