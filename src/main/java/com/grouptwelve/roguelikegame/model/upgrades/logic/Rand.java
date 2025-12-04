package com.grouptwelve.roguelikegame.model.upgrades.logic;

import java.util.concurrent.ThreadLocalRandom;

public class Rand {

    public static double range(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static int rangeInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
