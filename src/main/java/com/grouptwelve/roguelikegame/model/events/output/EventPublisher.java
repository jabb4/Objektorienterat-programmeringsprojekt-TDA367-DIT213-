package com.grouptwelve.roguelikegame.model.events.output;

import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.entities.Entity;
import com.grouptwelve.roguelikegame.model.events.output.events.AttackEvent;
import com.grouptwelve.roguelikegame.model.events.output.listeners.*;
import com.grouptwelve.roguelikegame.model.events.output.listeners.LevelUpListener;
import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

import java.util.LinkedList;
import java.util.List;

public class EventPublisher implements LevelUpPublisher, EntityPublisher, ChooseBuffPublisher{
    private List<AttackListener> attackListeners = new LinkedList<>();
    private List<EntityDeathListener> entityDeathListeners = new LinkedList<>();
    private List<LevelUpListener> levelUpListeners = new LinkedList<>();
    //private List<PlayerDeathListener> playerDeathListeners = new LinkedList<>();
    //private List<PlayerHitListener> playerHitListeners = new LinkedList<>();
    private List<EntityHitListener> entityHitListeners = new LinkedList<>();
    private List<ChooseBuffListener> chooseBuffListeners = new LinkedList<>();


    @Override
    public void onAttack(AttackEvent attackEvent) {
        for(AttackListener attackListener : attackListeners)
        {
            attackListener.onAttack(attackEvent);
        }
    }
    /*
    @Override
    public void onPlayerDeath() {
        for (PlayerDeathListener playerDeathListener : playerDeathListeners)
        {
            playerDeathListener.onPlayerDeath();
        }
    }

     */

    @Override
    public void onLevelUp() {
        for(com.grouptwelve.roguelikegame.model.events.output.listeners.LevelUpListener levelUpListener : levelUpListeners)
        {
            levelUpListener.onLevelUp();
        }
    }

    @Override
    public void onEntityDeath(Entity entity) {
        for(EntityDeathListener entityDeathListener : entityDeathListeners)
        {
            entityDeathListener.onEntityDeath(entity);
        }
    }
    /*
    @Override
    public void onPlayerHit(int x, int y)
    {
        for(PlayerHitListener playerHitListener : playerHitListeners)
        {
            playerHitListener.onPlayerHit(x, y);
        }
    }

     */
    @Override
    public void onEntityHit(Entity entity, CombatResult combatResult)
    {
        for (EntityHitListener entityHitListener : entityHitListeners)
        {
            entityHitListener.onEntityHit(entity, combatResult);
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


    //sub and unssubscribers
    @Override
    public void subscribeAttack(AttackListener attackListener) {
        attackListeners.add(attackListener);
    }
    @Override
    public void unsubscribeAttack(AttackListener attackListener) {
        attackListeners.remove(attackListener); // linear but does not even happen often
    }

/*
    @Override
    public void subscribePlayerDeath(PlayerDeathListener playerDeathListener) {
        playerDeathListeners.add(playerDeathListener);
    }
    @Override
    public void unsubscribePlayerDeath(PlayerDeathListener playerDeathListener) {
        playerDeathListeners.remove(playerDeathListener);

    }

 */


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
    /*
    @Override
    public void subscribePlayerHit(PlayerHitListener playerHitListener)
    {
        playerHitListeners.add(playerHitListener);
    }
    @Override
    public void unsubscribePlayerHit(PlayerHitListener playerHitListener)
    {
        playerHitListeners.remove(playerHitListener);
    }

     */

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



}
