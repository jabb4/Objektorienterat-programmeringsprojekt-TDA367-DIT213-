package com.grouptwelve.roguelikegame.model.events.output.publishers;

import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.events.output.events.AttackEvent;
import com.grouptwelve.roguelikegame.model.events.output.events.EntityHitEvent;
import com.grouptwelve.roguelikegame.model.events.output.events.EntityDeathEvent;
import com.grouptwelve.roguelikegame.model.events.output.events.XpChangeEvent;
import com.grouptwelve.roguelikegame.model.events.output.listeners.*;
import com.grouptwelve.roguelikegame.model.events.output.listeners.LevelUpListener;
import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

import java.util.LinkedList;
import java.util.List;

/**
 * centralized event publisher class for handling events from the model
 * contains list of listeners which can sub and unsubscribe
 * all methods are overridden from specific interfaces(one concern)
 * this class in a whole is never given out, only used though interfaces
 */

public class EventPublisher implements LevelUpPublisher, EntityPublisher, ChooseBuffPublisher, XpPublisher {
    private List<AttackListener> attackListeners = new LinkedList<>();
    private List<EntityDeathListener> entityDeathListeners = new LinkedList<>();
    private List<LevelUpListener> levelUpListeners = new LinkedList<>();
    private List<EntityHitListener> entityHitListeners = new LinkedList<>();
    private List<ChooseBuffListener> chooseBuffListeners = new LinkedList<>();
    private List<XpListener> xpListeners = new LinkedList<>();

    @Override
    public void onAttack(AttackEvent attackEvent) {
        for(AttackListener attackListener : attackListeners)
        {
            attackListener.onAttack(attackEvent);
        }
    }

    @Override
    public void onLevelUp() {
        for(LevelUpListener levelUpListener : levelUpListeners)
        {
            levelUpListener.onLevelUp();
        }
    }

    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        for(EntityDeathListener entityDeathListener : entityDeathListeners)
        {
            entityDeathListener.onEntityDeath(event);
        }
    }

    @Override
    public void onEntityHit(EntityHitEvent entityHitEvent)
    {
        for (EntityHitListener entityHitListener : entityHitListeners)
        {
            entityHitListener.onEntityHit(entityHitEvent);
        }
    }

    @Override
    public void onChooseBuff(UpgradeInterface[] upgrades)
    {
        for (ChooseBuffListener chooseBuffListener : chooseBuffListeners)
        {
            chooseBuffListener.onChooseBuff( upgrades);
        }
    }
    @Override
    public void onUpdateXp(XpChangeEvent xpChangeEvent) {
        for(XpListener xpListener : xpListeners)
        {
            xpListener.onUpdateXP(xpChangeEvent);
        }
    }

    // ==================== Sub- and unsubscribers ====================

    @Override
    public void subscribeAttack(AttackListener attackListener) {
        attackListeners.add(attackListener);
    }

    @Override
    public void unsubscribeAttack(AttackListener attackListener) {
        attackListeners.remove(attackListener); // linear but does not even happen often
    }

    @Override
    public void subscribeLevelUp(LevelUpListener levelUpListener) {
        levelUpListeners.add(levelUpListener);
    }

    @Override
    public void unsubscribeLevelUp(LevelUpListener levelUpListener) {
        levelUpListeners.remove(levelUpListener);
    }

    @Override
    public void subscribeEntityDeath(EntityDeathListener entityDeathListener) {
        entityDeathListeners.add(entityDeathListener);
    }
    @Override
    public void unsubscribeEntityDeath(EntityDeathListener entityDeathListener) {
        entityDeathListeners.remove(entityDeathListener);
    }

    @Override
    public void subscribeEntityHit(EntityHitListener entityHitListener)
    {
        entityHitListeners.add(entityHitListener);
    }

    @Override
    public void unsubscribeEntityHit(EntityHitListener entityHitListener)
    {
        entityHitListeners.remove(entityHitListener);
    }

    @Override
    public void subscribeBuff(ChooseBuffListener chooseBuffListener)
    {
        chooseBuffListeners.add(chooseBuffListener);
    }

    @Override
    public void unsubscribeBuff(ChooseBuffListener chooseBuffListener)
    {
        chooseBuffListeners.remove(chooseBuffListener);
    }

    @Override
    public void subscribeXp(XpListener xpListener) {
        xpListeners.add(xpListener);
    }

    @Override
    public void unsubscribeXp(XpListener xpListener) {
        xpListeners.remove(xpListener);

    }


}
