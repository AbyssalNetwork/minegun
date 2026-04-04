package org.vardinsdev.minegun.Events;

import net.minestom.server.MinecraftServer;
import org.vardinsdev.minegun.HealthManagement;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoadedEvent;

public class PlayerLoadedEventHandler implements HealthManagement {
    public static void register() {
        GlobalEventHandler eventHandler = MinecraftServer.getGlobalEventHandler();
        eventHandler.addListener(PlayerLoadedEvent.class, event -> {
            HealthManagement.bossBarMaker(event.getPlayer());
        });
    }
}
