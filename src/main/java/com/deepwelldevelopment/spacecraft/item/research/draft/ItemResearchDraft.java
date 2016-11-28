package com.deepwelldevelopment.spacecraft.item.research.draft;

import com.deepwelldevelopment.spacecraft.SpaceCraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemResearchDraft extends Item {

    protected String name;

    public ItemResearchDraft(String name) {
        this.name = name;

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(SpaceCraft.spaceCraftTab);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        itemStackIn.stackSize--;
        GameRegistry.addRecipe(new ItemStack(Items.STICK, 4), "I  ", "I  ", 'I', Blocks.PLANKS);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }
}
