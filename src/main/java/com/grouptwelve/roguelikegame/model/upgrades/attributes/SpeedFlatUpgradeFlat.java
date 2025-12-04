package com.grouptwelve.roguelikegame.model.upgrades.attributes;

import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.upgrades.logic.FlatAttributeUpgrade;

public class SpeedFlatUpgradeFlat extends FlatAttributeUpgrade {

    public SpeedFlatUpgradeFlat(double amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.increaseMoveSpeed(amount);
    }

    @Override
    public String getName() {
        return "+ " + amount + " Move Speed";
    }
}
