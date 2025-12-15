package com.grouptwelve.roguelikegame.model.events.output.listeners;

import com.grouptwelve.roguelikegame.model.combat.CombatResult;
import com.grouptwelve.roguelikegame.model.entities.Entity;

public interface EntityHitListener {
    public void onEntityHit(Entity entity, CombatResult combatResult);
}
