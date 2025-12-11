package com.grouptwelve.roguelikegame.model.upgrades.weapons;

import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.upgrades.logic.FlatAttributeUpgrade;

public class RangeFlatUpgradeFlat extends FlatAttributeUpgrade {

    public RangeFlatUpgradeFlat(double amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.getWeapon().addRange(amount);
    }

    @Override
    public String getName() {
        return "Range +" + (int)amount;
    }
}
