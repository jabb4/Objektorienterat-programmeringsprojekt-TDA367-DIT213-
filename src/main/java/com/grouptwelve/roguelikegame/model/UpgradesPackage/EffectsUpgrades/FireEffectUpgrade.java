package com.grouptwelve.roguelikegame.model.UpgradesPackage.EffectsUpgrades;

import com.grouptwelve.roguelikegame.model.EffectsPackage.FireEffect;
import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeInterface;

public class FireEffectUpgrade implements UpgradeInterface {

    private final double burnDamage;

    public FireEffectUpgrade(double burnDamage) {
        this.burnDamage = burnDamage;
    }

    @Override
    public void apply(Player player) {
        player.getWeapon().addEffect(new FireEffect(burnDamage));
    }

    @Override
    public String getName() {
        return "Add Fire Effect (" + burnDamage + " burn)";
    }
}


