package com.nikijulHypixel.utils;

import org.lwjgl.input.Keyboard;

import com.nikijulHypixel.NikijulHypixel;
import com.nikijulHypixel.gui.GuiZealot;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyHandler {

	@SubscribeEvent
	public void onKeyPressed(KeyInputEvent event) {
		if (NikijulHypixel.keyBinding.isKeyDown()) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiZealot());
		}

	}
}
