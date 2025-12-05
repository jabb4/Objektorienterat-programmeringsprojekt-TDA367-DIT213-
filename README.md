# Inheritance of Violence

> Object-Oriented Programming Project for TDA367/DIT213

A top-down roguelike survival game built with Java and JavaFX. Fight waves of enemies, level up, and choose upgrades to survive as long as possible.

---

## Features

### Gameplay
- **Top-down combat view**
- **Two enemy types**
- **XP and leveling system**
- **Upgrade selection**
- **Critical hit system**
- **Knockback physics**
- **Damage over time effects**
- **Enemy collision avoidance**

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
   cd "Roguelike Game"
   ```

2. **Run the game**
   ```bash
   mvn javafx:run
   ```

---

## How to Play

| Action          | Keys                                                           |
|-----------------|----------------------------------------------------------------|
| Move            | <kbd>W</kbd><kbd>A</kbd><kbd>S</kbd><kbd>D</kbd> or Arrow Keys |
| Attack          | <kbd>K</kbd>                                                   |
| Pause           | <kbd>Escape</kbd>                                              |
| Select upgrade  | <kbd>A</kbd> / <kbd>D</kbd> + <kbd>K</kbd>                     |

### Objective
Survive as long as possible against waves of enemies. Kill enemies to gain XP, level up, and choose upgrades to grow stronger.

---

## Project Structure

```
src/main/java/com/grouptwelve/roguelikegame/
├── controller/       # Input handling, game loop
├── model/
│   ├── combat/       # Collision detection, hit resolution
│   ├── effects/      # Knockback, fire, hit effects
│   ├── entities/     # Player, enemies, entity factory
│   ├── events/       # Event listeners and publishers
│   ├── level/        # XP and leveling system
│   ├── upgrades/     # Upgrade types and registry
│   └── weapons/      # Weapons used by entities
├── view/             # JavaFX rendering
└── App.java          # Entry point
```