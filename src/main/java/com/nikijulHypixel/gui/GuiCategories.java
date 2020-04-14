package com.nikijulHypixel.gui;

import java.io.IOException;
import java.util.ArrayList;

import com.nikijulHypixel.NikijulHypixel;
import com.nikijulHypixel.bazaar.AllCategories;
import com.nikijulHypixel.bazaar.AllItems;
import com.nikijulHypixel.bazaar.BazaarManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

public class GuiCategories extends GuiScreen {

	private int xSize = 256;
	private int ySize = 205;

	private static String fileLocation = NikijulHypixel.MODID + ":textures/gui/";

	private ResourceLocation buttonTexture;

	private static int itemsPerLine = 6;

	private int bigButtonWidth = 50;
	private int bigButtonHeight = 20;

	private int smallButtonSize = 20;

	private String currentPageName = AllCategories.FARMING.name();

	public GuiCategories(String pageName) {
		currentPageName = pageName;
	}
	
	public GuiCategories() {
	}
	
	@Override
	public void initGui() {
		buttonList.clear();

		int k = 0;
		for (AllCategories category : AllCategories.values()) {
			buttonList.add(new GuiButton(category.getButtonID(), (this.width - xSize) / 2 + 5,
					(this.height - ySize) / 2 - 15 + bigButtonHeight * k + (k + 1) * 5, bigButtonWidth, bigButtonHeight,
					category.name()));
			k++;
		}

		buttonList.add(new GuiButton(8, (this.width - xSize) / 2 + 5,
				(this.height - ySize) / 2 - 15 + bigButtonHeight * k + (k + 1) * 5, bigButtonWidth, bigButtonHeight,
				"SHOW PRICES"));

		int i = 0;
		int j = 0;

		ArrayList<AllItems> selectedItems = NikijulHypixel.bazaarManager.loadItems();

		for (AllItems item : AllItems.values()) {

			int color = 0xff182a;
			if (item.getCategory().equalsIgnoreCase(currentPageName)) {

				buttonTexture = new ResourceLocation(GuiCategories.fileLocation + item.name() + ".png");

				if (selectedItems.contains(item)) {
					color = 0x04ff00;
				}

				if (i >= GuiCategories.itemsPerLine) {
					i = 0;
					j++;
				}
				buttonList.add(new TextureButton(item.getButtonID(),
						(this.width - xSize) / 2 + 10 + bigButtonWidth + (int) (smallButtonSize * 1.7 * i),
						(this.height - ySize) / 2 - 15 + bigButtonHeight * j + (j + 1) * 5, smallButtonSize,
						smallButtonSize, item.name(), currentPageName.toLowerCase(), color));
				i++;
			}

		}

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
		
		if (button.id >= 100 && button.id <= 800) {

			for (AllItems item : AllItems.values()) {
				if ((item.getButtonID()) == button.id) {

					if(!isCtrlKeyDown()) {
					if (!NikijulHypixel.bazaarManager.loadItems().contains(item)) {
						NikijulHypixel.activateItems.addItem(item);
					} else {
						NikijulHypixel.activateItems.removeItem(item);
					}
					initGui();
					break;
					} else {
						NikijulHypixel.bazaarManager.refreshPrice(item);
						Minecraft.getMinecraft().displayGuiScreen(new GuiPrice(item, currentPageName));
					}
				}
			}
		} else if (button.id >= 1 && button.id <= 7) {

			for (AllCategories category : AllCategories.values()) {
				if (category.getButtonID() == button.id) {

					if (!category.name().equalsIgnoreCase(currentPageName)) {
						currentPageName = category.name();

						initGui();
					}
					break;
				}
			}

		} else if (button.id == 8) {

			NikijulHypixel.bazaarManager.refreshPrices();
			Minecraft.getMinecraft().displayGuiScreen(new GuiPrices());

		}

		super.actionPerformed(button);
	}
}
