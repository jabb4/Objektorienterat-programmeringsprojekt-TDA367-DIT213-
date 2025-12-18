package com.grouptwelve.roguelikegame.model.upgrades;

import com.grouptwelve.roguelikegame.model.entities.Entity;

/**
 * Represents a single upgrade that can be applied to an entity.
 * <p>
 * Upgrades may modify attributes, add effects, or change behavior.
 * Each upgrade can optionally declare whether it is currently available
 * based on the state of the entity.
 */
public interface UpgradeInterface {

    /**
     * Applies this upgrade to the given entity.
     *
     * @param entity the entity receiving the upgrade
     */
    void apply(Entity entity);

    /**
     * Determines whether this upgrade is currently available for the given entity.
     * <p>
     * By default, upgrades are always available.
     * Specific upgrades may override this to enforce prerequisites.
     *
     * @param entity the entity to check availability against
     * @return {@code true} if the upgrade can be offered, otherwise {@code false}
     */
    default boolean isAvailable(Entity entity){
        return true;
    }

    /**
     * Returns the display name of the upgrade.
     *
     * @return a name for UI display
     */
    String getName();
}
