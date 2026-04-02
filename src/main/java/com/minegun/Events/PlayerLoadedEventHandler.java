package com.minegun.Events;

import com.minegun.HealthManagement;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoadedEvent;

public class PlayerLoadedEventHandler implements HealthManagement {
    public static void register(GlobalEventHandler eventHandler) {
        eventHandler.addListener(PlayerLoadedEvent.class, event -> {
            HealthManagement.bossBarMaker(event.getPlayer());
        });
    }
}
