# 🔫 MineGun

**Advanced Firearms Library for Minestom** *Raycasted hitscan weapons, custom health & shield management, and a clean event-driven API.*

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-25-orange)](https://www.oracle.com/java/technologies/downloads/)
[![Wiki](https://img.shields.io/badge/Wiki-minegun.vardinsdev.org-4f9eff?style=flat-square)](https://minegun.vardinsdev.org)

---

## 📖 Documentation
For full API references, implementation details, and guides, visit the official wiki:
### 👉 [**minegun.vardinsdev.org**](https://minegun.vardinsdev.org)

---

## 🌟 Overview
MineGun is a high-performance firearms library built specifically for the [Minestom](https://minestom.net/) server implementation. It replaces vanilla projectile logic with a precise, step-based raycast engine.

### 📚 Wiki Quick-Links
* [**Installation Guide**](https://minegun.vardinsdev.org/#minegun/install) — Adding the library to your project.
* [**Quick Start**](https://minegun.vardinsdev.org/#minegun/quickstart) — Setting up your first event handlers.
* [**Weapon API**](https://minegun.vardinsdev.org/#minegun/weapons) — How the `weapons` class handles raycasting.
* [**Health & Shields**](https://minegun.vardinsdev.org/#minegun/health) — Managing the 100 HP / 100 SP system.
* [**Rifle & Rocket**](https://minegun.vardinsdev.org/#minegun/rifle) — Examples of hitscan and explosive weapons.

---

## 🚀 Quick Start

1. **Register Systems:**
```java
// Initialize the components in your GlobalEventHandler
RocketLauncher.register(eventHandler, instanceContainer);
Rifle.register(eventHandler, instanceContainer);
HealthManagement hm = new HealthManagement();
hm.bossBarMaker(eventHandler);
