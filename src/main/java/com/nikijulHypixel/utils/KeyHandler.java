package com.nikijulHypixel.utils;

import org.lwjgl.input.Keyboard;

import com.nikijulHypixel.NikijulHypixel;
import com.nikijulHypixel.gui.GuiCategories;
import com.nikijulHypixel.gui.GuiPrices;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyHandler {

	@SubscribeEvent
	public void onKeyPressed(KeyInputEvent event) {
		if (NikijulHypixel.keyBindingCategory.isKeyDown()) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiCategories());
		}
		if (NikijulHypixel.keyBindingPrices.isKeyDown()) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiPrices());
		}
		
		if(NikijulHypixel.keyBindingUpdate.isKeyDown()) {
			NikijulHypixel.bazaarManager.refreshPrices();
		}

	}
}
