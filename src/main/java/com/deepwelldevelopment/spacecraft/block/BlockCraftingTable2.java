package com.deepwelldevelopment.spacecraft.block;

import com.deepwelldevelopment.spacecraft.SpaceCraft;
import com.deepwelldevelopment.spacecraft.tileentity.TileEntityCraftingTable2;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

public class BlockCraftingTable2 extends Block implements ITileEntityProvider {

    public static final int GUI_ID = 2;

    protected String name;

    public BlockCraftingTable2() {
        super(Material.WOOD);

        name = "craftingtable2";
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(SpaceCraft.spaceCraftTab);

        setHardness(3f);
        setResistance(3f);

        GameRegistry.registerTileEntity(TileEntityCraftingTable2.class, SpaceCraft.modId  + "_craftingtable2");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCraftingTable2();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }
        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof TileEntityCraftingTable2)) {
            return false;
        }
        playerIn.openGui(SpaceCraft.instance, GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
}
