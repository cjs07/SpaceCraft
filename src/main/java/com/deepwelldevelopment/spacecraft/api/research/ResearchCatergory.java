package com.deepwelldevelopment.spacecraft.api.research;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

import java.util.Collection;
import java.util.LinkedHashMap;

public class ResearchCatergory {
    public static LinkedHashMap<String, ResearchCategoryList> researchCategories = new LinkedHashMap<String, ResearchCategoryList>();

    public static ResearchCategoryList getResearchList(String key) {
        return researchCategories.get(key);
    }

    public static ResearchItem getResearch(String key) {
        Collection<ResearchCategoryList> rc = researchCategories.values();
        for (ResearchCategoryList catList : rc) {
            Collection<ResearchItem> rl = catList.research.values();
            for (ResearchItem ri : rl) {
                if (ri.key.equals(key)) {
                    return ri;
                }
            }
        }
        return null;
    }

    public static void registerCategory(String key, String researchKey, ResourceLocation icon, ResourceLocation background) {
        if (ResearchCatergory.getResearchList(key) == null) {
            ResearchCategoryList rl = new ResearchCategoryList(researchKey, icon, background);
            researchCategories.put(key, rl);
        }
    }

    public static void registerCategory(String key, String researchKey, ResourceLocation icon, ResourceLocation background, ResourceLocation background2) {
        if (ResearchCatergory.getResearchList(key) == null) {
            ResearchCategoryList rl = new ResearchCategoryList(researchKey, icon, background, background2);
            researchCategories.put(key, rl);
        }
    }

    public static void addResearch(ResearchItem ri) {
        ResearchCategoryList rl = ResearchCatergory.getResearchList(ri.category);
        if (rl != null && !rl.research.containsKey(ri.key)) {
            if (!ri.isVirtual()) {
                for (ResearchItem rr : rl.research.values()) {
                    if (rr.displayColumn != ri.displayColumn || rr.displayRow != ri.displayRow) {
                        continue;
                    }
                    FMLLog.log(Level.FATAL, "[SpaceCraft] Research [" + ri.key + "]  not added as it overlaps with existing research [" + rr.key + "]");
                    return;
                }
            }
            rl.research.put(ri.key, ri);
            if (ri.displayColumn < rl.minDisplayColumn) {
                rl.minDisplayColumn = ri.displayColumn;
            }
            if (ri.displayRow < rl.minDisplayRow) {
                rl.minDisplayRow = ri.displayRow;
            }
            if (ri.displayColumn > rl.maxDisplayColumn) {
                rl.maxDisplayColumn = ri.displayColumn;
            }
            if (ri.displayRow > rl.maxDisplayRow) {
                rl.maxDisplayRow = ri.displayRow;
            }
        }
    }
}
