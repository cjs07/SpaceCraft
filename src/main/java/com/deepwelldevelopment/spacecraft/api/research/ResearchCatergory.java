package com.deepwelldevelopment.spacecraft.api.research;

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
}
