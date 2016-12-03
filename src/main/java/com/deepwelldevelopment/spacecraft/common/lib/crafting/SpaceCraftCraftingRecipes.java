package com.deepwelldevelopment.spacecraft.common.lib.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SpaceCraftCraftingRecipes {

    public static void init() {
        //remove all recipes
        CraftingManager.getInstance().getRecipeList().clear();
//        ResearchCraftingManager.getInstance().doRecipes();

        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.PLANKS, 4), Blocks.LOG);
        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.PLANKS, 4, 1), new ItemStack(Blocks.LOG, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.PLANKS, 4, 2), new ItemStack(Blocks.LOG, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.PLANKS, 4, 3), new ItemStack(Blocks.LOG, 1, 3));
        GameRegistry.addShapedRecipe(new ItemStack(Blocks.CRAFTING_TABLE, 1), "XX", "XX", 'X', Blocks.PLANKS);
    }
}
