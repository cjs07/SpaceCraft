package com.deepwelldevelopment.spacecraft.api.internal;

import com.deepwelldevelopment.spacecraft.api.facts.FactList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class DummyInternalMethodHandler implements IInternalMethodHandler {
    @Override
    public boolean isResearchComplete(String player, String key) {
        return false;
    }

    @Override
    public FactList generateTags(Item item, int meta) {
        return null;
    }

    @Override
    public boolean completeResearch(EntityPlayer player, String tag) {
        return false;
    }
}
