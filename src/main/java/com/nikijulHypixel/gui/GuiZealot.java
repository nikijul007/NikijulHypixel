package com.nikijulHypixel.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiZealot extends GuiScreen {

	private int xSize = 176;
	private int ySize = 166;
	private int yOffset = 0;

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		yOffset = 0;
		this.mc.getTextureManager().bindTexture(new ResourceLocation("nikijulhypixel:textures/gui/background.png"));

		drawTexturedModalRect((this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 0, 0, xSize, ySize);
		for(String s : getText()) {
		fontRendererObj.drawString(s, (this.width - this.xSize) / 2 + 10, (this.height - this.ySize) / 2 + 10 + yOffset , 0x000573);
		yOffset += 15;
		}

	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void drawDefaultBackground() {
		super.drawDefaultBackground();
	}
	
	public String[] getText() {
		String[] s = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
		return s;
	}

}
