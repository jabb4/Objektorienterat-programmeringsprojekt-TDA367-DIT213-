package com.grouptwelve.roguelikegame.model.events.output.listeners;

import com.grouptwelve.roguelikegame.model.events.output.events.XpChangeEvent;

public interface XpListener {
    void onUpdateXP(XpChangeEvent xpChangeEvent);
}
