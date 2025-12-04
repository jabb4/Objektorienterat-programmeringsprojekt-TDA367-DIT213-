package com.grouptwelve.roguelikegame.model.systems;

import com.grouptwelve.roguelikegame.model.EntitiesPackage.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * System responsible for collision and hit detection.
 * Provides utility methods for checking if attacks hit targets.
 */
public class CollisionSystem {

    /**
     * Checks if an attack at position (attackX, attackY) with the given range
     * hits a target at position (targetX, targetY) with the given size.
     * Uses circle-circle collision detection.
     *
     * @param attackX X coordinate of the attack point
     * @param attackY Y coordinate of the attack point
     * @param attackRange Range/radius of the attack
     * @param targetX X coordinate of the target
     * @param targetY Y coordinate of the target
     * @param targetSize Size/radius of the target
     * @return true if the attack hits the target
     */
    public static boolean isHit(double attackX, double attackY, double attackRange,
                                double targetX, double targetY, double targetSize) {
        double deltaX = attackX - targetX;
        double deltaY = attackY - targetY;
        double distanceSquared = deltaX * deltaX + deltaY * deltaY;
        double rangeSum = attackRange + targetSize;
        
        // Compare squared distances to avoid sqrt for better performance
        return distanceSquared < rangeSum * rangeSum;
    }

    /**
     * Finds all entities within range of an attack point.
     *
     * @param attackX X coordinate of the attack point
     * @param attackY Y coordinate of the attack point
     * @param range Range of the attack
     * @param entities List of entities to check
     * @param <T> Type of entity
     * @return List of entities that are hit by the attack
     */
    public static <T extends Entity> List<T> getEntitiesInRange(
            double attackX, double attackY, double range, List<T> entities) {
        
        List<T> hitEntities = new ArrayList<>();
        
        for (T entity : entities) {
            if (entity.getAliveStatus() && 
                isHit(attackX, attackY, range, entity.getX(), entity.getY(), entity.getSize())) {
                hitEntities.add(entity);
            }
        }
        
        return hitEntities;
    }

    /**
     * Calculates the normalized direction vector from point A to point B.
     *
     * @param fromX X coordinate of starting point
     * @param fromY Y coordinate of starting point
     * @param toX X coordinate of target point
     * @param toY Y coordinate of target point
     * @return Array of [dirX, dirY] normalized direction
     */
    public static double[] calculateDirection(double fromX, double fromY, double toX, double toY) {
        double dirX = toX - fromX;
        double dirY = toY - fromY;
        double length = Math.sqrt(dirX * dirX + dirY * dirY);
        
        if (length > 0) {
            dirX /= length;
            dirY /= length;
        }
        
        return new double[]{dirX, dirY};
    }
}
