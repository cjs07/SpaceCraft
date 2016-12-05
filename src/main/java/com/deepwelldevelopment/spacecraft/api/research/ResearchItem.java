package com.deepwelldevelopment.spacecraft.api.research;

import com.deepwelldevelopment.spacecraft.api.facts.Fact;
import com.deepwelldevelopment.spacecraft.api.facts.FactList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ResearchItem {
    public final String key;
    public final String category;
    public final FactList tags;
    public final int displayColumn;
    public final int displayRow;
    public final ItemStack[] iconItem;
    public final ResourceLocation[] iconResource;
    public String[] parents = null;
    public String[] parentsHidden = null;
    public String[] siblings = null;
    private int complexity;
    private boolean isSpecial;
    private boolean isSecondary;
    private boolean isRound;
    private boolean isStub;
    private boolean isVirtual;
    private boolean isHidden;
    private boolean isAutoUnlock;
    private boolean isFlipped;
    private ResearchPage[] pages = null;

    public ResearchItem(String key, String category) {
        this.key = key;
        this.category = category;
        this.tags = new FactList();
        this.iconItem = null;
        this.iconResource = null;
        this.displayColumn = 0;
        this.displayRow = 0;
        this.isVirtual = true;
    }

    public ResearchItem(String key, String category, FactList tags, int displayColumn, int displayRow, int complexity, Object... icon) {
        this.key = key;
        this.category = category;
        this.tags = tags;
        this.displayColumn = displayColumn;
        this.displayRow = displayRow;
        this.complexity = complexity;
        if (icon[0] instanceof ResourceLocation) {
            ResourceLocation[] t;
            t = new ResourceLocation[icon.length];
            System.arraycopy(icon, 0, t, 0, icon.length);
            this.iconResource = t;
        } else {
            this.iconResource = null;
        }
        if (icon[0] instanceof ItemStack) {
            ItemStack[] t;
            t = new ItemStack[icon.length];
            System.arraycopy(icon, 0, t, 0, icon.length);
            this.iconItem = t;
        } else {
            this.iconItem = null;
        }
        if (this.complexity < 1) {
            this.complexity = 1;
        }
        if (this.complexity > 3) {
            this.complexity = 3;
        }
    }

    public ResearchItem setParents(String... parents) {
        this.parents = parents;
        return this;
    }

    public ResearchItem setParentsHidden(String... parentsHidden) {
        this.parentsHidden = parentsHidden;
        return this;
    }

    public ResearchItem setSiblings(String... siblings) {
        this.siblings = siblings;
        return this;
    }

    public ResearchItem setSpecial() {
        isSpecial = true;
        return this;
    }

    public ResearchItem setSecondary() {
        isSecondary = true;
        return this;
    }

    public ResearchItem setRound() {
        isRound = true;
        return this;
    }

    public ResearchItem setStub() {
        isStub = true;
        return this;
    }

    public ResearchItem setHidden() {
        isHidden = true;
        return this;
    }

    public ResearchItem setAutoUnlock() {
        isAutoUnlock = true;
        return this;
    }

    public ResearchItem setFlipped() {
        isFlipped = true;
        return this;
    }

    public int getComplexity() {
        return complexity;
    }

    public ResearchItem setComplexity(int complexity) {
        this.complexity = complexity;
        return this;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public boolean isSecondary() {
        return isSecondary;
    }

    public boolean isRound() {
        return isRound;
    }

    public boolean isStub() {
        return isStub;
    }

    public boolean isVirtual() {
        return isVirtual;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public boolean isAutoUnlock() {
        return isAutoUnlock;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public ResearchPage[] getPages() {
        return pages;
    }

    public ResearchItem setPages(ResearchPage... pages) {
        this.pages = pages;
        return this;
    }

    public int getExperience() {
        if (this.tags != null && this.tags.studySize() > 0) {
            return Math.max(1, (int) Math.sqrt(this.tags.studySize()));
        }
        return 0;
    }

    public Fact getResearchPrimaryTag() {
        Fact fact = null;
        int highest = 0;
        if (this.tags != null) {
            for (Fact tag : this.tags.getFacts()) {
                if (this.tags.getAmount(tag) <= highest) {
                    continue;
                }
                fact = tag;
                highest = this.tags.getAmount(tag);
            }
        }
        return fact;
    }
}
