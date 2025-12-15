package com.grouptwelve.roguelikegame.model.events.output.listeners;

import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

public interface ChooseBuffListener {
    public void onChooseBuff(UpgradeInterface[] upgrades);
}
