package com.nikijulHypixel.gui.category;

import java.io.IOException;

import com.nikijulHypixel.NikijulHypixel;
import com.nikijulHypixel.gui.GuiCategories;
import com.nikijulHypixel.gui.TextureButton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

public class GuiFarming extends GuiCategories{
	
	private int xSize = 300;
	private int ySize = 200;
	
	private int bigButtonWidth = 50;
	private int bigButtonHeight = 20;
	
	private int smallButtonSize = 20;
	
	@Override
	public void initGui() {
		buttonList.clear();
		
		buttonList.add(new TextureButton(7, (this.width - this.xSize) / 2 + 10 + bigButtonWidth, (this.height - this.ySize) / 2 - 15 + bigButtonHeight * 0 + 1 * 5,
				smallButtonSize, smallButtonSize, "apple.png"));
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
		case 7:
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Du hast geklickt!"));

			break;

		default:
			super.actionPerformed(button);
		}
		
	}
}
