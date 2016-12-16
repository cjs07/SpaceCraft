package com.deepwelldevelopment.spacecraft.common.item.research.discovery;

import com.deepwelldevelopment.spacecraft.common.SpaceCraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemDiscovery extends Item {

    protected String name;

    String tag;

    public ItemDiscovery(String name, String tag) {
        this.name = name;
        this.tag = tag;

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(SpaceCraft.spaceCraftTab);
    }

    public String getTag() {
        return tag;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        itemStackIn.stackSize--;
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }
}
