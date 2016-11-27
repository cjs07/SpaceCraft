package com.deepwelldevelopment.spacecraft.block;

import com.deepwelldevelopment.spacecraft.item.ItemOreDict;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SpaceCraftBlocks {

    public static BlockOre oreCopper;
    public static BlockOre oreTin;
    public static BlockOre oreAluminum;
    public static BlockOre oreLead;
    public static BlockOre oreSilver;
    public static BlockOre oreNickel;
    public static BlockOre orePlatinum;
    public static BlockOre oreUranium;
    public static BlockOre oreIridium;

    public static BlockDraftingTable draftingTable;

    public static void init() {
        oreCopper = register(new BlockOre("oreCopper", "oreCopper"));
        oreTin = register(new BlockOre("oreTin", "oreTin"));
        oreAluminum = register(new BlockOre("oreAluminum", "oreAluminum"));
        oreLead = register(new BlockOre("oreLead", "oreLead"));
        oreSilver = register(new BlockOre("oreSilver", "oreSilver"));
        oreNickel = register(new BlockOre("oreNickel", "oreNickel"));
        orePlatinum = register(new BlockOre("orePlatinum", "orePlatinum"));
        oreUranium = register(new BlockOre("oreUranium", "oreUranium"));
        oreIridium = register(new BlockOre("oreIridium", "oreIridium"));

        //harvest levels
        oreCopper.setHarvestLevel("pickaxe", Item.ToolMaterial.STONE.getHarvestLevel());
        oreTin.setHarvestLevel("pickaxe", Item.ToolMaterial.STONE.getHarvestLevel());
        oreAluminum.setHarvestLevel("pickaxe", Item.ToolMaterial.STONE.getHarvestLevel());
        oreLead.setHarvestLevel("pickaxe", Item.ToolMaterial.IRON.getHarvestLevel());
        oreNickel.setHarvestLevel("pickaxe", Item.ToolMaterial.IRON.getHarvestLevel());
        orePlatinum.setHarvestLevel("pickaxe", Item.ToolMaterial.IRON.getHarvestLevel());
        oreUranium.setHarvestLevel("pickaxe", Item.ToolMaterial.IRON.getHarvestLevel());
        oreIridium.setHarvestLevel("pickaxe", Item.ToolMaterial.DIAMOND.getHarvestLevel());

        draftingTable = register(new BlockDraftingTable());
    }

    private static <T extends Block> T register(T block, ItemBlock itemBlock) {
        GameRegistry.register(block);
        GameRegistry.register(itemBlock);

        if (block instanceof BlockBase) {
            ((BlockBase)block).registerItemModel(itemBlock);
        }
        if (block instanceof ItemOreDict) {
            ((ItemOreDict)block).initOreDict();
        }
        if (itemBlock instanceof ItemOreDict) {
            ((ItemOreDict)itemBlock).initOreDict();
        }

        return block;
    }

    private static <T extends Block> T register(T block) {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return register(block, itemBlock);
    }

}
