package com.grouptwelve.roguelikegame.model.level;

public class LevelSystem {

    private static final int BASE_XP_TO_LEVEL = 100;
    private static final double XP_SCALING_FACTOR = 1.25;

    private int level = 1;
    private int xp = 0;
    private int xpToNext = BASE_XP_TO_LEVEL;

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

