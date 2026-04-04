package org.vardinsdev.minegun.Weapons;

import net.kyori.adventure.sound.Sound;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.ExplosionSupplier;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.play.ExplosionPacket;
import net.minestom.server.network.packet.server.play.ParticlePacket;
import net.minestom.server.particle.Particle;
import net.minestom.server.sound.SoundEvent;
import net.minestom.server.timer.TaskSchedule;
import net.minestom.server.utils.WeightedList;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface RaycastWeapons extends ExplosionSupplier {

    static Player isPlayerAtPosition(InstanceContainer instance, Pos targetPos, Player shooter) {
        for (Player player : instance.getPlayers()) {
            if (player == shooter) continue;
            Pos feetPos = player.getPosition();
            double height = player.isSneaking() ? 1.5 : 1.8;
            double y = 0.0;
            while (y <= height) {
                Pos checkPos = feetPos.add(0.0, y, 0.0);
                if (targetPos.distanceSquared(checkPos) <= 0.5 * 0.5) return player;
                y += 0.3;
            }
        }
        return shooter;
    }

    HashMap<UUID, Long> lastShotTime = new HashMap<>();

    /// Returns null if nobody was hit, otherwise returns the player that was hit
    static Player shoot(Player player, long cooldownMs, InstanceContainer instanceContainer, Particle particle, Boolean explosion) {

        long now = System.currentTimeMillis();
        long last = (lastShotTime.get(player.getUuid()) == null) ? 0 : lastShotTime.get(player.getUuid());
        if (now - last < cooldownMs) return null;
        lastShotTime.put(player.getUuid(), now);
        Player playerHit = null;

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

            Player hit = RaycastWeapons.isPlayerAtPosition(instanceContainer, exactPos, player);

            if (!instanceContainer.getBlock(blockPos).isAir()) {
                if (explosion) {
                    instanceContainer.sendGroupedPacket(new ExplosionPacket(
                            exactPos,
                            3.5f,
                            0,
                            Vec.ZERO,
                            Particle.EXPLOSION,
                            SoundEvent.ENTITY_GENERIC_EXPLODE,
                            new WeightedList<>(List.of())
                    ));
                }
                break;
            } else if (hit != player) {
                if (explosion) {
                    instanceContainer.sendGroupedPacket(new ExplosionPacket(
                            exactPos,
                            3.5f,
                            0,
                            Vec.ZERO,
                            Particle.EXPLOSION,
                            SoundEvent.ENTITY_GENERIC_EXPLODE,
                            new WeightedList<>(List.of())
                    ));
                }

                hit.playSound(
                        Sound.sound(
                                SoundEvent.ENTITY_FIREWORK_ROCKET_BLAST,
                                Sound.Source.PLAYER,
                                0.25f,
                                1f
                        )
                );

                playerHit = hit;

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
                            new ParticlePacket(particle, point.x(), point.y(), point.z(), 0f, 0f, 0f, 0f, 1)
                    );
                }
            }

            i += 0.5;
        }

        player.playSound(
                Sound.sound(SoundEvent.ENTITY_FIREWORK_ROCKET_BLAST, Sound.Source.PLAYER, 0.25f, 1f)
        );
        return playerHit;
    }
}