package com.deepwelldevelopment.spacecraft.recipe.crafting;

import com.deepwelldevelopment.spacecraft.item.research.discovery.ItemDiscovery;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;

public class ResearchRecipeShaped extends ShapedRecipes implements ResearchCraftingRecipe {

    protected ItemDiscovery requiredResearch;

    public ResearchRecipeShaped(int width, int height, ItemStack[] components, ItemStack output, ItemDiscovery requiredResearch) {
        super(width, height, components, output);
        this.requiredResearch = requiredResearch;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        if (false) {
            return super.matches(inv, worldIn);
        } else {
            return false;
        }
    }


    @Override
    public ItemDiscovery getRequiredResearch() {
        return requiredResearch;
    }
}
