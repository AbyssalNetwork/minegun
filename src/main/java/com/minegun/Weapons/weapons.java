package com.minegun.Weapons;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.InstanceContainer;

public class weapons {
    public static Player isPlayerAtPosition(InstanceContainer instance, Pos targetPos, Player shooter) {
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

}

