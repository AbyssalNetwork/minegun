package org.vardinsdev.minegun.Events;

import org.vardinsdev.minegun.HealthManagement;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerTickEvent;

public class PlayerTickEventHandler implements HealthManagement {
    public static void register(GlobalEventHandler eventHandler) {
        eventHandler.addListener(PlayerTickEvent.class, event -> {
            HealthManagement.tickUpdate(event.getPlayer());
        });
    }
}
