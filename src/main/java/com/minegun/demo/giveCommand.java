package com.minegun.demo;

import com.minegun.Weapons.Rifle;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandExecutor;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.Nullable;

import com.minegun.Weapons.RocketLauncher;
import com.minegun.minegunLogger;

public class giveCommand extends Command{

    public giveCommand() {
        super("give");

        var item = ArgumentType.Word("item-given")
          .from("RocketLauncher", "Rifle"); // Auto Fill

        addSyntax((sender, context) -> {
            if (sender instanceof Player p) {
              if (p.getPermissionLevel() >= 2) {
                if (context.get(item).equalsIgnoreCase("rocketLauncher")) {
                    RocketLauncher.givePlayer(p);
                }
                if (context.get(item).equalsIgnoreCase("rifle")) {
                    Rifle.givePlayer(p);
                }
              }
            } else {
                minegunLogger.error("Console tried to give itself a rocket launcher!");
            }
        }, item);
    }
}
