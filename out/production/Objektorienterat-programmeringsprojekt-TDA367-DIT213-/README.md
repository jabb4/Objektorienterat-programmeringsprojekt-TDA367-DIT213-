# Objektorienterat-programmeringsprojekt-TDA367-DIT213-
Project repo for Objektorienterat programmeringsprojekt TDA367 / DIT213 course

## Idea

> Vampire Survivors game *ish*

**Base Features:**
- Topdown view
- Enemies spawn continuously and chase player
- Player gains XP from killing enemies
- Level up system with upgrade choices
- Multiple weapon types with different behaviors
- Unique enemies with different attack patterns
- Enemies scale in difficulty over time
- Reset every run

**Expansion Ideas:**
- Weapon evolution/combination system (weapons merge to form new ones)
- Power-up/pickup system with different effects
- Environmental hazards (fire, snow) that damage or slows both player and enemies
- Different dammage types
- Random events and objectives
- Persistent progression (unlock new starter kits, abilities between runs)
- Projectile system (different bullet types, collision detection)
- Experience/leveling algorithm with exponential scaling
- Local 1v1 mode
- Procedurally generated maps
- Gear that modularly connect to each other and protect the core
- Save gear for next run
- Scoreboard

**Design Patterns:**
- Factory: Enemy/weapon creation
- Observer: Weather conditions
- Composite: Weapon/armor combinations
- State: Player/enemy states (invulnerable, running, burning, poisoned)

**MVP (Minimal Viable Product):**
- Single player character that can move in 4 directions
- One basic weapon with auto-attack
- 2-3 enemy types that spawn and chase player
- Basic collision detection
- Simple leveling system (gain XP, choose 1 of 3 upgrades on level up)
- Health system for player
- Single fixed map/arena
- Simple UI showing health, level, and XP

**Target audience:**
- Rouguelike gamers with low attention span
