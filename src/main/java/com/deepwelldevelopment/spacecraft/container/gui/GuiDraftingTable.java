package com.deepwelldevelopment.spacecraft.container.gui;

import com.deepwelldevelopment.spacecraft.SpaceCraft;
import com.deepwelldevelopment.spacecraft.container.ContainerDraftingTable;
import com.deepwelldevelopment.spacecraft.tileentity.TileEntityDraftingTable;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiDraftingTable extends GuiContainer {

    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;

    private static final ResourceLocation background = new ResourceLocation(SpaceCraft.modId, "textures/gui/draftingtable.png");

    TileEntityDraftingTable tileEntityDraftingTable;

    public GuiDraftingTable(TileEntityDraftingTable tileEntity, ContainerDraftingTable container) {
        super(container);

        this.tileEntityDraftingTable = tileEntity;

        xSize = WIDTH;
        ySize = HEIGHT;
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRendererObj.drawString("Drafting", this.xSize / 2 - this.fontRendererObj.getStringWidth("Drafting") / 2, 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int l = this.getProgressScaled(36);
        this.drawTexturedModalRect(guiLeft + 71, guiTop + 24, 180, 0, l + 1, 21);
    }

    int getProgressScaled(int pixels) {
        return  (10-tileEntityDraftingTable.getField(0)) * pixels / 10;
    }
}
