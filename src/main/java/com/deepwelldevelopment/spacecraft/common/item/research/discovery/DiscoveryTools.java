package com.deepwelldevelopment.spacecraft.common.item.research.discovery;

import com.deepwelldevelopment.spacecraft.common.lib.research.ResearchManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class DiscoveryTools extends ItemDiscovery {

    public DiscoveryTools() {
        super("discoveryTools", "tools");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ResearchManager.getResearchForPlayer(playerIn.getName()).add(tag);
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }
}
