package com.nikijulHypixel.gui;

import java.io.IOException;
import java.util.ArrayList;

import com.nikijulHypixel.NikijulHypixel;
import com.nikijulHypixel.bazaar.ActivateItems;
import com.nikijulHypixel.bazaar.AllItems;
import com.nikijulHypixel.config.ConfigNikijulHypixel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiPrices extends GuiScreen {

	private static ArrayList<AllItems> itemList;

	private int xSize = 400;
	private int ySize = 200;
	private int yOffset = 0;

	private int itemsPerPage = 8;
	private int pageNumber = 1;
	private int pages;
	private int lastPageItemAmount;

	@Override
	public void initGui() {

		NikijulHypixel.activateItems.loadItems();
		itemList = NikijulHypixel.bazaarManager.getSelectedItems();

		int length = itemList.size();

		lastPageItemAmount = length % itemsPerPage;
		pages = (lastPageItemAmount != 0) ? 1 : 0;
		pages += length / itemsPerPage;

		buttonList.clear();

		buttonList.add(

				new GuiButton(0, (this.width - this.xSize) / 2 + ((width - ySize) / 40),
						(this.height - this.ySize) / 2 - ((height / 10 - ySize / 10)), 50, 20, "Back"));

		buttonList.add(new GuiButton(2, (int) ((this.width - xSize) / 2 + 18), (int) (this.height / 3 + ySize / 2), 20,
				20, "<"));

		buttonList.add(new GuiButton(1, (int) ((this.width - this.xSize) / 2 + xSize - 40),
				(int) (this.height / 3 + ySize / 2), 20, 20, ">"));

		super.initGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// drawDefaultBackground();
		yOffset = 10;
		this.mc.getTextureManager().bindTexture(new ResourceLocation("nikijulhypixel:textures/gui/background.png"));

		GlStateManager.pushMatrix();

		GlStateManager.scale(1f * 1.55f, 1f * 1, 1f);
		drawTexturedModalRect((int) (((this.width - xSize) / 2) / 1.5), (int) (((this.height - ySize) / 2) / 1.5f), 0,
				0, 256, 200);
		GlStateManager.popMatrix();

		// drawTexturedModalRect((this.width - xSize) / 2, (this.height - ySize)
		// / 2, 0, 0, 256, 256);

		if (!NikijulHypixel.bazaarManager.isKeyValid()) {
			fontRendererObj.drawString("You need an ApiKey to use this mod.", (this.width - this.xSize) / 2 + 10,
					(this.height - this.ySize) / 2 - (height / 7 - ySize / 4) + yOffset, 0xfffff);
			yOffset += 15;
			fontRendererObj.drawString("Go to on the HypixelServer an get Key by /api!",
					(this.width - this.xSize) / 2 + 10,
					(this.height - this.ySize) / 2 - (height / 7 - ySize / 4) + yOffset, 0xfffff);
			yOffset += 15;
			fontRendererObj.drawString("Copy the key in ApiKey.cfg instead of 'YOUR KEY'!",
					(this.width - this.xSize) / 2 + 10,
					(this.height - this.ySize) / 2 - (height / 7 - ySize / 4) + yOffset, 0xfffff);
			yOffset += 15;
			fontRendererObj.drawString("Or use /apikey [APIKEY]", (this.width - this.xSize) / 2 + 10,
					(this.height - this.ySize) / 2 - (height / 7 - ySize / 4) + yOffset, 0xfffff);
		} else {
			int firstItem = (pageNumber - 1) * itemsPerPage;

			fontRendererObj.drawString("ITEM NAME", (this.width - this.xSize) / 2 + 10,
					(this.height - this.ySize) / 2 - (height / 7 - ySize / 4) + yOffset, 0xffffff);

			fontRendererObj.drawString("BUY",
					(this.width - this.xSize) / 2 + 10 + 250 - fontRendererObj.getStringWidth("BUY"),
					(this.height - this.ySize) / 2 - (height / 7 - ySize / 4) + yOffset, 0xffffff);

			fontRendererObj.drawString("SELL",
					(this.width - this.xSize) / 2 + 10 + 320 - fontRendererObj.getStringWidth("SELL"),
					(this.height - this.ySize) / 2 - (height / 7 - ySize / 4) + yOffset, 0xffffff);

			fontRendererObj.drawString("PROFIT",
					(this.width - this.xSize) / 2 + 10 + 380 - fontRendererObj.getStringWidth("PROFIT"),
					(this.height - this.ySize) / 2 - (height / 7 - ySize / 4) + yOffset, 0xffffff);
			yOffset += 15;

			if (pageNumber < pages) {

				for (int i = firstItem; i < firstItem + itemsPerPage; i++) {
					String name = itemList.get(i).name().toLowerCase();
					String sellPrice = NikijulHypixel.configItemsPrices.getString(name, "SellPrice");
					String buyPrice = NikijulHypixel.configItemsPrices.getString(name, "BuyPrice");
					String profit = NikijulHypixel.configItemsPrices.getString(name, "Profit");

					int sellPriceWidth = fontRendererObj.getStringWidth(sellPrice);
					int buyPriceWidth = fontRendererObj.getStringWidth(buyPrice);
					int profitWidth = fontRendererObj.getStringWidth(profit);

					fontRendererObj.drawString(name, (this.width - this.xSize) / 2 + 10,
							(this.height - this.ySize) / 2 - (height / 7 - ySize / 4) + yOffset, 0xffffff);

					fontRendererObj.drawString(buyPrice, (this.width - this.xSize) / 2 + 10 + 250 - buyPriceWidth,
							(this.height - this.ySize) / 2 - (height / 7 - ySize / 4) + yOffset, 0xffffff);

					fontRendererObj.drawString(sellPrice, (this.width - this.xSize) / 2 + 10 + 320 - sellPriceWidth,
							(this.height - this.ySize) / 2 - (height / 7 - ySize / 4) + yOffset, 0xffffff);

					fontRendererObj.drawString(profit, (this.width - this.xSize) / 2 + 10 + 380 - profitWidth,
							(this.height - this.ySize) / 2 - (height / 7 - ySize / 4) + yOffset, 0xffffff);

					yOffset += 15;
				}
			} else {
				for (int i = firstItem; i < firstItem + lastPageItemAmount; i++) {
					String name = itemList.get(i).name().toLowerCase();
					String sellPrice = NikijulHypixel.configItemsPrices.getString(name, "SellPrice");
					String buyPrice = NikijulHypixel.configItemsPrices.getString(name, "BuyPrice");
					String profit = NikijulHypixel.configItemsPrices.getString(name, "Profit");

					int sellPriceWidth = fontRendererObj.getStringWidth(sellPrice);
					int buyPriceWidth = fontRendererObj.getStringWidth(buyPrice);
					int profitWidth = fontRendererObj.getStringWidth(profit);

					fontRendererObj.drawString(name, (this.width - this.xSize) / 2 + 10,
							(this.height - this.ySize) / 2 - (height / 7 - ySize / 4) + yOffset, 0xffffff);

					fontRendererObj.drawString(buyPrice, (this.width - this.xSize) / 2 + 10 + 250 - buyPriceWidth,
							(this.height - this.ySize) / 2 - (height / 7 - ySize / 4) + yOffset, 0xffffff);

					fontRendererObj.drawString(sellPrice, (this.width - this.xSize) / 2 + 10 + 320 - sellPriceWidth,
							(this.height - this.ySize) / 2 - (height / 7 - ySize / 4) + yOffset, 0xffffff);

					fontRendererObj.drawString(profit, (this.width - this.xSize) / 2 + 10 + 380 - profitWidth,
							(this.height - this.ySize) / 2 - (height / 7 - ySize / 4) + yOffset, 0xffffff);

					yOffset += 15;

				}
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
			Minecraft.getMinecraft().displayGuiScreen(new GuiCategories());

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
