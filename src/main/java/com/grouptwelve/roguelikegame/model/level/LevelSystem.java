package com.grouptwelve.roguelikegame.model.level;

/**
 * Manages player leveling and experience progression.
 * <p>
 * Experience required to level up increases multiplicatively,
 * making each new level progressively harder to reach.
 */
public class LevelSystem {

    private static final int BASE_XP_TO_LEVEL = 100;
    private static final double XP_SCALING_FACTOR = 1.25;

    private int level = 1;
    private int xp = 0;
    private int xpToNext = BASE_XP_TO_LEVEL;

    /**
     * If accumulated XP reaches or exceeds the required amount,
     * the player levels up and excess XP is carried over.
     *
     * @param amount the amount of XP to add
     * @return {@code true} if a level-up occurred, otherwise {@code false}
     */
    public boolean addXP(int amount) {
        xp += amount;
        if (xp >= xpToNext) {
            xp -= xpToNext;
            level++;
            xpToNext = (int) (xpToNext * XP_SCALING_FACTOR);
            return true; // means leveled up
        }
        return false;
    }

    public int getLevel() { return level; }
    public int getXP() { return xp; }
    public int getXPToNext() { return xpToNext; }
}

