package com.deepwelldevelopment.spacecraft.client;

import com.deepwelldevelopment.spacecraft.common.CommonProxy;
import com.deepwelldevelopment.spacecraft.common.SpaceCraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(SpaceCraft.modId + ":" + id, "inventory"));
    }
}
