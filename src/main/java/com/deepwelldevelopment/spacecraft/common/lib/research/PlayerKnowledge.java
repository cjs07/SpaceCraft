package com.deepwelldevelopment.spacecraft.common.lib.research;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerKnowledge {
    public Map<String, ArrayList<String>> completedResearch = new HashMap<String, ArrayList<String>>();
    public Map<String, HashMap<String, Byte>> researchCompletedFlags = new HashMap<String, HashMap<String, Byte>>();

    public void wipePlayerKnowledge(String player) {
        this.completedResearch.remove(player);
    }
}
