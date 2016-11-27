package com.deepwelldevelopment.spacecraft.item;

import net.minecraftforge.oredict.OreDictionary;

public class ItemOre extends ItemBase implements ItemOreDict {

    protected String oreName;

    public ItemOre(String itemName, String oreName) {
        super(itemName);

        this.oreName = oreName;
    }

    @Override
    public void initOreDict() {
        OreDictionary.registerOre(name, this);
    }
}
