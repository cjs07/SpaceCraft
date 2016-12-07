package com.deepwelldevelopment.spacecraft.client;

import com.deepwelldevelopment.spacecraft.common.CommonProxy;
import com.deepwelldevelopment.spacecraft.common.SpaceCraft;
import com.deepwelldevelopment.spacecraft.common.lib.research.PlayerKnowledge;
import com.deepwelldevelopment.spacecraft.common.lib.research.ResearchManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ClientProxy extends CommonProxy {
    protected PlayerKnowledge playerKnowledge = new PlayerKnowledge();
    protected ResearchManager researchManager = new ResearchManager();

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(SpaceCraft.modId + ":" + id, "inventory"));
    }

    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().theWorld;
    }
}
