package com.grouptwelve.roguelikegame.controller;

/**
 * For objects that want to observe input commands.
 */
public interface InputEventListener {
    /**
     * @param command The command that was triggered
     */
    void onCommandPressed(Command command);

    /**
     * @param command The command that was released
     */
    void onCommandReleased(Command command);
}
