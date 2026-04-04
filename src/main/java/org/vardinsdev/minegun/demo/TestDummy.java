package org.vardinsdev.minegun.demo;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.instance.InstanceContainer;

public class TestDummy {
    public static void createDummy(InstanceContainer instanceContainer) {
        EntityCreature zombie = new EntityCreature(EntityType.ZOMBIE);
        Pos spawnPos = new Pos(1, 42, 1);
        zombie.setInstance(instanceContainer, spawnPos);
        zombie.spawn();
    }
}
