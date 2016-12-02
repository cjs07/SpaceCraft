package com.deepwelldevelopment.spacecraft.client;

import com.deepwelldevelopment.spacecraft.common.container.ContainerCraftingTable2;
import com.deepwelldevelopment.spacecraft.common.container.ContainerDraftingTable;
import com.deepwelldevelopment.spacecraft.common.container.gui.GuiCraftingTable2;
import com.deepwelldevelopment.spacecraft.common.container.gui.GuiDraftingTable;
import com.deepwelldevelopment.spacecraft.common.tileentity.TileEntityCraftingTable2;
import com.deepwelldevelopment.spacecraft.common.tileentity.TileEntityDraftingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityDraftingTable) {
            return new ContainerDraftingTable(player.inventory, (TileEntityDraftingTable) te);
        } else if (te instanceof TileEntityCraftingTable2) {
            return new ContainerCraftingTable2(player.inventory, (TileEntityCraftingTable2) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityDraftingTable) {
            TileEntityDraftingTable containerTileEntity = (TileEntityDraftingTable) te;
            return new GuiDraftingTable(containerTileEntity, new ContainerDraftingTable(player.inventory, containerTileEntity));
        } else if (te instanceof TileEntityCraftingTable2) {
            TileEntityCraftingTable2 containerTileEntity = (TileEntityCraftingTable2) te;
            return new GuiCraftingTable2(containerTileEntity, new ContainerCraftingTable2(player.inventory, containerTileEntity));
        }
        return null;
    }
}
