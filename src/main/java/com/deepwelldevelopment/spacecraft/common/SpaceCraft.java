package com.deepwelldevelopment.spacecraft.common;

import com.deepwelldevelopment.spacecraft.common.block.SpaceCraftBlocks;
import com.deepwelldevelopment.spacecraft.client.SpaceCraftTab;
import com.deepwelldevelopment.spacecraft.common.lib.event.SpaceCraftEventHandler;
import com.deepwelldevelopment.spacecraft.client.GuiHandler;
import com.deepwelldevelopment.spacecraft.common.item.SpaceCraftItems;
import com.deepwelldevelopment.spacecraft.common.lib.crafting.SpaceCraftCraftingRecipes;
import com.deepwelldevelopment.spacecraft.common.lib.crafting.SpaceCraftSmeltingRecipes;
import com.deepwelldevelopment.spacecraft.common.lib.world.SpaceCraftWorldGen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = SpaceCraft.modId, name = SpaceCraft.name, version = SpaceCraft.version)
public class SpaceCraft {

    public static final String modId = "spacecraft";
    public static final String name  = "SpaceCraft";
    public static final String version = "0.0.1";

    @Mod.Instance(modId)
    public static SpaceCraft instance;

    @SidedProxy(serverSide = "com.deepwelldevelopment.spacecraft.common.CommonProxy", clientSide = "com.deepwelldevelopment.spacecraft.client.ClientProxy")
    public static CommonProxy proxy;

    public static final SpaceCraftTab spaceCraftTab = new SpaceCraftTab();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        SpaceCraftItems.init();
        SpaceCraftBlocks.init();

        GameRegistry.registerWorldGenerator(new SpaceCraftWorldGen(), 3);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        SpaceCraftCraftingRecipes.init();
        SpaceCraftSmeltingRecipes.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        MinecraftForge.EVENT_BUS.register(new SpaceCraftEventHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
