package com.deepwelldevelopment.spacecraft.common.lib.research;

import com.deepwelldevelopment.spacecraft.api.facts.FactList;
import com.deepwelldevelopment.spacecraft.common.lib.util.HexUtils;

import java.util.HashMap;

public class ResearchNoteData {

    public String key;
    public int color;
    public HashMap<String, ResearchManager.HexEntry> hexEntries = new HashMap<>();
    public HashMap<String, HexUtils.Hex> hexes = new HashMap<>();
    public boolean complete;
    public int copies;
    public FactList facts = new FactList();

    public boolean isComplete() {
        return complete;
    }
}
