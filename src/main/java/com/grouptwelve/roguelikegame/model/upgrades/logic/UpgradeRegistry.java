package com.grouptwelve.roguelikegame.model.upgrades.logic;

import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;
import com.grouptwelve.roguelikegame.model.upgrades.attributes.*;
import com.grouptwelve.roguelikegame.model.upgrades.effects.*;
import com.grouptwelve.roguelikegame.model.upgrades.weapons.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Central registry for all possible upgrades in the game.
 * <p>
 * Stores upgrade factories rather than instances to ensure
 * fresh randomized values each time an upgrade is generated.
 */
public class UpgradeRegistry {

    private static final List<Supplier<UpgradeInterface>> upgrades = new ArrayList<>();

        static {
            // Weapon damage upgrades
            upgrades.add(() -> new WeaponDamageFlatUpgradeFlat(Rand.range(5, 15)));
            upgrades.add(() -> new WeaponDamagePercentUpgrade(Rand.range(0.03, 0.10)));

            // Movement speed upgrades
            upgrades.add(() -> new SpeedFlatUpgradeFlat(Rand.range(10, 30)));
            upgrades.add(() -> new SpeedPercentUpgrade(Rand.range(0.05, 0.15)));

            // Maximum health upgrades
            upgrades.add(() -> new MaxHpFlatUpgradeFlat(Rand.range(10, 40)));
            upgrades.add(() -> new MaxHpPercentUpgrade(Rand.range(0.05, 0.15)));

            // Healing upgrades
            upgrades.add(() -> new HpHealingUpgradeFlat(Rand.range(17, 35)));
            upgrades.add(() -> new HpHealingPercentUpgrade(Rand.range(0.10, 0.25)));

            // Fire effect upgrades
            upgrades.add(() -> new FireEffectAddToWeapon(Rand.rangeInt(1, 3), Rand.rangeInt(3, 5)));
            upgrades.add(() -> new FireEffectFlatDmg(Rand.range(1, 4)));
            upgrades.add(() -> new FireEffectFlatDuration(Rand.range(1, 3)));

            // Weapon range upgrades
            upgrades.add(() -> new RangeFlatUpgradeFlat(Rand.range(3, 10)));
            upgrades.add(() -> new RangePercentUpgrade(Rand.range(0.05, 0.15)));

            // Critical hit upgrades
            upgrades.add(() -> new WeaponCritChancePercentUpgrade(Rand.range(0.04, 0.13)));
            upgrades.add(() -> new WeaponCritMultiplierFlatUpgrade(Rand.range(0.2, 0.7)));
            upgrades.add(() -> new WeaponCritMultiplierPercentUpgrade(Rand.range(0.05, 0.15)));

            // Knockback upgrades
            upgrades.add(() -> new KnockbackFlatUpgrade(Rand.range(20, 60)));
            upgrades.add(() -> new KnockbackPercentUpgrade(Rand.range(0.10, 0.25)));
        }

    /**
     * Returns a randomly generated upgrade.
     *
     * @return a new upgrade instance
     */
    public static UpgradeInterface randomUpgrade() {
        int index = (int) (Math.random() * upgrades.size());
        return upgrades.get(index).get();
    }
}
