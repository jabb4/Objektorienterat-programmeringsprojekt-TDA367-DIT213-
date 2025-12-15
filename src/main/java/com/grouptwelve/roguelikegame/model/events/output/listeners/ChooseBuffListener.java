package com.grouptwelve.roguelikegame.model.events.output.listeners;

import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

/**
 * Implement this to be notified when the player needs to choose a buff/upgrade.
 */
public interface ChooseBuffListener {
    /**
     * Called when the player must choose from available upgrades.
     *
     * @param upgrades the array of upgrade options to choose from
     */
    void onChooseBuff(UpgradeInterface[] upgrades);
}
