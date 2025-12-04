package com.grouptwelve.roguelikegame.model.upgrades.attributes;

import com.grouptwelve.roguelikegame.model.entities.Player;
import com.grouptwelve.roguelikegame.model.upgrades.logic.FlatAttributeUpgrade;

public class MaxHpFlatUpgradeFlat extends FlatAttributeUpgrade {

    public MaxHpFlatUpgradeFlat(double amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.setMaxHP(player.getMaxHP() + amount);
        player.setHp(player.getHp() + amount); // also heal the flat amount
    }

    @Override
    public String getName() {
        return "+ " + amount + " Max HP";
    }
}
