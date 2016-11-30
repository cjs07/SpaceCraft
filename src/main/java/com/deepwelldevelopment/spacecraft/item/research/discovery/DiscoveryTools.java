package com.deepwelldevelopment.spacecraft.item.research.discovery;

import com.deepwelldevelopment.spacecraft.item.research.capability.ResearchCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class DiscoveryTools extends ItemDiscovery {

    public DiscoveryTools() {
        super("discoveryTools");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if (playerIn.capabilities instanceof ResearchCapabilities) {
            ((ResearchCapabilities) playerIn.capabilities).addResearch(this);
        } else {
            playerIn.capabilities = new ResearchCapabilities();
            ((ResearchCapabilities)playerIn.capabilities).addResearch(this);
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }
}
