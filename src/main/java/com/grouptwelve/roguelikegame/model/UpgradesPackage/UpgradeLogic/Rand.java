package com.grouptwelve.roguelikegame.model.UpgradesPackage.UpgradeLogic;

import java.util.concurrent.ThreadLocalRandom;//Thread va mycket bättre än javas random

public class Rand {

    public static double range(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static int rangeInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
