package com.deepwelldevelopment.spacecraft.recipe.crafting;

import com.deepwelldevelopment.spacecraft.item.research.discovery.ItemDiscovery;
import com.google.common.base.Throwables;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.List;

public class ResearchRecipeShapeless extends ShapelessRecipes implements ResearchCraftingRecipe {

    protected ItemDiscovery requiredResearch;

    public ResearchRecipeShapeless(ItemStack output, List<ItemStack> inputList, ItemDiscovery requiredresearch) {
        super(output, inputList);
        this.requiredResearch = requiredresearch;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        EntityPlayer player = findPlayer(inv);
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

    private static final Field eventHandlerField = ReflectionHelper.findField(InventoryCrafting.class, "eventHandler");
    private static final Field containerPlayerPlayerField = ReflectionHelper.findField(ContainerPlayer.class, "thePlayer");
    private static final Field slotCraftingPlayerField = ReflectionHelper.findField(SlotCrafting.class, "thePlayer");

    private static EntityPlayer findPlayer(InventoryCrafting inv) {
        try {
            Container container = (Container) eventHandlerField.get(inv);
            if (container instanceof ContainerPlayer) {
                return (EntityPlayer) containerPlayerPlayerField.get(container);
            } else if (container instanceof ContainerWorkbench) {
                return (EntityPlayer) slotCraftingPlayerField.get(container.getSlot(0));
            } else {
                // don't know the player
                return null;
            }
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
}
