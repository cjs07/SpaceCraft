package com.deepwelldevelopment.spacecraft.common.lib.research;

import com.deepwelldevelopment.spacecraft.common.item.SpaceCraftItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ResearchManager {

    private static final String RESEARCH_TAG = "SPACECRAFT.RESEARCH";

    public static ItemStack createResearchNoteForPlayer(World world, EntityPlayer player, String key) {
        ItemStack note = null;
        boolean addSlot = false;
        int slot = ResearchManager.getResearchSlot(player, key);
        if (slot >= 0) {
            note = player.inventory.getStackInSlot(slot);
        } else if (ResearchManager.consumeInkFromPlayer(player, false) && ResearchManager.consumePaperFromPlayer(player, false)) {
            ResearchManager.consumeInkFromPlayer(player, true);
            ResearchManager.consumePaperFromPlayer(player, true);
            note = ResearchManager.createNote(new ItemStack(SpaceCraftItems.researchNotes), key, world);
            if (!player.inventory.addItemStackToInventory(note)) {
                player.dropItem(note, false);
            }
            player.inventoryContainer.detectAndSendChanges();
        }
        return note;
    }

    public static int getResearchSlot(EntityPlayer player, String key) {
        ItemStack[] inv = player.inventory.mainInventory;
        if (inv == null || inv.length == 0) {
            return -1;
        }
        for (int i = 0; i < inv.length; i++) {
            if (inv[i] == null || inv[i].getItem() == null || inv[i].getItem() != SpaceCraftItems.researchNotes || ResearchManager.getData(inv[i]) == null /*|| !ResearchManager.getData((ItemStack)inv[i]).key.equals(key)*/) {
                continue;
            }
            return i;
        }
        return -1;
    }

    public static boolean consumeInkFromPlayer(EntityPlayer player, boolean simulate) {
        return false;
    }

    public static boolean consumePaperFromPlayer(EntityPlayer playre, boolean simulate) {
        return false;
    }

    public static ItemStack createNote(ItemStack note, String key, World world) {
        return null;
    }

    //placeholder for error mapping
    public static ItemStack getData(ItemStack stack) {
        return null;
    }
}
