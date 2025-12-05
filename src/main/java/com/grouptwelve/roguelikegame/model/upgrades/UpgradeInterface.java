package com.grouptwelve.roguelikegame.model.upgrades;

import com.grouptwelve.roguelikegame.model.entities.Player;

public interface UpgradeInterface {
    void apply(Player player);
    String getName();
}
