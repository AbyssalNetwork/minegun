package org.vardinsdev.minegun.Weapons;

import org.vardinsdev.minegun.HealthManagement;
import org.vardinsdev.minegun.minegunLogger;
import net.minestom.server.MinecraftServer;
import net.minestom.server.component.DataComponents;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerHand;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.particle.Particle;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;

public interface RocketLauncher extends RaycastWeapons, HealthManagement {
    static void givePlayer(Player player){
        ItemStack item = ItemStack.builder(Material.WOODEN_AXE)
                .set(DataComponents.CUSTOM_NAME, Component.text("Rocket Launcher", NamedTextColor.YELLOW))
                .set(DataComponents.LORE, List.of(Component.text("Custom Made Weapon"), Component.text("By: VardinsDev")))
                .build();
        player.setItemInHand(PlayerHand.MAIN, item);
        minegunLogger.success(player.getUsername() + " has been given a Rocket Launcher!");
    }

   static void register(InstanceContainer instanceContainer) {
        GlobalEventHandler eventHandler = MinecraftServer.getGlobalEventHandler();
        eventHandler.addListener(PlayerUseItemEvent.class, event -> {
            if (event.getPlayer().getItemInMainHand().material() != Material.WOODEN_AXE) return;
            Player playerHit = RaycastWeapons.shoot(event.getPlayer(), 25L, instanceContainer, Particle.FLAME, true);

            if (playerHit != null) {
                playerHit.damage(DamageType.ARROW, 12f);
                playerHit.heal();
                HealthManagement.damage(playerHit, 25);
                if (HealthManagement.getHealth(playerHit) <= 0) {
                    HealthManagement.setKilledBy(playerHit, event.getPlayer());
                    minegunLogger.info(playerHit.getUsername()  + " was killed by " + HealthManagement.getKilledBy(playerHit).getUsername() + " using a Rocket Launcher!");
                    MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(person -> {
                        person.sendMessage(Component.text(playerHit.getUsername() + " has been shot by " + HealthManagement.getKilledBy(playerHit).getUsername() + " using a Rocket Launcher!").color(NamedTextColor.YELLOW));
                    });
                }
            }
        });
    }
}
