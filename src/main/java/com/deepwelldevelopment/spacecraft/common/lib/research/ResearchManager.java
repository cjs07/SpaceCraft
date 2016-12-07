package com.deepwelldevelopment.spacecraft.common.lib.research;

import com.deepwelldevelopment.spacecraft.api.facts.Fact;
import com.deepwelldevelopment.spacecraft.api.facts.FactList;
import com.deepwelldevelopment.spacecraft.api.research.ResearchCatergory;
import com.deepwelldevelopment.spacecraft.api.research.ResearchItem;
import com.deepwelldevelopment.spacecraft.common.SpaceCraft;
import com.deepwelldevelopment.spacecraft.common.item.SpaceCraftItems;
import com.deepwelldevelopment.spacecraft.common.lib.util.HexUtils;
import com.deepwelldevelopment.spacecraft.common.lib.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.server.FMLServerHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
    public static ItemStack createNote(ItemStack stack, String key, World world) {
        ResearchItem rr = ResearchCatergory.getResearch(key);
        Fact primaryFact = rr.getResearchPrimaryTag();
        FactList facts = new FactList();
        if (primaryFact == null) {
            return null;
        }
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setString("key", key);
        stack.getTagCompound().setInteger("color", primaryFact.getColor());
        stack.getTagCompound().setBoolean("complete", false);
        stack.getTagCompound().setInteger("copies", 0);
        int radius = 1 + Math.min(3, rr.getComplexity());
        HashMap<String, HexUtils.Hex> hexLocs = HexUtils.generateHexes(radius);
        ArrayList<HexUtils.Hex> outerRing = HexUtils.distributeRingRandomly(radius, rr.tags.size(), world.rand);
        HashMap<String, HexEntry> hexEntries = new HashMap<String, HexEntry>();
        HashMap<String, HexUtils.Hex> hexes = new HashMap<String, HexUtils.Hex>();
        for (HexUtils.Hex hex : hexLocs.values()) {
            hexes.put(hex.toString(), hex);
            hexEntries.put(hex.toString(), new HexEntry(null, 0));
        }
        int count = 0;
        for (HexUtils.Hex hex : outerRing) {
            hexes.put(hex.toString(), hex);
            hexEntries.put(hex.toString(), new HexEntry(rr.tags.getFacts()[count], 1));
            count++;
        }
        for (Fact fact : Fact.getBasicFacts()) {
            facts.add(fact, rr.tags.size() + radius + world.rand.nextInt(radius));
        }
        facts.writeToNBT(stack.getTagCompound(), "facts");
        if (rr.getComplexity() > 1) {
            int blanks = rr.getComplexity() * 2;
            HexUtils.Hex[] temp = hexes.values().toArray(new HexUtils.Hex[0]);
            while (blanks > 0) {
                int index = world.rand.nextInt(temp.length);
                if (hexEntries.get(temp[index].toString()) == null || hexEntries.get(temp[index].toString()).type != 0) {
                    continue;
                }
                boolean gtg = true;
                for (int i = 0; i < 6; i++) {
                    HexUtils.Hex neighbor = temp[index].getNeighbor(i);
                    if (!hexes.containsKey(neighbor.toString()) || hexEntries.get(neighbor.toString()).type != 1) {
                        continue;
                    }
                    int cc = 0;
                    for (int j = 0; j < 6; j++) {
                        if (hexes.containsKey(hexes.get(neighbor.toString()).getNeighbor(j).toString())) {
                            cc++;
                        }
                        if (cc >= 2) {
                            break;
                        }
                    }
                    if (cc >= 2) {
                        continue;
                    }
                    gtg = false;
                    break;
                }
                if (!gtg) {
                    continue;
                }
                hexes.remove(temp[index].toString());
                hexEntries.remove(temp[index].toString());
                temp = hexes.values().toArray(new HexUtils.Hex[0]);
                blanks--;
            }
        }
        NBTTagList gridTag = new NBTTagList();
        for (HexUtils.Hex hex : hexes.values()) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setByte("hexq", (byte) hex.q);
            compound.setByte("hexr", (byte) hex.r);
            compound.setByte("type", (byte) hexEntries.get(hex.toString()).type);
            if (hexEntries.get(hex.toString()).fact != null) {
                compound.setString("fact", hexEntries.get(hex.toString()).fact.getTag());
            }
            gridTag.appendTag(compound);
        }
        stack.getTagCompound().setTag("hexgrid", gridTag);
        return stack;
    }

    //placeholder for error mapping
    public static ResearchNoteData getData(ItemStack stack) {
        if (stack == null) {
            return null;
        }
        ResearchNoteData data = new ResearchNoteData();
        if (stack.getTagCompound() == null) {
            return null;
        }
        data.key = stack.getTagCompound().getString("key");
        data.color = stack.getTagCompound().getInteger("color");
        data.complete = stack.getTagCompound().getBoolean("complete");
        data.copies = stack.getTagCompound().getInteger("copies");
        data.facts.readfromNBT(stack.getTagCompound(), "facts");
        NBTTagList grid = stack.getTagCompound().getTagList("hexgrid", 10);
        data.hexEntries = new HashMap<String, HexEntry>();
        for (int i = 0; i < grid.tagCount(); i++) {
            NBTTagCompound compound = grid.getCompoundTagAt(i);
            byte q = compound.getByte("hexq");
            byte r = compound.getByte("hexr");
            byte type = compound.getByte("type");
            String tag = compound.getString("fact");
            Fact fact = tag != null ? Fact.getFact(tag) : null;
            HexUtils.Hex hex = new HexUtils.Hex(q, r);
            data.hexEntries.put(hex.toString(), new HexEntry(fact, type));
            data.hexes.put(hex.toString(), hex);
        }
        return data;
    }

    //placeholder
    public static void updateData(ItemStack stack, ResearchNoteData data) {
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setString("key", data.key);
        stack.getTagCompound().setInteger("color", data.color);
        stack.getTagCompound().setBoolean("complete", data.complete);
        stack.getTagCompound().setInteger("copies", data.copies);
        data.facts.writeToNBT(stack.getTagCompound(), "facts");
        NBTTagList gridTag = new NBTTagList();
        for (HexUtils.Hex hex : data.hexes.values()) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setByte("hexq", (byte) hex.q);
            compound.setByte("hexr", (byte) hex.r);
            compound.setByte("type", (byte) data.hexEntries.get(hex.toString()).type);
            if (data.hexEntries.get(hex.toString()).fact != null) {
                compound.setString("fact", data.hexEntries.get(hex.toString()).fact.getTag());
            }
            gridTag.appendTag(compound);
        }
        stack.getTagCompound().setTag("hexgrid", gridTag);
    }

    public static ArrayList<String> getResearchForPlayer(String player) {
        ArrayList<String> out = SpaceCraft.proxy.getCompletedResearch().get(player);
        try {
            if (!loadingBlocked && out == null && SpaceCraft.proxy.getClientWorld() == null && FMLServerHandler.instance().getServer().worldServerForDimension(0) != null) {
                SpaceCraft.proxy.getCompletedResearch().put(player, new ArrayList<String>());
                if (player != null) {
                    IPlayerFileData playerNBTManager = FMLServerHandler.instance().getServer().worldServerForDimension(0).getSaveHandler().getPlayerNBTManager();
                    SaveHandler sh = (SaveHandler) playerNBTManager;
                    File dir = ObfuscationReflectionHelper.getPrivateValue(SaveHandler.class, sh, "playersDirectory", "field_75771_c", "c");
                    File file1 = new File(dir, "_" + player + ".scres");
                    File file2 = new File(dir, "_" + player + ".scresbak");
                    ResearchManager.loadPlayerData(player, file1, file2);
                }
                out = SpaceCraft.proxy.getCompletedResearch().get(player);
            }
        } catch (Exception e) {
            //empty catch block
        }
        if (out == null) {
            out = new ArrayList<String>();
        }
        return out;
    }

    public static ArrayList<String> getResearchForPlayerSafe(String player) {
        return SpaceCraft.proxy.getCompletedResearch().get(player);
    }

    public static boolean doesPlayerHaveRequisites(String player, String key) {
        ResearchItem ri = ResearchCatergory.getResearch(key);
        if (ri == null) {
            return true;
        }
        boolean out = true;
        String[] parents = ri.parents;
        ArrayList<String> completed = ResearchManager.getResearchForPlayer(player);
        if (parents != null && parents.length > 0) {
            out = false;
            if (completed != null && completed.size() > 0) {
                out = true;
                for (String s : parents) {
                    if (completed.contains(s)) {
                        continue;
                    }
                    return false;
                }
            }
        }
        if ((parents = ri.parentsHidden) != null && parents.length > 0) {
            if (parents != null && parents.length > 0) {
                out = false;
                for (String s : parents) {
                    if (completed.contains(s)) {
                        continue;
                    }
                    return false;
                }
            }
        }
        return out;
    }

    public static HashMap<String, Byte> getResearchFlagsForPlayer(String player) {
        if (!SpaceCraft.proxy.getCompletedResearchFlags().containsKey(player)) {
            SpaceCraft.proxy.getCompletedResearchFlags().put(player, new HashMap<String, Byte>());
        }
        return SpaceCraft.proxy.getCompletedResearchFlags().get(player);
    }

    public static Fact getCombinationresult(Fact fact1, Fact fact2) {
        Collection<Fact> facts = Fact.facts.values();
        for (Fact fact : facts) {
            if (fact.getComponents() == null || (fact.getComponents()[0] != fact1 || fact.getComponents()[1] != fact2) && (fact.getComponents()[0] != fact2 || fact.getComponents()[1] != fact1)) {
                continue;
            }
            return fact;
        }
        return null;
    }

    public static void setResearchFlag(String player, String key, byte flags) {
        ResearchManager.getResearchFlagsForPlayer(player).put(key, flags);
    }

    public static boolean hasNewResearchFlags(String player, String key) {
        if (ResearchManager.getResearchFlagsForPlayer(player).containsKey(key)) {
            return Utils.getBit(ResearchManager.getResearchFlagsForPlayer(player).get(key), 1);
        }
        return false;
    }

    public static boolean hasNewPageFlag(String player, String key) {
        if (ResearchManager.getResearchFlagsForPlayer(player).containsKey(key)) {
            return Utils.getBit(ResearchManager.getResearchFlagsForPlayer(player).get(key), 2);
        }
        return false;
    }

    public static void setNewPageFlag(String player, String key) {

    }

    public static void clearNewPageFlag(String player, String key) {

    }

    public static void setNewResearchFlag(String player, String key) {

    }

    public static void clearNewResearchFlag(String player, String key) {

    }

    public static boolean completeResearchUnsaved(String player, String key) {
        return false;
    }

    public static boolean completeResearch(String player, String key) {
        return false;
    }

    public static void loadPlayerData(String player, File file1, File file2) {

    }

    public static boolean savePlayerData(String player, File file1, File file2) {
        return false;
    }

    public static void saveResearchNBT(NBTTagCompound compound, EntityPlayer player) {

    }

    public static class HexEntry {

        public Fact fact;
        public int type;

        public HexEntry(Fact fact, int type) {
            this.fact = fact;
            this.type = type;
        }
    }
}
