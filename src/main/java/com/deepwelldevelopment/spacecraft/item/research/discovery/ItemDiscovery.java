package com.deepwelldevelopment.spacecraft.item.research.discovery;

import com.deepwelldevelopment.spacecraft.SpaceCraft;
import net.minecraft.item.Item;

public class ItemDiscovery extends Item {

    protected String name;

    public ItemDiscovery(String name) {
        this.name = name;

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(SpaceCraft.spaceCraftTab);
    }
}
