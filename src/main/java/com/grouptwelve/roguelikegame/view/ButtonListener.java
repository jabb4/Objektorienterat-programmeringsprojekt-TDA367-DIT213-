package com.grouptwelve.roguelikegame.view;

public interface ButtonListener {

    /**
     * Resume the game when the resume button is clicked
     */
    void onResume();

    /**
     * Quit the game when the quit button is clicked
     */
    void onQuit();

    /**
     * Start a new game if player died and click on play again button.
     */
    void onPlayAgain();
}
