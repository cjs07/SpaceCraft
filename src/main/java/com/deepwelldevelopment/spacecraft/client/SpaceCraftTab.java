package com.deepwelldevelopment.spacecraft.client;

import com.deepwelldevelopment.spacecraft.common.SpaceCraft;
import com.deepwelldevelopment.spacecraft.common.item.SpaceCraftItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class SpaceCraftTab extends CreativeTabs {

    public SpaceCraftTab() {
        super(SpaceCraft.modId);
    }

    @Override
    public Item getTabIconItem() {
        return SpaceCraftItems.ingotCopper;
    }
}
