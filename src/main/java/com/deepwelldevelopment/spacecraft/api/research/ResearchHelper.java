package com.deepwelldevelopment.spacecraft.api.research;

import com.deepwelldevelopment.spacecraft.api.SpaceCraftApi;
import net.minecraft.entity.player.EntityPlayer;

public class ResearchHelper {

    public static boolean completeResearch(EntityPlayer player, String key) {
        return SpaceCraftApi.internalMethods.completeResearch(player, key);
    }

    public static boolean isResearchComplete(String player, String[] keys) {
        for (String s : keys) {
            if (!ResearchHelper.isResearchComplete(player, s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isResearchComplete(String player, String key) {
        return SpaceCraftApi.internalMethods.isResearchComplete(player, key);
    }
}
