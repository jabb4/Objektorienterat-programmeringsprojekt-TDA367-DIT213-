package com.grouptwelve.roguelikegame.model.events.output.events;

/**
 * Event data for experience point changes.
 */
public class XpChangeEvent {
    private int totalXP;
    private int XPtoNext;
    private int level;

    /**
     * Creates a new XP change event.
     *
     * @param totalXP the player's total accumulated XP
     * @param XPtoNext the XP required to reach the next level
     * @param level the player's current level
     */
    public XpChangeEvent(int totalXP, int XPtoNext, int level)
    {
        this.totalXP = totalXP;
        this.XPtoNext = XPtoNext;
        this.level = level;
    }

    public int getTotalXP()
    {
        return totalXP;
    }

    public int getXPtoNext()
    {
        return XPtoNext;
    }

    public int getLevel()
    {
        return level;
    }
}
