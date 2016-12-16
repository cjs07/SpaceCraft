package com.deepwelldevelopment.spacecraft.common.lib;

import com.deepwelldevelopment.spacecraft.api.facts.FactList;
import com.deepwelldevelopment.spacecraft.api.internal.IInternalMethodHandler;
import com.deepwelldevelopment.spacecraft.common.lib.research.ResearchManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class InternalMethodHandler implements IInternalMethodHandler {
    @Override
    public boolean isResearchComplete(String player, String key) {
        return ResearchManager.isResearchComplete(player, key);
    }

    @Override
    public FactList generateTags(Item item, int meta) {
        return null;
    }

    @Override
    public boolean completeResearch(EntityPlayer player, String key) {
        if (!ResearchManager.isResearchComplete(player.getName(), key) && ResearchManager.doesPlayerHaveRequisites(player.getName(), key)) {

        }
        return false;
    }
}
