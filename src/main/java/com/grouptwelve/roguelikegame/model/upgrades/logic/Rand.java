package com.grouptwelve.roguelikegame.model.upgrades.logic;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility class for generating random values within ranges.
 */
public class Rand {

    /**
     * Returns a random double within the given range.
     *
     * @param min inclusive lower bound
     * @param max exclusive upper bound
     * @return a random double between min and max
     */
    public static double range(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    /**
     * Returns a random integer within the given range.
     *
     * @param min inclusive lower bound
     * @param max exclusive upper bound
     * @return a random integer between min and max
     */
    public static int rangeInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
