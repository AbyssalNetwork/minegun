package org.vardinsdev.minegun;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import net.minestom.server.adventure.bossbar.BossBarManager;
import net.minestom.server.entity.Player;
import net.minestom.server.potion.Potion;
import net.minestom.server.potion.PotionEffect;
import net.minestom.server.tag.Tag;

import java.time.Duration;
import java.util.HashMap;
import java.util.UUID;

public interface HealthManagement {
    BossBarManager bossBarManager = new BossBarManager();

    HashMap<UUID, BossBar> healthBars = new HashMap<>();
    HashMap<UUID, BossBar> shieldBars = new HashMap<>();

    HashMap<UUID, Player> killedBy = new HashMap<>();

    HashMap<UUID, Boolean> isLoaded = new HashMap<>();

    Tag<Double> healthTag = Tag.Double("health");
    Tag<Double> shieldTag = Tag.Double("shield");

    static void bossBarMaker(Player player) {
        BossBar healthBar;
        BossBar shieldBar;
        healthBar = BossBar.bossBar(
                Component.text("Health"),
                1f,
                BossBar.Color.GREEN,
                BossBar.Overlay.PROGRESS
        );

        shieldBar = BossBar.bossBar(
                Component.text("Shield"),
                1f,
                BossBar.Color.BLUE,
                BossBar.Overlay.PROGRESS
        );

        bossBarManager.addBossBar(player, healthBar);
        bossBarManager.addBossBar(player, shieldBar);

        healthBars.put(player.getUuid(), healthBar);
        shieldBars.put(player.getUuid(), shieldBar);

        player.setTag(healthTag, 100.0);
        player.setTag(shieldTag, 100.0);

        isLoaded.put(player.getUuid(), true);
    }
    static void damage(Player player, double hitDamage) {
        double shield = getShield(player);
        double health = getHealth(player);
        double overShield;

        if (shield < hitDamage) {
            shield = 0;
            overShield = shield - hitDamage;
            health += overShield;
        } else if (shield == hitDamage) {
            shield = 0;
        } else {
            shield -= hitDamage;
        }

        player.setTag(healthTag, health);
        player.setTag(shieldTag, shield);
    }

    static void tickUpdate(Player player) {
        if (isLoaded.get(player.getUuid()) == null) {
            return;
        }
        if (!isLoaded.get(player.getUuid())) {
            return;
        }
        BossBar playerHealthBar = healthBars.get(player.getUuid());
        BossBar playerShieldBar = shieldBars.get(player.getUuid());

        double shield;
        double health = getHealth(player);

        if (health <= 0) {
            player.teleport(player.getRespawnPoint());
            player.showTitle(
                    Title.title(
                            Component.text("You DIED").color(NamedTextColor.RED),
                            Component.text(""),
                            Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(3), Duration.ofMillis(500))
                    )
            );
            player.addEffect(new Potion(PotionEffect.BLINDNESS, 1, 100));
            health = 100;
            shield = 100;
            player.setTag(healthTag, health);
            player.setTag(shieldTag, shield);
        }

        if (playerHealthBar == null) {
            return;
        }

        playerHealthBar.progress((float) (player.getTag(healthTag) / 100));
        playerShieldBar.progress((float) (player.getTag(shieldTag) / 100));
    }

    static double getHealth(Player player) {
        return player.getTag(healthTag);
    }

    static double getShield(Player player) {
        return player.getTag(shieldTag);
    }

    static Player getKilledBy(Player player) {
        return killedBy.get(player.getUuid());
    }

    static void setKilledBy(Player killed, Player killer) {
        killedBy.put(killed.getUuid(), killer);
    }

}
