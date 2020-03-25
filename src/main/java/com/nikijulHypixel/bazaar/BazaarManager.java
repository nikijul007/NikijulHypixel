package com.nikijulHypixel.bazaar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.icu.impl.IllegalIcuArgumentException;
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

public class BazaarManager {

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
		// ÄNDERN diff >= 60;
		if (diff >= 5) {
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Aktualisiert!"));
			quickStatus();

			// for(AllItems items : selectedItems) {
			// writeQuickStatus(items.name());
			// }

			lastTimestamp = currentTimestamp;
		} else {
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(
					new ChatComponentText("Wird in " + (60 - diff) + " Sekunden aktualisiert!"));
		}
	}

	private void quickStatus() {
		ArrayList<String> selected = NikijulHypixel.activateItems.loadItems();
		selectedItems.clear();
		for (String s : selected) {
			try {
				AllItems item = AllItems.valueOf(s);
				if (item != null) {
					selectedItems.add(item);
					sendRequest(item.getID(), item.name());
				}

			} catch (IllegalArgumentException e) {
			}

		}
	}

	private void writeQuickStatus(String s, String itemName) {
		// String s = NikijulHypixel.configTemp.getString(itemName, "status");
		itemName = itemName.toLowerCase();

		String buyPriceString = StringUtils.substringBetween(s, firstBuy, lastBuy);
		String sellPriceString = StringUtils.substringBetween(s, firstSell, lastSell);

		double buyPrice = toDouble(buyPriceString);
		double sellPrice = toDouble(sellPriceString);

		double diff = (sellPrice - 0.1) - (buyPrice + 0.1);
		diff = diff - (diff * 0.01);
		diff = formatDouble(diff);

		if (NikijulHypixel.configItemsPrices.hasCategory(itemName)) {
			NikijulHypixel.configItems.removeConfig(itemName);
		}
		NikijulHypixel.configItemsPrices.writeConfig(itemName, "BuyPrice", buyPrice + "");
		NikijulHypixel.configItemsPrices.writeConfig(itemName, "SellPrice", sellPrice + "");
		NikijulHypixel.configItemsPrices.writeConfig(itemName, "Profit", diff + "");
	}

	private void saveRequest(String s, String itemName) {
		itemName = itemName.toLowerCase();
		if (NikijulHypixel.configTemp.hasCategory(itemName)) {
			NikijulHypixel.configTemp.removeConfig(itemName);
		}
		NikijulHypixel.configTemp.writeConfig(itemName, "status", s);
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

			if (status > 200) {
				br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while ((line = br.readLine()) != null) {
					responseContent.append(line);
				}
				br.close();
			} else {
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = br.readLine()) != null) {
					responseContent.append(line);
				}
				br.close();
			}

			// saveRequest(responseContent.toString(), itemName);
			// NikijulHypixel.configTempAllData.writeConfig("items", itemName,
			// responseContent.toString());
			writeQuickStatus(responseContent.toString(), itemName);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			connection.disconnect();
			// responseContent
		}
	}

	private double toDouble(String s) {
		double number;

		number = Double.parseDouble(s);

		number = formatDouble(number);

		return number;
	}

	private double formatDouble(double number) {
		DecimalFormat df = new DecimalFormat("######0.0");
		DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(dfs);
		df.setMaximumFractionDigits(1);

		String s = df.format(number);

		number = Double.parseDouble(s);
		return number;
	}

	public ArrayList<AllItems> getSelectedItems() {
		return selectedItems;
	}

}
