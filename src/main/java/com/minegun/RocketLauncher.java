package com.minegun;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.component.DataComponents;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerHand;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RocketLauncher {
    public static void givePlayer(Player player){
        ItemStack item = ItemStack.builder(Material.WOODEN_AXE)
                .set(DataComponents.CUSTOM_NAME, Component.text("Rocket Launcher", NamedTextColor.YELLOW))
                .set(DataComponents.LORE, List.of(Component.text("Custom Made Weapon"), Component.text("By: VardinsDev")))
                .build();
        player.setItemInHand(PlayerHand.MAIN, item);
        minegunLogger.success(player.getUsername() + " has been given a Rocket Launcher!");
    }
    private static final long cooldownMs = 25L;
    private static final HashMap<UUID, Long> lastShotTime = new HashMap<>();

    public static void register(GlobalEventHandler eventHandler, InstanceContainer instanceContainer) {
        eventHandler.addListener(PlayerUseItemEvent.class, event -> {
            Player player = event.getPlayer();


        });
    }
}
