package com.grouptwelve.roguelikegame.model.EntitiesPackage;

/**
 * Abstract base class for all enemy entities.
 */
public abstract class Enemy extends Entity {
    
    /**
     * Represents the current attack state of the enemy.
     */
    public enum AttackState {
        IDLE, // Enemy is not attacking, moving
        WINDING_UP, // Enemy is preparing to attack
        COOLDOWN // Enemy is on cooldown after attacking
    }
    
    protected double targetDist;
    protected double attackRange;
    
    // Wind-up attack state machine
    protected AttackState attackState;
    protected double windUpTime;
    protected double windUpRemaining;
    
    // Locked attack direction (set when wind-up starts)
    protected double lockedDirX;
    protected double lockedDirY;

    public Enemy(String name, Entities type, double x, double y, double hp, int size, double maxHP) {
        super(name, type, x, y, hp, size, maxHP);
        this.velocity.setMaxSpeed(50); // Default enemy velocity
        this.attackRange = 50;
        this.attackState = AttackState.IDLE;
        this.windUpTime = 0.3; // Default wind-up time
        this.windUpRemaining = 0;
        this.lockedDirX = 0;
        this.lockedDirY = 0;
    }

    /**
     * Sets the target position for the enemy to move towards.
     * Calculates direction and updates velocity.
     *
     * @param tx Target x-coordinate
     * @param ty Target y-coordinate
     */
    public void setTargetPos(double tx, double ty) {
        double dx = tx - this.x;
        double dy = ty - this.y;

        // Normalize the vector (for diagonal movement)
        double length = Math.sqrt(dx * dx + dy * dy);
        targetDist = length;
        double normDx = dx / length;
        double normDy = dy / length;
        this.dirX = normDx;
        this.dirY = normDy;

        // Scale by maxSpeed to get velocity
        velocity.set(normDx * velocity.getMaxSpeed(), normDy * velocity.getMaxSpeed());
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        
        updateAttackState(deltaTime);

        move(deltaTime); // Always apply movement, which includes knockback
    }
    
    /**
     * Updates the attack state machine.
     * 
     * State transitions:
     * - IDLE: Move only if player is out of range, if player in range and weapon ready, start wind-up
     * - WINDING_UP: Stop movement, count down timer, then attack in locked direction
     * - COOLDOWN: Move only if player is out of range, wait for weapon cooldown, then return to IDLE
     *
     * @param deltaTime Time since last update
     */
    private void updateAttackState(double deltaTime) {
        switch (attackState) {
            case IDLE:
                // Player in range and weapon ready
                if (targetDist < attackRange && weapon != null && weapon.canAttack()) {
                    // Lock the current direction for the attack
                    lockedDirX = dirX;
                    lockedDirY = dirY;
                    attackState = AttackState.WINDING_UP; // Start wind-up
                    windUpRemaining = windUpTime;
                    velocity.stop();
                } else if (targetDist < attackRange) { // In range but weapon on cooldown
                    velocity.stop(); // Stand still
                }
                // out of range, keep moving
                break;
                
            case WINDING_UP:
                // Always stop during wind-up
                velocity.stop();
                windUpRemaining -= deltaTime;

                // Wind-up complete
                if (windUpRemaining <= 0) {
                    // Execute attack in locked direction and start cooldown
                    attackInLockedDirection();
                    attackState = AttackState.COOLDOWN;
                }
                break;
                
            case COOLDOWN:
                // Stand still if player in range
                if (targetDist < attackRange) {
                    velocity.stop();
                }
                
                // Wait for weapon cooldown to end
                if (weapon != null && weapon.canAttack()) {
                    attackState = AttackState.IDLE;
                }
                break;
        }
    }
    
    /**
     * Executes an attack in the locked direction (set when wind-up started).
     * This ensures the attack goes where the player WAS, not where they ARE.
     */
    private void attackInLockedDirection() {
        // Temporarily set direction to locked direction for the attack
        double originalDirX = this.dirX;
        double originalDirY = this.dirY;
        
        this.dirX = lockedDirX;
        this.dirY = lockedDirY;
        
        attack();
        
        // Restore current direction
        this.dirX = originalDirX;
        this.dirY = originalDirY;
    }
    
    /**
     * Checks if the enemy is currently winding up an attack.
     * Can be used by the view to show attack wind-up animation.
     *
     * @return true if enemy is in wind-up state
     */
    public boolean isWindingUp() {
        return attackState == AttackState.WINDING_UP;
    }

    /**
     * Gets the current attack state.
     *
     * @return The current AttackState
     */
    public AttackState getAttackState() {
        return attackState;
    }
    
    /**
     * Gets the locked X direction for the wind-up attack.
     *
     * @return The locked X direction (-1 to 1)
     */
    public double getLockedDirX() {
        return lockedDirX;
    }
    
    /**
     * Gets the locked Y direction for the wind-up attack.
     *
     * @return The locked Y direction (-1 to 1)
     */
    public double getLockedDirY() {
        return lockedDirY;
    }
    
    /**
     * Revives the enemy and resets all state for reuse from pool.
     * Ensures no lingering attack state from previous life.
     */
    @Override
    public void revive() {
        super.revive();
        
        // Reset attack state machine
        attackState = AttackState.IDLE;
        windUpRemaining = 0;
        lockedDirX = 0;
        lockedDirY = 0;
        
        // Reset weapon cooldown
        if (weapon != null) {
            weapon.resetCooldown();
        }
    }
}
