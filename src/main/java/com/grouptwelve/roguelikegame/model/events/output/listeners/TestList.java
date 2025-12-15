package com.grouptwelve.roguelikegame.model.events.output.listeners;

import com.grouptwelve.roguelikegame.model.events.output.events.AttackEvent;

public class TestList implements  AttackListener{
    @Override
    public void onAttack(AttackEvent attackEvent) {
        System.out.println("attack");

    }
}
