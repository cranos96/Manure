package com.natamus.manure.forge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.manure.events.ManureDropEvent;
import com.natamus.manure.util.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.server.ServerLifecycleHooks;

@EventBusSubscriber
public class ForgeManureDropEvent {
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load e) {
        Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getWorld());
        if (level == null) {
            return;
        }

        Util.attemptBlacklistProcessing(level);
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent e) {
        if (!e.phase.equals(TickEvent.Phase.END)) {
            return;
        }

        ManureDropEvent.onServerTick(ServerLifecycleHooks.getCurrentServer());
    }

    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent e) {
        Level level = e.getWorld();
        if (level.isClientSide) {
            return;
        }

        ManureDropEvent.onEntityJoin(e.getEntity(), (ServerLevel)level);
    }

    @SubscribeEvent
    public void onEntityLeave(EntityLeaveWorldEvent e) {
        Level level = e.getWorld();
        if (level.isClientSide) {
            return;
        }

        ManureDropEvent.onEntityLeave(e.getEntity(), (ServerLevel)level);
    }
}