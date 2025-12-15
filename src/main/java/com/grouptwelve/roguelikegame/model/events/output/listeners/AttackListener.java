package com.grouptwelve.roguelikegame.model.events.output.listeners;

import com.grouptwelve.roguelikegame.model.events.output.events.AttackEvent;

public interface AttackListener {
    void onAttack(AttackEvent attackEvent);
}
