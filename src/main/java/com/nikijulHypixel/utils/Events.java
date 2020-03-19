package com.nikijulHypixel.utils;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardEndHandler;

import com.nikijulHypixel.gui.GuiZealot;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Events {
	
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event) {
		if(event.gui instanceof GuiBeacon) {
			event.gui = new GuiZealot();
		}
	}
	
	
}
