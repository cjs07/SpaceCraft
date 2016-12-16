package com.deepwelldevelopment.spacecraft.api.facts;

import com.deepwelldevelopment.spacecraft.common.SpaceCraft;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Fact {
    public static HashMap<Integer, Fact> mixList = new HashMap<Integer, Fact>();
    public static LinkedHashMap<String, Fact> facts = new LinkedHashMap<String, Fact>();
    String tag;
    Fact[] components;
    int color;
    ResourceLocation image;
    int blend;
    private String chatColor;

    public static final Fact UNBREAKABLE_STONE = new Fact("unbreakablestone",  16777086, "e",  1);

    public Fact(String tag, int color, Fact[] components, ResourceLocation image, int blend) {
        if (facts.containsKey(tag)) {
            throw new IllegalArgumentException(tag + " already registered");
        }
        this.tag = tag;
        this.components = components;
        this.color = color;
        this.image = image;
        this.blend = blend;
        facts.put(tag, this);
        if (components != null) {
            int h = (components[0].getTag() + components[1].getTag()).hashCode();
            mixList.put(h, this);
        }
    }

    public Fact(String tag, int color, Fact[] components) {
        this(tag, color, components, new ResourceLocation(SpaceCraft.modId, "textures/fact/" + tag.toLowerCase() + ".png"), 1);
    }

    public Fact(String tag, int color, Fact[] components, int blend) {
        this(tag, color, components, new ResourceLocation(SpaceCraft.modId, "textures/fact/" + tag.toLowerCase() + ".png"), blend);
    }

    public Fact(String tag, int color, String chatColor, int blend) {
        this(tag, color, (Fact[]) null, blend);
    }

    public static Fact getFact(String tag) {
        return facts.get(tag);
    }

    public static ArrayList<Fact> getBasicFacts() {
        ArrayList<Fact> basics = new ArrayList<Fact>();
        Collection<Fact> pa = facts.values();
        for (Fact fragment : pa) {
            if (fragment.isBasic()) {
                basics.add(fragment);
            }
        }
        return basics;
    }

    public static ArrayList<Fact> getTheorizedFacts() {
        ArrayList<Fact> theorized = new ArrayList<Fact>();
        Collection<Fact> pa = facts.values();
        for (Fact fragment : pa) {
            if (!fragment.isBasic()) {
                theorized.add(fragment);
            }
        }
        return theorized;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Fact[] getComponents() {
        return components;
    }

    public void setComponents(Fact[] components) {
        this.components = components;
    }

    public int getColor() {
        return color;
    }

    public String getChatColor() {
        return chatColor;
    }

    public void setChatColor(String chatColor) {
        this.chatColor = chatColor;
    }

    public ResourceLocation getImage() {
        return image;
    }

    public int getBlend() {
        return blend;
    }

    public void setBlend(int blend) {
        this.blend = blend;
    }

    public boolean isBasic() {
        return this.getComponents() == null || this.getComponents().length != 2;
    }
}
