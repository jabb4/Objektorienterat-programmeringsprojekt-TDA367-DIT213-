package com.grouptwelve.roguelikegame.model.events.output;

import com.grouptwelve.roguelikegame.model.events.output.listeners.LevelUpListener;

public interface LevelUpPublisher {
    public void subscribeLevelUp(LevelUpListener levelUpListener);
    public void unsubscribeLevelUp(LevelUpListener levelUpListener);
    public void onLevelUp();
}
