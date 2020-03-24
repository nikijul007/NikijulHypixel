package com.nikijulHypixel.bazaar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nikijulHypixel.NikijulHypixel;
import com.nikijulHypixel.config.ConfigNikijulHypixel;

import jdk.nashorn.internal.parser.JSONParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.JsonBlendingMode;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import scala.util.parsing.json.JSON;
import scala.util.parsing.json.JSONObject;

public class BazaarMain {

	private static HttpURLConnection connection;
	
	private String key;
	private long lastTimestamp = 0;
	
	private String firstBuy = "buyPrice\":";
	private String lastBuy = ",\"buyVol";
	
	private String firstSell = "sellPrice\":";
	private String lastSell = ",\"sellVol";
	
	private ArrayList<AllItems> selectedItems = new ArrayList<AllItems>();

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
		if (key == null || key.equals("YOUR KEY") || key.equals("") || key.length() != 36) {
			loadkey();
			return;
		}

		if (diff >= 60) {
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Aktualisiert!"));
			quickStatus();
			lastTimestamp = currentTimestamp;
		} else {
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(
					new ChatComponentText("Wird in " + (60 - diff) + " Sekunden aktualisiert!"));
		}
	}

	private void quickStatus() {
		ArrayList<String> selected = NikijulHypixel.activateItems.loadItems();
		for (String s : selected) {
			AllItems item = AllItems.valueOf(s);
			selectedItems.add(item);
			sendRequest(item.getID(), item.name());

		}
	}

	private void writeQuickStatus(String s, String itemName) {
		itemName = itemName.toLowerCase();
		String buyPrice = StringUtils.substringBetween(s, firstBuy, lastBuy);
		String sellPrice = StringUtils.substringBetween(s, firstSell, lastSell);
		
		double buy = toDouble(buyPrice);
		double sell = toDouble(sellPrice);
		
		System.out.println(buy);
		System.out.println(sell);
		
		NikijulHypixel.configItemsPrices.writeConfig(itemName, "BuyPrice", buyPrice);
		NikijulHypixel.configItemsPrices.writeConfig(itemName, "SellPrice", sellPrice);
	}

	private void sendRequest(String itemID, String itemName) {
		BufferedReader br;
		String line;
		StringBuffer responseContent = new StringBuffer();
		try {
			URL url = new URL("https://api.hypixel.net/skyblock/bazaar/product?key=" + key + "&productId=" + itemID);
			connection = (HttpURLConnection) url.openConnection();
			
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			int status = connection.getResponseCode();
			
			if(status > 200) {
				br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = br.readLine()) !=null) {
					responseContent.append(line);
				}
				br.close();
			}else {
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = br.readLine()) !=null) {
					responseContent.append(line);
				}
				br.close();
			}	
			
			
			writeQuickStatus(responseContent.toString(), itemName);
			//NikijulHypixel.configTempAllData.writeConfig("items", itemName, responseContent.toString());
					
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			connection.disconnect();
			//responseContent
		}
	}
	
	private double toDouble(String s) {
		double number;
		NumberFormat nf = NumberFormat.getInstance();
		
		number =  Double.parseDouble(s);
		nf.setMaximumFractionDigits(1);
		
		number = Double.parseDouble(nf.format(number));
		
		return number;
		
	}

}
