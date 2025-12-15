package com.grouptwelve.roguelikegame.model.events.output;

import com.grouptwelve.roguelikegame.model.events.output.listeners.ChooseBuffListener;
import com.grouptwelve.roguelikegame.model.upgrades.UpgradeInterface;

public interface ChooseBuffPublisher {
    public void subscribeBuff(ChooseBuffListener chooseBuffListener);
    public void unsubscribeBuff(ChooseBuffListener chooseBuffListener);
    public void onChooseBuff(UpgradeInterface[] upgrades);
}
