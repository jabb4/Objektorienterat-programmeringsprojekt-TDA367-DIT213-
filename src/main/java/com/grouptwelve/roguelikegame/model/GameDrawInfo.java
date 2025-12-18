package com.grouptwelve.roguelikegame.model;

import com.grouptwelve.roguelikegame.model.entities.Obstacle;
import com.grouptwelve.roguelikegame.model.entities.Player;

/**
 * interface that only contains get methods that view use to draw the game state.
 * only sends restricted classes types(Obstacle)
 */
import java.util.List;

    public interface GameDrawInfo {

    Player getPlayer();

    List<Obstacle> getEnemies();

}
