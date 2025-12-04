package com.grouptwelve.roguelikegame.model.UpgradesPackage;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Player;
import com.grouptwelve.roguelikegame.model.WeaponsPackage.Weapon;

public interface UpgradeInterface {
    void apply(Player player);
    String getName();
}
