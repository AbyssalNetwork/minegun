package com.minegun.Weapons;

import com.minegun.minegunLogger;
import net.minestom.server.MinecraftServer;
import net.minestom.server.component.DataComponents;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerHand;
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
            if (player.getItemInMainHand().material() != Material.WOODEN_AXE) return;

            long now = System.currentTimeMillis();
            long last = lastShotTime.getOrDefault(player.getUuid(), 0L);
            if (now - last < cooldownMs) return;
            lastShotTime.put(player.getUuid(), now);

            Pos eyePos = player.getPosition().add(0.0, player.getEyeHeight(), 0.0);
            Vec direction = player.getPosition().direction();

            double i = 1.0;
            while (i <= 100) {
                Vec point = eyePos.asVec().add(direction.mul(i));
                Pos exactPos = point.asPos();
                Pos blockPos = new Pos(
                        Math.floor(point.x()),
                        Math.floor(point.y()),
                        Math.floor(point.z())
                );

                Player hit = weapons.isPlayerAtPosition(instanceContainer, exactPos, player);

                if (instanceContainer.getBlock(blockPos) != Block.AIR) {
                    instanceContainer.explode((float) point.x(), (float) point.y(), (float) point.z(), 10);
                    break;
                } else if (hit != player) {
                    hit.playSound(
                            Sound.sound(
                                    SoundEvent.ENTITY_FIREWORK_ROCKET_BLAST,
                                    Sound.Source.PLAYER,
                                    0.25f,
                                    1f
                            )
                    );
                    //Hit event

                    MinecraftServer.getSchedulerManager()
                            .buildTask(() -> {
                                player.playSound(
                                        Sound.sound(
                                                SoundEvent.ENTITY_EXPERIENCE_ORB_PICKUP,
                                                Sound.Source.PLAYER,
                                                1f,
                                                1f
                                        )
                                );
                                hit.playSound(
                                        Sound.sound(
                                                SoundEvent.ENTITY_GENERIC_HURT,
                                                Sound.Source.PLAYER,
                                                0.25f,
                                                1f
                                        )
                                );
                            })
                            .delay(TaskSchedule.tick(3))
                            .schedule();

                    break;
                } else {
                    if (player.getInstance() != null) {
                        player.getInstance().sendGroupedPacket(
                                new ParticlePacket(Particle.FLAME, point.x(), point.y(), point.z(), 0f, 0f, 0f, 0f, 1)
                        );
                    }
                }

                i += 0.5;
            }

            player.playSound(
                    Sound.sound(SoundEvent.ENTITY_FIREWORK_ROCKET_BLAST, Sound.Source.PLAYER, 0.25f, 1f)
            );
        });
    }
}
