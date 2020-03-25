package com.nikijulHypixel.gui;

import java.io.IOException;

import com.nikijulHypixel.NikijulHypixel;
import com.nikijulHypixel.bazaar.ActivateItems;
import com.nikijulHypixel.gui.category.GuiFarming;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class SelectItemsGui extends GuiScreen {

	
	private int width = 176;
	private int height = 180;


	private GuiTextField textField;

	@Override
	public void initGui() {
		
		buttonList.clear();
		
		buttonList.add(
				new GuiButton(0, (super.width - width) / 2 + 40, (super.height - height) / 2 + 90, 100, 20, "Enter"));
		
		buttonList.add(new GuiButton(1, (super.width - width) / 2 + 40, (super.height - height) / 2 + 110, 100, 20,
				"Show Price"));
		
		buttonList.add(new GuiButton(2, (super.width - width)/2 + 40, (super.height - height) / 2 + 130, 100, 20, "Category"));
		
		textField = new GuiTextField(3, fontRendererObj, (super.width - width) / 2 + 40,
				(super.height - height) / 2 + 40, 100, 20);
		
		textField.setMaxStringLength(80);
		textField.setFocused(true);
		textField.setCanLoseFocus(false);

	}

	@Override
	public void updateScreen() {
		textField.updateCursorCounter();
		super.updateScreen();
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		textField.textboxKeyTyped(typedChar, keyCode);
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// drawDefaultBackground();

		this.mc.getTextureManager().bindTexture(new ResourceLocation("nikijulhypixel:textures/gui/background.png"));
		
		GlStateManager.pushMatrix();

		GlStateManager.scale(1f * 1f, 1f * 1.3f, 1f);
		drawTexturedModalRect((super.width - width) / 2, (super.height - height) / 2, 0, 0, width, height);
		GlStateManager.popMatrix();
		//drawTexturedModalRect((super.width - width) / 2, (super.height - height) / 2, 0, 0, width, height);

		textField.drawTextBox();

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
			String input = textField.getText();
			
			if(!input.equalsIgnoreCase("")) {
			NikijulHypixel.activateItems.addItem(input);
			textField.setText("");
			}
			
			break;
		case 1:
			NikijulHypixel.bazaarManager.refreshPrices();
			Minecraft.getMinecraft().displayGuiScreen(new GuiPrices());
			break;
		case 2: 
			Minecraft.getMinecraft().displayGuiScreen(new GuiFarming());
			break;
		default:
			break;
		}
		super.actionPerformed(button);
	}

}
