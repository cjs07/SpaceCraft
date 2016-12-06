package com.deepwelldevelopment.spacecraft.api.research;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ResearchCategoryList {
    public int minDisplayColumn;
    public int minDisplayRow;
    public int maxDisplayColumn;
    public int maxDisplayRow;
    public ResourceLocation icon;
    public ResourceLocation background;
    public ResourceLocation background2;
    public String researchKey;
    public Map<String, ResearchItem> research = new HashMap<String, ResearchItem>();

    public ResearchCategoryList(ResourceLocation icon, ResourceLocation background, String researchKey) {
        this.icon = icon;
        this.background = background;
        this.background2 = null;
        this.researchKey = researchKey;
    }

    public ResearchCategoryList(ResourceLocation icon, ResourceLocation background, ResourceLocation background2, String researchKey) {
        this.icon = icon;
        this.background = background;
        this.background2 = background2;
        this.researchKey = researchKey;
    }
}
