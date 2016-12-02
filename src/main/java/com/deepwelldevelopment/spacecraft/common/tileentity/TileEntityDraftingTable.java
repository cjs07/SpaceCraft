package com.deepwelldevelopment.spacecraft.common.tileentity;

import com.deepwelldevelopment.spacecraft.common.item.research.ResearchRecipes;
import com.deepwelldevelopment.spacecraft.common.item.research.draft.ItemResearchDraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityDraftingTable extends TileEntity implements ITickable {

    public static final int SIZE = 4;

    ItemStack[] itemsInSlots = new ItemStack[3];

    int stacksRequired = 10;
    ItemResearchDraft activeDraft = null;

    private ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            TileEntityDraftingTable.this.markDirty();
        }
    };

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagList nbtTagList = compound.getTagList("Items", 10);

        for (int i = 0; i < nbtTagList.tagCount(); i++) {
            NBTTagCompound nbtTagCompound = nbtTagList.getCompoundTagAt(i);
            int j = nbtTagCompound.getByte("Slot");

            if (j >= 0 && j < SIZE) {
                itemStackHandler.setStackInSlot(j, ItemStack.loadItemStackFromNBT(nbtTagCompound));
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < SIZE; i++) {
            if (itemStackHandler.getStackInSlot(i) != null) {
                NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setByte("Slot", (byte) i);
                itemStackHandler.getStackInSlot(i).writeToNBT(nbtTagCompound);
                nbtTagList.appendTag(nbtTagCompound);
            }
        }

        compound.setTag("Items", nbtTagList);

        return compound;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) itemStackHandler;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        if (!worldObj.isRemote) {
            for (int i = 0; i < itemStackHandler.getSlots() - 1; i++) {
                itemsInSlots[i] = itemStackHandler.getStackInSlot(i);
            }

            if (itemStackHandler.getStackInSlot(SIZE - 1) == null) {
                ItemResearchDraft draft = ResearchRecipes.instance().getMk1DraftFromInputs(itemsInSlots);
                if (draft != null) {
                    if (activeDraft != null) {
                        if (ItemStack.areItemsEqual(new ItemStack(draft), new ItemStack(activeDraft))) {
                            for (int i = 0; i < itemStackHandler.getSlots() - 1; i++) {
                                if (itemStackHandler.getStackInSlot(i) != null) {
                                    itemStackHandler.getStackInSlot(i).stackSize--;
                                }
                            }
                            stacksRequired--;
                        }
                    } else {
                        for (int i = 0; i < itemStackHandler.getSlots() - 1; i++) {
                            if (itemStackHandler.getStackInSlot(i) != null) {
                                itemStackHandler.getStackInSlot(i).stackSize--;
                            }
                            activeDraft = draft;
                        }
                        stacksRequired--;
                    }
                    if (stacksRequired <= 0) {
                        itemStackHandler.setStackInSlot(SIZE - 1, new ItemStack(draft));
                        activeDraft = null;
                        stacksRequired = 10;
                    }
                }
                //remove any item stacks with no items remaining
                for (int i = 0; i < itemStackHandler.getSlots() - 1; i++) {
                    ItemStack stack = itemStackHandler.getStackInSlot(i);
                    if (stack != null) {
                        if (stack.stackSize <= 0) {
                            itemStackHandler.setStackInSlot(i, null);
                            itemsInSlots[i] = null;
                        }
                    }
                }
            }
        }
        markDirty();
    }

    public int getField(int id) {
        switch (id) {
            case 0:
                return stacksRequired;
            default:
                return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id) {
            case 0:
                stacksRequired = value;
                break;
        }
    }
}
