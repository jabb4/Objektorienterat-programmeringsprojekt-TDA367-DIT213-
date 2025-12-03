package com.grouptwelve.roguelikegame.model;

import com.grouptwelve.roguelikegame.model.EventsPackage.EnemyDeathEvent;
import com.grouptwelve.roguelikegame.view.ControllerListener;


import java.util.ArrayList;
import java.util.List;

public class ControlEventManager
{
    //singleton instance
    private static ControlEventManager instance;
    //list of listeners to draw this events
    private final List<ControllerListener> listeners;

    private ControlEventManager()
    {
        listeners = new ArrayList<>();
    }

    /**
     * @return singleton instance
     */
    public static ControlEventManager getInstance()
    {
        if(instance == null)
        {
            instance = new ControlEventManager();
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

    public void playerDied(double x, double y)
    {
        for (ControllerListener listener:  listeners)
        {
            listener.playerDied(x, y);
        }
    }

    public void enemyDied(double x, double y, int xp) {
        EnemyDeathEvent event = new EnemyDeathEvent(x, y, xp);
        //for (ControllerListener listener : listeners) {
        //    listener.onEnemyDeath(event);
        //}
    }


    /**
     * Called when an enemy takes damage, to trigger visual feedback.
     * @param x X coordinate of the hit
     * @param y Y coordinate of the hit
     * @param damage Amount of damage dealt
     */
    public void onEnemyHit(double x, double y, double damage)
    {
        for (ControllerListener listener : listeners)
        {
            listener.onEnemyHit(x, y, damage);
        }
    }
}
