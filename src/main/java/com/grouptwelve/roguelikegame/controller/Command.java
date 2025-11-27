package com.grouptwelve.roguelikegame.controller;

public enum Command {
    // Movement
    MOVE_UP,
    MOVE_DOWN,
    MOVE_LEFT,
    MOVE_RIGHT,

    // Actions
    ATTACK,
    // ABILITY_1,
    // ABILITY_2,
    // ...


    // System
    // PAUSE,
    PAUSE;
    // FULLSCREEN,

    /**
     * Use to keep track of movement state
     *
     * @return true if it is a movement command
     */
    public boolean isMovement() {
        return this == MOVE_UP || this == MOVE_DOWN || this == MOVE_LEFT || this == MOVE_RIGHT;
    }

    /**
     * @return true if it is an action command
     */
    public boolean isAction() {
        return this == ATTACK; // ABILITY_1 ...

    }

    // isSystem() ...
}
