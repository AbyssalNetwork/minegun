package com.minegun.demo;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.event.entity.EntitySpawnEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.BlockHandler;

import javax.swing.text.html.parser.Entity;
public class TestDummy {
    public void createDummy(InstanceContainer instanceContainer) {
        EntityCreature zombie = new EntityCreature(EntityType.PLAYER);
        Pos spawnPos = new Pos(0, 40, 0);
        zombie.setInstance(instanceContainer, spawnPos);
    }
}
