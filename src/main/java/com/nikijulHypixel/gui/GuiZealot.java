package com.nikijulHypixel.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiZealot extends GuiScreen {

	private int xSize = 176;
	private int ySize = 166;

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		this.mc.getTextureManager().bindTexture(new ResourceLocation("nikijulhypixel:textures/gui/background.png"));

		drawTexturedModalRect((this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 0, 0, xSize, ySize);
		fontRendererObj.drawString("PETER", (this.width - this.xSize) / 2 + 10, (this.height - this.ySize) / 2 + 10, 1);

	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void drawDefaultBackground() {
		super.drawDefaultBackground();
	}

}
