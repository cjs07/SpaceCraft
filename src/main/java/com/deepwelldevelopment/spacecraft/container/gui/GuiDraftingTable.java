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

    public GuiDraftingTable(TileEntityDraftingTable tileEntity, ContainerDraftingTable container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
