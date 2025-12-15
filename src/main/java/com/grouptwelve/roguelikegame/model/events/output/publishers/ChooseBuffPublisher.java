package com.grouptwelve.roguelikegame.model.events.output.publishers;

import com.grouptwelve.roguelikegame.model.events.output.listeners.ChooseBuffListener;
import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

public interface ChooseBuffPublisher {
    void subscribeBuff(ChooseBuffListener chooseBuffListener);
    void unsubscribeBuff(ChooseBuffListener chooseBuffListener);
    void onChooseBuff(UpgradeInterface[] upgrades);
}
