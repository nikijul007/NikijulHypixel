package com.nikijulHypixel.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class SelectItemsGui extends GuiScreen {

	private int width = 176;
	private int height = 166;

	private GuiTextField textField;

	@Override
	public void initGui() {
		buttonList.add(
				new GuiButton(0, (super.width - width) / 2 + 40, (super.height - height) / 2 + 100, 100, 20, "Enter"));
		buttonList.add(new GuiButton(1, (super.width - width) / 2 + 40, (super.height - height) / 2 + 130, 100, 20,
				"Show Price"));

		textField = new GuiTextField(2, fontRendererObj, (super.width - width) / 2 + 40,
				(super.height - height) / 2 + 50, 100, 20);
		textField.setMaxStringLength(25);
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
		drawTexturedModalRect((super.width - width) / 2, (super.height - height) / 2, 0, 0, width, height);

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
			GuiZealot.sList.add(input);
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(
					new ChatComponentText(EnumChatFormatting.DARK_BLUE + "Item: " + input + " hinzugefügt!"));
			break;
		case 1:
			Minecraft.getMinecraft().displayGuiScreen(new GuiZealot());
			break;
		default:
			break;
		}
		super.actionPerformed(button);
	}

}
