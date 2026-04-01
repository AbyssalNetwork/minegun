package com.minegun.demo;

import com.minegun.Rifle;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandExecutor;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.Nullable;

import com.minegun.RocketLauncher;
import com.minegun.minegunLogger;

public class giveCommand extends Command{

    public giveCommand() {
        super("give");

        var item = ArgumentType.Word("item-given")
          .from("RocketLauncher", "Rifle"); // Auto Fill

        addSyntax((sender, context) -> {
            if (sender instanceof Player) {
                if (context.get(item).equalsIgnoreCase("rocketLauncher")) {
                    RocketLauncher.givePlayer((Player) sender);
                }
                if (context.get(item).equalsIgnoreCase("Rifle")) {
                    Rifle.givePlayer((Player) sender);
                }
            } else {
                minegunLogger.error("Console tried to give itself a rocket launcher!");
            }
        }, item);
    }
}
