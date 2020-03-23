package com.nikijulHypixel.gui;

import java.io.IOException;

import com.nikijulHypixel.NikijulHypixel;
import com.nikijulHypixel.gui.category.GuiFarming;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

public class GuiCategories extends GuiScreen {

	private int xSize = 300;
	private int ySize = 200;

	private ResourceLocation buttonTexture = new ResourceLocation(NikijulHypixel.MODID + ":textures/gui/apple.png");
	
	private int bigButtonWidth = 50;
	private int bigButtonHeight = 20;
	
	private int smallButtonSize = 20;

	@Override
	public void initGui() {
		

		buttonList.add(new GuiButton(0, (this.width - this.xSize) / 2 + 5, (this.height - this.ySize) / 2 - 15 + bigButtonHeight * 0 + 1 * 5,
				bigButtonWidth, bigButtonHeight, "FARMING"));
		
		buttonList.add(new GuiButton(1, (this.width - this.xSize) / 2 + 5, (this.height - this.ySize) / 2 - 15 + bigButtonHeight * 1 + 2 * 5,
				bigButtonWidth, bigButtonHeight, "ANIMALS"));
		
		buttonList.add(new GuiButton(2, (this.width - this.xSize) / 2 + 5, (this.height - this.ySize) / 2 - 15 + bigButtonHeight * 2 + 3 * 5,
				bigButtonWidth, bigButtonHeight, "MINING"));
		
		buttonList.add(new GuiButton(3, (this.width - this.xSize) / 2 + 5, (this.height - this.ySize) / 2 - 15 + bigButtonHeight * 3 + 4 * 5,
				bigButtonWidth, bigButtonHeight, "WOOD"));
		
		buttonList.add(new GuiButton(4, (this.width - this.xSize) / 2 + 5, (this.height - this.ySize) / 2 - 15 + bigButtonHeight * 4 + 5 * 5,
				bigButtonWidth, bigButtonHeight, "FISHING"));
		
		buttonList.add(new GuiButton(5, (this.width - this.xSize) / 2 + 5, (this.height - this.ySize) / 2 - 15 + bigButtonHeight * 5 + 6 * 5,
				bigButtonWidth, bigButtonHeight, "COMBAT"));
		
		buttonList.add(new GuiButton(6, (this.width - this.xSize) / 2 + 5, (this.height - this.ySize) / 2 - 15 + bigButtonHeight * 6 + 7 * 5,
				bigButtonWidth, bigButtonHeight, "HYPIXEL"));
		
		super.initGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

		this.mc.getTextureManager()
				.bindTexture(new ResourceLocation("nikijulhypixel:textures/gui/categoryBackground.png"));

		drawTexturedModalRect((this.width - this.xSize) / 2, (this.height - this.ySize) / 2 - 15, 0, 0, xSize, ySize);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
		case 0 : 
			Minecraft.getMinecraft().displayGuiScreen(new GuiFarming());
			System.out.println("NEUES GUI!!!");
			break;
		default:
			break;
		}
		super.actionPerformed(button);
	}
}
