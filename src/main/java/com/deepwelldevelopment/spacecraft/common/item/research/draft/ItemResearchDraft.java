package com.deepwelldevelopment.spacecraft.common.item.research.draft;

import com.deepwelldevelopment.spacecraft.common.SpaceCraft;
import net.minecraft.item.Item;

public class ItemResearchDraft extends Item {

    protected String name;

    public ItemResearchDraft(String name) {
        this.name = name;

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(SpaceCraft.spaceCraftTab);
    }
}
