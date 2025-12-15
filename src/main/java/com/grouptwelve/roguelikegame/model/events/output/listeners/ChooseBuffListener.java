package com.grouptwelve.roguelikegame.model.events.output.listeners;

import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

public interface ChooseBuffListener {
    void onChooseBuff(UpgradeInterface[] upgrades);
}
