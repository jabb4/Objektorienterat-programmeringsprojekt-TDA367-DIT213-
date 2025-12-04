package com.grouptwelve.roguelikegame.model.LevelPackage;

public class LevelSystem {

    private int level = 1;
    private int xp = 0;
    private int xpToNext = 100;

    public boolean addXP(int amount) {
        xp += amount;
        if (xp >= xpToNext) {
            xp -= xpToNext;
            level++;
            xpToNext = (int)(xpToNext * 1.25);  // scale
            return true; // means leveled up
        }
        return false;
    }

    public int getLevel() { return level; }
    public int getXP() { return xp; }
    public int getXPToNext() { return xpToNext; }
}

