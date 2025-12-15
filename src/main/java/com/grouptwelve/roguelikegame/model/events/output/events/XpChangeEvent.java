package com.grouptwelve.roguelikegame.model.events.output.events;

public class XpChangeEvent {
    private int totalXP;
    private int XPtoNext;
    private int level;
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
