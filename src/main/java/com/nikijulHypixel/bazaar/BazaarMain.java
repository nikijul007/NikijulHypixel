package com.nikijulHypixel.bazaar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

import com.nikijulHypixel.NikijulHypixel;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

public class BazaarMain {

	private String key;
	private long lastTimestamp = 0;

	public void loadkey() {
		key = NikijulHypixel.configApiKey.getString("apikey", "ApiKey");
	}

	public String getKey() {
		return key;
	}

	public void refreshPrices() {
		Date date = new Date();
		long currentTimestamp = date.getTime();
		currentTimestamp = currentTimestamp / 1000;
		long diff = (currentTimestamp - lastTimestamp);
		if (diff >= 60) {
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Aktualisiert!"));
			lastTimestamp = currentTimestamp;
		} else {
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(
					new ChatComponentText("Wird in " + (60 - diff) + " Sekunden aktualisiert!"));
		}
	}
}
