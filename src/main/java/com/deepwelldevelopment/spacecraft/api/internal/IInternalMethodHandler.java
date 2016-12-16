package com.deepwelldevelopment.spacecraft.api.internal;

import com.deepwelldevelopment.spacecraft.api.facts.FactList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public interface IInternalMethodHandler {
    
    boolean isResearchComplete(String player, String key);
    
    FactList generateTags(Item item, int meta);
    
    boolean completeResearch(EntityPlayer player, String key);
}
