package com.deepwelldevelopment.spacecraft.common.block;

import com.deepwelldevelopment.spacecraft.common.SpaceCraft;
import com.deepwelldevelopment.spacecraft.common.tileentity.TileEntityDraftingTable;
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

public class BlockDraftingTable extends Block implements ITileEntityProvider {

    public static final int GUI_ID = 1;

    protected String name;

    public BlockDraftingTable(){
        super(Material.WOOD);

        name = "draftingTable";
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(SpaceCraft.spaceCraftTab);

        setHardness(3f);
        setResistance(3f);

        GameRegistry.registerTileEntity(TileEntityDraftingTable.class, SpaceCraft.modId  + "_draftingtable");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityDraftingTable();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }
        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof TileEntityDraftingTable)) {
            return false;
        }
        playerIn.openGui(SpaceCraft.instance, GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
}
