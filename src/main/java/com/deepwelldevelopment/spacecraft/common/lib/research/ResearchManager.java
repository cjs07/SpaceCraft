package com.deepwelldevelopment.spacecraft.common.lib.research;

import com.deepwelldevelopment.spacecraft.api.facts.Fact;
import com.deepwelldevelopment.spacecraft.common.item.SpaceCraftItems;
import com.deepwelldevelopment.spacecraft.common.lib.util.HexUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ResearchManager {

    private static final String RESEARCH_TAG = "SPACECRAFT.RESEARCH";
    public static boolean loadingBlocked = false;

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

    public static boolean consumeInkFromPlayer(EntityPlayer player, boolean doit) {
        ItemStack[] inv = player.inventory.mainInventory;
        for (ItemStack stack : inv) {
            if (stack == null || !(stack.getItem() instanceof ItemDye) || !(stack.getItemDamage() == 0)) {
                continue;
            }
            if (doit) {
                stack.stackSize--;
            }
            return true;
        }
        return false;
    }

    public static boolean consumePaperFromPlayer(EntityPlayer player, boolean doit) {
        ItemStack[] inv = player.inventory.mainInventory;
        for (ItemStack stack : inv) {
            if (!(stack.getItem() == Items.PAPER)) {
                continue;
            }
            if (doit) {
                stack.stackSize--;
            }
            return true;
        }
        return false;
    }

    public static boolean checkResearchCompletion(ItemStack contents, ResearchNoteData note, String username) {
        ArrayList<String> checked = new ArrayList<String>();
        ArrayList<String> main = new ArrayList<String>();
        ArrayList<String> remains = new ArrayList<String>();
        for (HexUtils.Hex hex : note.hexes.values()) {
            if (note.hexEntries.get(hex.toString()).type != 1) {
                continue;
            }
            main.add(hex.toString());
        }
        for (HexUtils.Hex hex : note.hexes.values()) {
            if (note.hexEntries.get(hex.toString()).type != 1) {
                continue;
            }
            main.remove(hex.toString());
            ResearchManager.checkConnections(note, hex, checked, main, remains, username);
            break;
        }
        if (main.size() == 0) {
            ArrayList<String> remove = new ArrayList<String>();
            for (HexUtils.Hex hex : note.hexes.values()) {
                if (note.hexEntries.get(hex.toString()).type == 1 || remains.contains(hex.toString())) {
                    continue;
                }
                remove.add(hex.toString());
            }
            for (String s : remove) {
                note.hexEntries.remove(s);
                note.hexes.remove(s);
            }
            note.complete = true;
            ResearchManager.updateData(contents, note);
            return true;
        }
        return false;
    }

    private static void checkConnections(ResearchNoteData note, HexUtils.Hex hex, ArrayList<String> checked, ArrayList<String> main, ArrayList<String> remains, String username) {
        checked.add(hex.toString());
        for (int i = 0; i < 6; i++) {
            HexUtils.Hex target = hex.getNeighbor(i);
            if (checked.contains(target.toString()) || !note.hexEntries.containsKey(target.toString()) || note.hexEntries.get(target.toString()).type < 1) {
                continue;
            }
            Fact fact1 = note.hexEntries.get(hex.toString()).fact;
            Fact fact2 = note.hexEntries.get(target.toString()).fact;
            if (fact1.isBasic() || fact1.getComponents()[0] != fact1 && fact2.getComponents()[1] != fact1) {
                continue;
            }
            remains.add(target.toString());
            if (note.hexEntries.get(target.toString()).type == 1) {
                main.remove(target.toString());
            }
            ResearchManager.checkConnections(note, target, checked, main, remains, username);
        }
    }


    //placeholder
    public static ItemStack createNote(ItemStack note, String key, World world) {
        return null;
    }

    //placeholder for error mapping
    public static ResearchNoteData getData(ItemStack stack) {
        return null;
    }

    //placeholder
    public static void updateData(ItemStack stack, ResearchNoteData note) {
    }

    public static class HexEntry {

        public int type;
        public Fact fact;

        public HexEntry(int type) {
            this.type = type;
        }
    }
}
