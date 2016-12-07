package com.deepwelldevelopment.spacecraft.common;

import com.deepwelldevelopment.spacecraft.common.lib.research.PlayerKnowledge;
import com.deepwelldevelopment.spacecraft.common.lib.research.ResearchManager;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommonProxy {
    public PlayerKnowledge playerKnowledge;
    public ResearchManager researchManager;

    public PlayerKnowledge getPlayerKnowledge() {
        return this.playerKnowledge;
    }

    public ResearchManager getResearchManager() {
        return this.researchManager;
    }

    public Map<String, ArrayList<String>> getCompletedResearch() {
        return this.playerKnowledge.completedResearch;
    }

    public Map<String, HashMap<String, Byte>> getCompletedResearchFlags() {
        return this.playerKnowledge.researchCompletedFlags;
    }

    public World getClientWorld() {
        return null;
    }

    public void registerItemRenderer(Item item, int meta, String id) {
    }
}
