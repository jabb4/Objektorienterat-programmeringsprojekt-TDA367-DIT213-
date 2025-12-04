package com.grouptwelve.roguelikegame.model.UpgradesPackage.EffectsUpgrades;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic.FlatAttributeUpgrade;

public class KnockbackFlatUpgrade extends FlatAttributeUpgrade {

    public KnockbackFlatUpgrade(double amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.getWeapon().addKnockback(amount);
    }

    @Override
    public String getName() {
        return "+ " + amount + " Knockback";
    }
}
