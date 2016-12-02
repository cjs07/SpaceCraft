package com.deepwelldevelopment.spacecraft.common.item.research;

import com.deepwelldevelopment.spacecraft.common.item.SpaceCraftItems;
import com.deepwelldevelopment.spacecraft.common.item.research.discovery.ItemDiscovery;
import com.deepwelldevelopment.spacecraft.common.item.research.draft.ItemResearchDraft;
import com.google.common.collect.Maps;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class ResearchRecipes {

    private static final ResearchRecipes RESEARCH_BASE = new ResearchRecipes();
    Map<ItemStack[], ItemResearchDraft> mk1Drafts = Maps.newHashMap();
    Map<ItemStack[], ItemDiscovery> mk1Discoveries = Maps.newHashMap();

    public static ResearchRecipes instance() {
        return RESEARCH_BASE;
    }

    private ResearchRecipes() {
        addDraft(new ItemStack[] {new ItemStack(Blocks.PLANKS), new ItemStack(Blocks.PLANKS), null}, SpaceCraftItems.draftTools, 1);
    }

    public void addDraft(ItemStack[] inputs, ItemResearchDraft draft, int stage) {
        int numInputs;
        switch (stage) {
            case 1:
                numInputs = 3;
                break;
            default:
                numInputs = 3;
        }

        ItemStack[] modifiedInputs = new ItemStack[numInputs];
        if (inputs.length != modifiedInputs.length) {
            for (int i = 0; i <  inputs.length; i++) {
                modifiedInputs[i] = inputs[i];
                if (i == inputs.length-1 && i < modifiedInputs.length-1) {
                    for (int j = 0; j < modifiedInputs.length; j++) {
                        modifiedInputs[j] = null;
                    }
                }
            }
        } else {
            modifiedInputs = inputs;
        }
        this.mk1Drafts.put(modifiedInputs, draft);
    }

    public void addDiscovery(ItemStack[] inputs, ItemDiscovery discovery) {
        this.mk1Discoveries.put(inputs, discovery);
    }

    public ItemResearchDraft getMk1DraftFromInputs(ItemStack[] inputs) {
        for (Map.Entry <ItemStack[], ItemResearchDraft> entry : this. mk1Drafts.entrySet()) {
            for (int i = 0; i < inputs.length; i++) {
                if (!ItemStack.areItemsEqual(inputs[i], entry.getKey()[i])) {
                    break;
                }
                if (i == (entry.getKey().length - 1)) { //every slot has been checked
                    return entry.getValue();
                }
            }
        }
        return null;
    }
}
