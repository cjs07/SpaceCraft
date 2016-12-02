package com.deepwelldevelopment.spacecraft.common.lib.crafting;

import com.deepwelldevelopment.spacecraft.common.item.research.discovery.ItemDiscovery;
import com.google.common.base.Throwables;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

public class ResearchRecipeShaped extends ShapedRecipes implements ResearchCraftingRecipe {

    protected ItemDiscovery requiredResearch;

    public ResearchRecipeShaped(int width, int height, ItemStack[] components, ItemStack output, ItemDiscovery requiredResearch) {
        super(width, height, components, output);
        this.requiredResearch = requiredResearch;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        EntityPlayer player = findPlayer(inv);
        if (player != null) {
            return false;
        }
        return true;
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
