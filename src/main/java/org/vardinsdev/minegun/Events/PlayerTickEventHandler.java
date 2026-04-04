package org.vardinsdev.minegun.Events;

import net.minestom.server.MinecraftServer;
import org.vardinsdev.minegun.HealthManagement;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerTickEvent;

public class PlayerTickEventHandler implements HealthManagement {
    public static void register() {
        GlobalEventHandler eventHandler = MinecraftServer.getGlobalEventHandler();
        eventHandler.addListener(PlayerTickEvent.class, event -> {
            HealthManagement.tickUpdate(event.getPlayer());
        });
    }
}
