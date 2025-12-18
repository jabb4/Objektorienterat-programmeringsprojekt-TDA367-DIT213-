# Inheritance of Violence

> Object-Oriented Programming Project for TDA367/DIT213

A top-down roguelike survival game built with Java and JavaFX. Fight waves of enemies, level up, and choose upgrades to survive as long as possible.

---

## Features

### Gameplay
- **Top-down combat view**
- **Two enemy types** (Goblin, Troll)
- **Two weapon types** (Sword, Club)
- **XP and leveling system**
- **Upgrade selection** (20+ upgrades across attributes, weapons, and effects)
- **Critical hit system**
- **Knockback physics**
- **Damage over time effects** (Fire)
- **Enemy collision avoidance**
- **Game statistics tracking with high score** (time survived, kills, damage dealt)

---

## Built With

- Java 25
- JavaFX 25.0.1
- Maven

---

## Installation

### Prerequisites
- Java 25 or later
- Maven 3.6+

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/jabb4/Objektorienterat-programmeringsprojekt-TDA367-DIT213-.git
   cd Objektorienterat-programmeringsprojekt-TDA367-DIT213-
   ```

2. **Run the game**
   ```bash
   mvn clean javafx:run
   ```

---

## How to Play

| Action          | Keys                                                           |
|-----------------|----------------------------------------------------------------|
| Move            | <kbd>W</kbd><kbd>A</kbd><kbd>S</kbd><kbd>D</kbd> or Arrow Keys |
| Attack          | <kbd>Space</kbd>                                               |
| Pause           | <kbd>Escape</kbd>                                              |
| Select upgrade  | <kbd>W</kbd> / <kbd>S</kbd> + <kbd>Enter</kbd>                 |

### Objective
Survive as long as possible against waves of enemies. Kill enemies to gain XP, level up, and choose upgrades to grow stronger.

---

## Project Structure

```
src/main/java/com/grouptwelve/roguelikegame/
├── controller/       # Input handling, game loop, scene management
├── model/
│   ├── combat/       # Collision detection, hit resolution
│   ├── constraints/  # World-level rules enforced on entities (e.g. world bounds)
│   ├── effects/      # Knockback, fire, damage over time effects
│   ├── entities/     # Player, enemies, entity factory
│   ├── events/       # Event listeners and publishers
│   ├── statistics/   # Game statistics and high score system
│   ├── upgrades/     # Upgrade types and registry
│   └── weapons/      # Weapons used by entities
├── view/
│   ├── effects/      # Visual effects (particles, damage numbers, death effects)
│   ├── rendering/    # Entity rendering
│   ├── state/        # View state management
│   └── ui/           # HUD, menus, buff selection, statistics display
└── App.java          # Entry point
```