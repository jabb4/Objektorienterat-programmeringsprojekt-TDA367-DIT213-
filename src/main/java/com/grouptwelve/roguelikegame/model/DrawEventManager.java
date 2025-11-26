package com.grouptwelve.roguelikegame.model;

import com.grouptwelve.roguelikegame.view.ModelListener;

import java.util.ArrayList;
import java.util.List;

public class DrawEventManager
{
    //singleton instance
    private static DrawEventManager instance;
    //list of listeners to draw this events
    private final List<ModelListener> listeners;

    private DrawEventManager()
    {
        listeners = new ArrayList<>();
    }

    /**
     * @return singleton instance
     */
    public static DrawEventManager getInstance()
    {
        if(instance == null)
        {
            instance = new DrawEventManager();
        }
        return instance;
    }

    /**
     * used by listens to add self to the list
     * @param listener caller
     */
    public void subscribe(ModelListener listener)
    {
        listeners.add(listener);
    }
    public void unsubscribe(ModelListener listener)
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
        for(ModelListener listener : listeners)
        {
            listener.drawAttack(x, y, size);
        }
    }

    public void playerDied()
    {
        for (ModelListener listener:  listeners)
        {
            listener.playerDied();
        }
    }
}
