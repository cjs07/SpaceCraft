package com.deepwelldevelopment.spacecraft.client.gui;

import com.deepwelldevelopment.spacecraft.common.SpaceCraft;
import com.deepwelldevelopment.spacecraft.common.container.ContainerCraftingTable2;
import com.deepwelldevelopment.spacecraft.common.tileentity.TileEntityCraftingTable2;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiCraftingTable2 extends GuiContainer {

    public static final int WIDTH = 174;
    public static final int HEIGHT = 213;

    private static final ResourceLocation CRAFTING_TABLE_2_TEXTURES = new ResourceLocation(SpaceCraft.modId, "textures/gui/craftingtable2.png");

    TileEntityCraftingTable2 tileEntityCraftingTable2;

    public GuiCraftingTable2(TileEntityCraftingTable2 tileEntity, ContainerCraftingTable2 container) {
        super(container);

        this.tileEntityCraftingTable2 = tileEntity;

        xSize = WIDTH;
        ySize = HEIGHT;
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRendererObj.drawString("Crafting", this.xSize / 2 - this.fontRendererObj.getStringWidth("Crafting") / 2, 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(CRAFTING_TABLE_2_TEXTURES);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
