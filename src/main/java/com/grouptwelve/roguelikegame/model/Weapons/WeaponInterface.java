package com.grouptwelve.roguelikegame.model.Weapons;

import com.grouptwelve.roguelikegame.model.EffectsPackage.EffectInterface;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entities;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;

import java.util.List;

public interface WeaponInterface {
    void attack(boolean isPlayer, double x, double y);
    double getDamage();
    double getRange();
    List<EffectInterface> getEffects();
    void addEffect(EffectInterface effect);
    void upgrade(UpgradeInterface upgrade);
}
