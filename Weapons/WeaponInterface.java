package Weapons;

import EffectsPackage.EffectInterface;
import EntitiesPackage.Entities;
import UpgradesPackage.UpgradeInterface;

import java.util.List;

public interface WeaponInterface {
    void attack(Entities attacker, Entities target);
    double getDamage();
    double getRange();
    List<EffectInterface> getEffects();
    void addEffect(EffectInterface effect);
    void upgrade(UpgradeInterface upgrade);
}
