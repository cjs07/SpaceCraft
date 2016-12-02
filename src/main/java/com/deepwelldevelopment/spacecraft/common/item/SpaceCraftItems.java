package com.deepwelldevelopment.spacecraft.common.item;

import com.deepwelldevelopment.spacecraft.common.item.research.discovery.DiscoveryTools;
import com.deepwelldevelopment.spacecraft.common.item.research.draft.ItemResearchDraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SpaceCraftItems {

    public static ItemOre ingotCopper;
    public static ItemOre ingotTin;
    public static ItemOre ingotAluminum;
    public static ItemOre ingotLead;
    public static ItemOre ingotSilver;
    public static ItemOre ingotNickel;
    public static ItemOre ingotPlatinum;
    public static ItemOre ingotUranium;
    public static ItemOre ingotIridium;

    //research drafts
    public static ItemResearchDraft draftTools;

    //discoveries
    public static DiscoveryTools discoveryTools;

    //TODO: Finish refactoring research API
    public static Item researchNotes  = new Item();

    public static void init() {
        ingotCopper = register(new ItemOre("ingotCopper", "ingotCopper"));
        ingotTin = register(new ItemOre("ingotTin", "ingotTin"));
        ingotAluminum = register(new ItemOre("ingotAluminum", "ingotAluminum"));
        ingotLead = register(new ItemOre("ingotLead", "ingotLead"));
        ingotSilver = register(new ItemOre("ingotSilver", "ingotSilver"));
        ingotNickel = register(new ItemOre("ingotNickel", "ingotNickel"));
        ingotPlatinum = register(new ItemOre("ingotPlatinum", "ingotPlatinum"));
        ingotUranium = register(new ItemOre("ingotUranium", "ingotUranium"));
        ingotIridium = register(new ItemOre("ingotIridium", "ingotIridium"));
        draftTools = register(new ItemResearchDraft("draftTools"));
        discoveryTools = register(new DiscoveryTools());
    }

    private static <T extends Item> T register(T item) {
        GameRegistry.register(item);

        if (item instanceof ItemBase) {
            ((ItemBase) item).registerItemModel();
        }
        if (item instanceof ItemOreDict) {
            ((ItemOreDict)item).initOreDict();
        }

        return item;
    }
}
