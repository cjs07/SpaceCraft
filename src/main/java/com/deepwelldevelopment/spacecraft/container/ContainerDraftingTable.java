package com.deepwelldevelopment.spacecraft.container;

import com.deepwelldevelopment.spacecraft.container.gui.slot.SlotDraftingTableOutput;
import com.deepwelldevelopment.spacecraft.tileentity.TileEntityDraftingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class ContainerDraftingTable extends Container {

    private TileEntityDraftingTable te;
    private int stacksRequired;

    public ContainerDraftingTable(IInventory playerInventory, TileEntityDraftingTable te) {
        this.te = te;

        // This container references items out of our own inventory (the 9 slots we hold ourselves)
        // as well as the slots from the player inventory so that the user can transfer items between
        // both inventories. The two calls below make sure that slots are defined for both inventories.

        IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int x = 40;
        int y = 8;

        // Add our own slots
        int slotIndex = 0;
        for (int i = 0; i < itemHandler.getSlots()-1; i++) {
            addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y));
            slotIndex++;
            y += 18;
        }

        addSlotToContainer(new SlotDraftingTableOutput(itemHandler, slotIndex, 124, 26));

        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                x = 10 + col * 18;
                y = row * 18 + 70;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            x = 10 + row * 18;
            y = 58 + 70;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); i++) {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if (this.stacksRequired != this.te.getField(0)) {
                icontainerlistener.sendProgressBarUpdate(this, 0, this.te.getField(0));
            }
        }
        this.stacksRequired = this.te.getField(0);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.te.setField(id, data);
    }

    //shift click implementation
    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < TileEntityDraftingTable.SIZE) {
                if (!this.mergeItemStack(itemstack1, TileEntityDraftingTable.SIZE, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, TileEntityDraftingTable.SIZE, false)) {
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
