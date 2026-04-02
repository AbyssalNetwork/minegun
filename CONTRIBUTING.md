# Contributing to MineGun

Thank you for your interest in improving MineGun! As a library built for Minestom, we aim for high performance and clean API design.

## 🛠️ Development Standards
* **Java Version:** All contributions must be compatible with Java 25.
* **Dependency:** Ensure changes are tested against Minestom version `2026.03.03-1.21.11`.
* **Logging:** Use `minegunLogger` for any console output to maintain the library's visual style.

## 🚀 How to Contribute
1. **Fork** the repository and create your branch from `main`.
2. **Implement** your feature or fix. If creating a new weapon, utilize the `weapons.isPlayerAtPosition` utility for consistent hitbox detection.
3. **Test** your changes using the `com.minegun.demo` package to ensure no regressions occur.
4. **Submit** a Pull Request with a clear description of your changes.

## ⚖️ License
By contributing, you agree that your contributions will be licensed under the project's **MIT License**.
