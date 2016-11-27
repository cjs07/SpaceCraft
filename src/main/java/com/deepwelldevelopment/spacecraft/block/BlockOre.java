package com.deepwelldevelopment.spacecraft.block;

import com.deepwelldevelopment.spacecraft.item.ItemOreDict;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.oredict.OreDictionary;

public class BlockOre extends BlockBase implements ItemOreDict {

    protected String oreName;

    public BlockOre(String blockName, String oreName) {
        super(Material.ROCK, blockName);

        this.oreName = oreName;

        setHardness(3f);
        setResistance(5f);
    }

    @Override
    public BlockOre setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }

    @Override
    public void initOreDict() {
        OreDictionary.registerOre(oreName, this);
    }
}
