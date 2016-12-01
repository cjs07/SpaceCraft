package com.deepwelldevelopment.spacecraft.container;

import com.deepwelldevelopment.spacecraft.tileentity.TileEntityCraftingTable2;
import com.deepwelldevelopment.spacecraft.tileentity.TileEntityDraftingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class ContainerCraftingTable2 extends Container {

    private TileEntityCraftingTable2 te;

    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();

    public ContainerCraftingTable2(InventoryPlayer playerInventory, TileEntityCraftingTable2 te) {
        this.te = te;

        addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 101, 34));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                addSlotToContainer(new Slot(this.craftMatrix, col + row * 3, 7 + col * 18, 16 + row * 18));
            }
        }

        IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int x = 7;
        int y = 83;

        int slotIndex = 0;
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 9; col++) {
                x = 7 + col * 18;
                y = 83 + row * 18;
                addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y));
                slotIndex++;
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 2; col++) {
                x = 131 + col * 18;
                y = 16 + row * 18;
                addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y));
                slotIndex++;
            }
        }

        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                x = 7 + col * 18;
                y = row * 18 + 132;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            x = 7 + row * 18;
            y = 190;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < TileEntityDraftingTable.SIZE) {
                if (!this.mergeItemStack(itemstack1, TileEntityCraftingTable2.SIZE, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, TileEntityCraftingTable2.SIZE, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.canInteractWith(playerIn);
    }
}
