package com.grouptwelve.roguelikegame.model.events.output.publishers;

import com.grouptwelve.roguelikegame.model.events.output.listeners.LevelUpListener;

public interface LevelUpPublisher {
    void subscribeLevelUp(LevelUpListener levelUpListener);
    void unsubscribeLevelUp(LevelUpListener levelUpListener);
    void onLevelUp();
}
