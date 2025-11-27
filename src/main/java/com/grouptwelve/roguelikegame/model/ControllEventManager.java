package com.grouptwelve.roguelikegame.model;

import com.grouptwelve.roguelikegame.view.ControllerListener;


import java.util.ArrayList;
import java.util.List;

public class ControllEventManager
{
    //singleton instance
    private static ControllEventManager instance;
    //list of listeners to draw this events
    private final List<ControllerListener> listeners;

    private ControllEventManager()
    {
        listeners = new ArrayList<>();
    }

    /**
     * @return singleton instance
     */
    public static ControllEventManager getInstance()
    {
        if(instance == null)
        {
            instance = new ControllEventManager();
        }
        return instance;
    }

    /**
     * used by listens to add self to the list
     * @param listener caller
     */
    public void subscribe(ControllerListener listener)
    {
        listeners.add(listener);
    }
    public void unsubscribe(ControllerListener listener)
    {
        listeners.remove(listener);
    }

    /**
     * called by either player or enemy to notify an attackEvent
     * @param x coordinate
     * @param y coordinate
     * @param size range
     */
    public void drawAttack(double x, double y, double size)
    {
        for(ControllerListener listener : listeners)
        {
            listener.drawAttack(x, y, size);
        }
    }

    public void playerDied()
    {
        for (ControllerListener listener:  listeners)
        {
            listener.playerDied();
        }
    }
}
