package com.minegun.Weapons;

import com.minegun.HealthManagement;
import com.minegun.minegunLogger;
import net.minestom.server.MinecraftServer;
import net.minestom.server.component.DataComponents;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerHand;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.network.packet.server.play.ParticlePacket;
import net.minestom.server.particle.Particle;
import net.minestom.server.sound.SoundEvent;
import net.minestom.server.timer.TaskSchedule;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Rifle implements RaycastWeapons, HealthManagement {
    public static void givePlayer(Player player) {
        ItemStack item = ItemStack.builder(Material.WOODEN_HOE)
                .set(DataComponents.CUSTOM_NAME, Component.text("Rifle", NamedTextColor.YELLOW))
                .set(DataComponents.LORE, List.of(Component.text("Custom Made Weapon"), Component.text("By: VardinsDev")))
                .build();
        player.setItemInHand(PlayerHand.MAIN, item);
        minegunLogger.success(player.getUsername() + " has been given a Rifle!");
    }
    private static final long cooldownMs = 25L;
    private static final HashMap<UUID, Long> lastShotTime = new HashMap<>();

    public static void register(GlobalEventHandler eventHandler, InstanceContainer instanceContainer) {
        eventHandler.addListener(PlayerUseItemEvent.class, event -> {
            if (event.getPlayer().getItemInMainHand().material() != Material.WOODEN_HOE) return;
            Player playerHit = RaycastWeapons.shoot(event.getPlayer(), 25, instanceContainer, Particle.CRIT);
            if (playerHit != null) {
                playerHit.damage(DamageType.ARROW, 12f);
                playerHit.heal();
                HealthManagement.damage(playerHit, 25);
                if (HealthManagement.getHealth(playerHit) <= 0) {
                    HealthManagement.setKilledBy(playerHit, event.getPlayer());
                    minegunLogger.info(playerHit.getUsername()  + " was killed by " + HealthManagement.getKilledBy(playerHit).getUsername() + " using a Rifle!");
                    MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(person -> {
                        person.sendMessage(Component.text(playerHit.getUsername() + " has been shot by " + HealthManagement.getKilledBy(playerHit).getUsername() + " using a Rifle!").color(NamedTextColor.YELLOW));
                    });
                }
            }
        });
    }
}
