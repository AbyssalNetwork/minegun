package org.vardinsdev.minegun.demo;

import org.vardinsdev.minegun.HealthManagement;
import org.vardinsdev.minegun.Weapons.Rifle;
import org.vardinsdev.minegun.Weapons.RocketLauncher;
import net.minestom.server.Auth;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerGameModeRequestEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;
import org.vardinsdev.minegun.minegunLogger;

// This is how you make a comment you idiot.
public class Main {
    static void main(String[] args) {
        minegunLogger.printBanner();
        MinecraftServer minecraftServer = MinecraftServer.init(new Auth.Online());
        minegunLogger.info("Server Initiated!");

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();
        minegunLogger.info("Instance Created!");

        instanceContainer.setGenerator(unit -> unit.modifier().fillHeight(39, 40, Block.STONE));
        minegunLogger.info("Chunks filled!");
        instanceContainer.setChunkSupplier(LightingChunk::new); // adds lighting
        minegunLogger.info("Lighting added!");

        GlobalEventHandler eventHandler = MinecraftServer.getGlobalEventHandler();

        Rifle.register(instanceContainer);
        minegunLogger.info("Rifle event loaded!");

        RocketLauncher.register(instanceContainer);

        MinecraftServer.getCommandManager().register(new giveCommand());
        minegunLogger.info("Give Command registered!");

        eventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            event.setSpawningInstance(instanceContainer);
            event.getPlayer().setPermissionLevel(4);
            minegunLogger.info(event.getPlayer().getUsername() + " has permission level " + event.getPlayer().getPermissionLevel() + "!");
            event.getPlayer().setRespawnPoint(new Pos(0,42, 0));
            Rifle.givePlayer(event.getPlayer());
        });
        minegunLogger.info("Player configuration added!");

        eventHandler.addListener(PlayerGameModeRequestEvent.class, event -> {
           if (event.getPlayer().getPermissionLevel() >= 2 ) {
               event.getPlayer().setGameMode(event.getRequestedGameMode());
           }
        });
        minegunLogger.info("F3 + F4 Registered");

        TestDummy.createDummy(instanceContainer);
        minegunLogger.info("Dummy made!");

        //Health Stuff

        HealthManagement.register();
        minegunLogger.info("Health Events Registerd");

        minecraftServer.start("0.0.0.0", 25565);
        minegunLogger.success("Minegun Demo Started!");
    }
}
