package com.grouptwelve.roguelikegame.controller;

public enum Command {
    // Movement / Selecting buff
    MOVE_UP,
    MOVE_DOWN,
    MOVE_LEFT,
    MOVE_RIGHT,

    // Actions
    ATTACK,
    SELECT,

    // System
    PAUSE;

    /**
     * Use to keep track of movement state
     *
     * @return true if it is a movement command
     */
    public boolean isMovement() {
        return this == MOVE_UP || this == MOVE_DOWN || this == MOVE_LEFT || this == MOVE_RIGHT;
    }
}
