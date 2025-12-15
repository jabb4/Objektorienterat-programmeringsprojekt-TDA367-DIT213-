package com.grouptwelve.roguelikegame.model.events.output.publishers;

import com.grouptwelve.roguelikegame.model.events.output.events.XpChangeEvent;
import com.grouptwelve.roguelikegame.model.events.output.listeners.XpListener;

public interface XpPublisher {
    void subscribeXp(XpListener xpListener);
    void unsubscribeXp(XpListener xpListener);
    void onUpdateXp(XpChangeEvent xpChangeEvent);
}
