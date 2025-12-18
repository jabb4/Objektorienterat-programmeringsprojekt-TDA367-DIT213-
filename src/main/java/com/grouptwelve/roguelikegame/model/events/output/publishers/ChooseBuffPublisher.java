package com.grouptwelve.roguelikegame.model.events.output.publishers;

import com.grouptwelve.roguelikegame.model.events.output.events.UpgradeEvent;
import com.grouptwelve.roguelikegame.model.events.output.listeners.ChooseBuffListener;

/**
 * publisher interface that EventPublisher implements,
 * game use this publisher when player levels up
 * sub and unsubscribers for listeners to use
 */
public interface ChooseBuffPublisher {
    void subscribeBuff(ChooseBuffListener chooseBuffListener);
    void unsubscribeBuff(ChooseBuffListener chooseBuffListener);

    /**
     * called by game when player levels up and buffs have been generated
     * @param upgradeEvent buff to select from
     */
    void onChooseBuff(UpgradeEvent upgradeEvent);
}
