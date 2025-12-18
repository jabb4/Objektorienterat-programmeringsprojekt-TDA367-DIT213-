package com.grouptwelve.roguelikegame.model.upgrades.logic;

import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;
import com.grouptwelve.roguelikegame.model.upgrades.attributes.*;
import com.grouptwelve.roguelikegame.model.upgrades.effects.*;
import com.grouptwelve.roguelikegame.model.upgrades.weapons.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class UpgradeRegistry {

    private static final List<Supplier<UpgradeInterface>> upgrades = new ArrayList<>();

    static {

        // Damage upgrades (flat 5–15, percent 3–10%)
        upgrades.add(() -> new WeaponDamageFlatUpgradeFlat(
                Rand.range(5, 15)
        ));
        upgrades.add(() -> new WeaponDamagePercentUpgrade(
                Rand.range(0.03, 0.10)
        ));

        // Speed upgrades (flat 10–30, percent 5–15%)
        upgrades.add(() -> new SpeedFlatUpgradeFlat(
                Rand.range(10, 30)
        ));
        upgrades.add(() -> new SpeedPercentUpgrade(
                Rand.range(0.05, 0.15)
        ));

        // Max HP (flat 10–40, percent 5–15%)
        upgrades.add(() -> new MaxHpFlatUpgradeFlat(
                Rand.range(10, 40)
        ));
        upgrades.add(() -> new MaxHpPercentUpgrade(
                Rand.range(0.05, 0.15)
        ));

        // Fire Effect (dps 1–4, duration 1–3 seconds)
        upgrades.add(() -> new FireEffectAddToWeapon(
                Rand.rangeInt(1, 3),
                Rand.rangeInt(3, 5)
        ));

        upgrades.add(() -> new FireEffectFlatDmg(
                Rand.range(1, 4)
        ));
        upgrades.add(() -> new FireEffectFlatDuration(
                Rand.range(1, 3)
        ));

        // Range (flat 3–10, percent 5–15%)
        upgrades.add(() -> new RangeFlatUpgradeFlat(
                Rand.range(3, 10)
        ));
        upgrades.add(() -> new RangePercentUpgrade(
                Rand.range(0.05, 0.15)
        ));

        upgrades.add(() -> new WeaponCritChanceFlatUpgrade(Rand.range(0.02, 0.08)));   // +2% to +8%
        upgrades.add(() -> new WeaponCritChancePercentUpgrade(Rand.range(0.05, 0.20))); // +5%–20% relative

        upgrades.add(() -> new WeaponCritMultiplierFlatUpgrade(Rand.range(0.1, 0.4))); // +0.1 to +0.4
        upgrades.add(() -> new WeaponCritMultiplierPercentUpgrade(Rand.range(0.05, 0.15))); // +5%–15%

        upgrades.add(() -> new KnockbackFlatUpgrade(Rand.range(20, 60)));
        upgrades.add(() -> new KnockbackPercentUpgrade(Rand.range(0.10, 0.25)));

        upgrades.add(() -> new HpHealingUpgradeFlat(Rand.range(5, 25)));
        upgrades.add(() -> new HpHealingPercentUpgrade(Rand.range(0.10, 0.25)));


    }

    public static UpgradeInterface randomUpgrade() {
        int index = (int)(Math.random() * upgrades.size());
        return upgrades.get(index).get();
    }
}
