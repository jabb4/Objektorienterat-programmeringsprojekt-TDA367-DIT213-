package com.grouptwelve.roguelikegame.model.events.output.events;

import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

/**
 * event-data for choosing buff
 */
public class UpgradeEvent {
    private UpgradeInterface[] upgrades;

    public UpgradeEvent(UpgradeInterface[] upgrades)
    {
        this.upgrades = upgrades;
    }

    public UpgradeInterface[] getUpgrades() {
        return upgrades;
    }
}
