package com.deepwelldevelopment.spacecraft.item.research.capability;

import com.deepwelldevelopment.spacecraft.item.research.discovery.ItemDiscovery;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public class ResearchCapabilities extends PlayerCapabilities {

    List<ItemDiscovery> completedResearch = Lists.newArrayList();

    @Override
    public void writeCapabilitiesToNBT(NBTTagCompound tagCompound) {
        super.writeCapabilitiesToNBT(tagCompound);

        NBTTagList list = new NBTTagList();
        for (int i = 0; i < completedResearch.size(); i++) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setByte("id", (byte) i);
            ItemStack stack = new ItemStack(completedResearch.get(i));
            stack.writeToNBT(compound);
            list.appendTag(compound);
        }
    }

    @Override
    public void readCapabilitiesFromNBT(NBTTagCompound tagCompound) {
        super.readCapabilitiesFromNBT(tagCompound);

        NBTTagList list = tagCompound.getTagList("Researches", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound compound = list.getCompoundTagAt(i);
            ItemDiscovery research = (ItemDiscovery) ItemStack.loadItemStackFromNBT(compound).getItem();
            int j = compound.getByte("id");
            completedResearch.set(j, research);
            if (completedResearch.contains(research)) {
                completedResearch.add(research);
            }
        }
    }

    public void addResearch(ItemDiscovery discovery) {
        completedResearch.add(discovery);
    }

    //returns whether or not the player has completed the specified research
    public boolean hasResearch(ItemDiscovery research) {
        for (ItemDiscovery toCompare : completedResearch) {
            if (ItemStack.areItemsEqualIgnoreDurability(new ItemStack(research), new ItemStack(toCompare))) {
                System.out.println("[Research] Player can craft this item");
                return true;
            }
        }
        System.out.println("[Research] Player cannot craft this item");
        return false;
    }
}
