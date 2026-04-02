# 🔫 MineGun

**Advanced Firearms Library for Minestom** *Raycasted hitscan weapons, custom health & shield management, and a clean event-driven API — all in pure Java.*

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-25-orange)](https://www.oracle.com/java/technologies/downloads/)
[![Wiki](https://img.shields.io/badge/Wiki-minegun.vardinsdev.org-blue)](https://minegun.vardinsdev.org)

---

## 🌟 Overview
MineGun is a high-performance firearms library built specifically for the [Minestom](https://minestom.net/) server implementation. It moves away from vanilla Minecraft's limited projectile system in favor of a precise, step-based raycast engine.

### 🎯 Key Features
* **Raycasted Hitscan:** Precise detection at 0.5 block increments with a 100-block range.
* **Dynamic Hitboxes:** Sneak-aware hitbox detection that adjusts based on player height (1.8m standing / 1.5m sneaking).
* **Health & Shield System:** A custom 100 HP / 100 SP system using Minestom Tags, complete with Boss Bar HUDs and damage routing.
* **Ready-to-Use Weapons:** Includes pre-configured **Rifle** (Hitscan) and **Rocket Launcher** (Explosive) implementations.
* **minegunLogger:** Built-in ANSI-colored console logger for clean lifecycle tracking.

---

## 🚀 Installation

MineGun is a source-level library. To integrate it, clone the repository and copy the core packages into your project.

### 1. Requirements
* **Java:** 25+
* **Minestom:** `2026.03.03-1.21.11`

### 2. Integration
Copy the following files from `src/main/java/com/minegun` into your source root:
* `HealthManagement.java`
* `minegunLogger.java`
* `Weapons/weapons.java`
* `Weapons/Rifle.java`
* `Weapons/RocketLauncher.java`

> [!WARNING]  
> **Skip the Demo:** Do not copy the `com.minegun.demo` package into production. It contains hardcoded world generation and test entities not intended for reuse.

---

## ⚡ Quick Start

Register the systems in your `GlobalEventHandler`:

```java
// 1. Register Weapons
Rifle.register(eventHandler, instanceContainer);
RocketLauncher.register(eventHandler, instanceContainer);

// 2. Initialize Health Management
HealthManagement hm = new HealthManagement();
hm.bossBarMaker(eventHandler);
hm.tickUpdate(eventHandler);

// 3. Give Weapons
Rifle.givePlayer(player);
