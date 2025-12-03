package com.grouptwelve.roguelikegame.model.WeaponsPackage;

import com.grouptwelve.roguelikegame.model.EffectsPackage.EffectInterface;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;

import java.util.List;

public interface WeaponInterface {
  /**
   * Attempts to perform an attack at the specified position.
   *
   * @param isPlayer true if the attacker is the player, false for enemies
   * @param x x-coordinate of the attack point
   * @param y y-coordinate of the attack point
   * @return true if attack was performed, false if on cooldown
   */
  boolean attack(boolean isPlayer, double x, double y);

  /**
   * Updates the weapon state (e.g., cooldown timer).
   *
   * @param deltaTime Time since last update in seconds
   */
  void update(double deltaTime);

  /**
   * Checks if the weapon is ready to attack.
   *
   * @return true if weapon can attack, false if on cooldown
   */
  boolean canAttack();

  double getDamage();

  double getRange();

  List<EffectInterface> getEffects();

  void addEffect(EffectInterface effect);

  void upgrade(UpgradeInterface upgrade);
}
