package com.deepwelldevelopment.spacecraft.event;

import com.deepwelldevelopment.spacecraft.item.research.capability.ResearchCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class SpaceCraftEventHandler {

    @SubscribeEvent
    public void entityJoin(EntityJoinWorldEvent event) {
        //change player capabilities object to custom implementation
        if (event.getEntity() instanceof EntityPlayer) {
            ((EntityPlayer) event.getEntity()).capabilities = new ResearchCapabilities();
            System.out.println("attached custom capabilities");
        }
    }
}
