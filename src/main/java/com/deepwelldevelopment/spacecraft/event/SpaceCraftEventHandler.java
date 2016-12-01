package com.deepwelldevelopment.spacecraft.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpaceCraftEventHandler {

    @SubscribeEvent
    public void entityJoin(EntityJoinWorldEvent event) {
        //change player capabilities object to custom implementation
        if (event.getEntity() instanceof EntityPlayer) {

        }
    }
}
