package com.deepwelldevelopment.spacecraft.block;

import com.deepwelldevelopment.spacecraft.SpaceCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block {

    protected String name;

    public BlockBase(Material material, String name) {
        super(material);

        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(SpaceCraft.spaceCraftTab);
    }

    public void registerItemModel(ItemBlock itemBlock) {
        SpaceCraft.proxy.registerItemRenderer(itemBlock, 0, name);
    }

    @Override
    public BlockBase setCreativeTab(CreativeTabs tab)  {
        super.setCreativeTab(tab);
        return this;
    }
}
