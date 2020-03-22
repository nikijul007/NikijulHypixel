package com.nikijulHypixel.gui;

import java.io.IOException;
import java.util.ArrayList;

import com.nikijulHypixel.NikijulHypixel;
import com.nikijulHypixel.bazaar.ActivateItems;
import com.nikijulHypixel.config.ConfigNikijulHypixel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiZealot extends GuiScreen {

	private static ArrayList<String> itemList;
	
	private int xSize = 176;
	private int ySize = 166;
	private int yOffset = 0;

	private int itemsPerPage = 10;
	private int pageNumber = 1;
	private int pages;
	private int lastPageItemAmount;

	@Override
	public void initGui() {
		
		NikijulHypixel.activateItems.loadItems();
		itemList = NikijulHypixel.activateItems.getSelectedItems();
		
		
		int length = itemList.size();

		lastPageItemAmount = length % itemsPerPage;
		pages = (lastPageItemAmount != 0) ? 1 : 0;
		pages += length / itemsPerPage;

		buttonList.clear();

		buttonList.add(

				new GuiButton(0, (this.width - this.xSize) / 2, (this.height - this.ySize) / 2 - 20, 50, 20, "Back"));

		buttonList.add(
				new GuiButton(2, (this.width - this.xSize) / 2, (this.height - this.ySize) / 2 + ySize, 20, 20, "<"));

		buttonList.add(new GuiButton(1, (this.width - this.xSize) / 2 + xSize - 20,
				(this.height - this.ySize) / 2 + ySize, 20, 20, ">"));

		super.initGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// drawDefaultBackground();
		yOffset = 0;
		this.mc.getTextureManager().bindTexture(new ResourceLocation("nikijulhypixel:textures/gui/background.png"));

		drawTexturedModalRect((this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 0, 0, xSize, ySize);

		int firstItem = (pageNumber - 1) * itemsPerPage;
		if (pageNumber < pages) {

			for (int i = firstItem; i < firstItem + itemsPerPage; i++) {
				fontRendererObj.drawString(itemList.get(i), (this.width - this.xSize) / 2 + 10,
						(this.height - this.ySize) / 2 + 10 + yOffset, 0x000573);
				yOffset += 15;
			}
		} else {
			for (int i = firstItem; i < firstItem + lastPageItemAmount; i++) {
				fontRendererObj.drawString(itemList.get(i), (this.width - this.xSize) / 2 + 10,
						(this.height - this.ySize) / 2 + 10 + yOffset, 0x000573);
				yOffset += 15;

			}
		}

		super.drawScreen(mouseX, mouseY, partialTicks);

	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
		case 0:
			pageNumber = 1;
			Minecraft.getMinecraft().displayGuiScreen(new SelectItemsGui());
			
			break;
		case 2:
			if (pageNumber > 1) {
				pageNumber--;
				initGui();
			}

			break;
		case 1:
			if (pageNumber < pages) {
				pageNumber++;
				initGui();
			}
			
			break;
		default:
			break;
		}
		super.actionPerformed(button);
	}

}
