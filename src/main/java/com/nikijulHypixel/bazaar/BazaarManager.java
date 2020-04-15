package com.nikijulHypixel.bazaar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.icu.impl.IllegalIcuArgumentException;
import com.nikijulHypixel.NikijulHypixel;
import com.nikijulHypixel.config.ConfigNikijulHypixel;
import com.sun.jna.platform.win32.Sspi.TimeStamp;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.JsonBlendingMode;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class BazaarManager {

	private static HttpURLConnection connection;

	private String key;
	private int updateTime;
	private long lastTimestamp = 0;
	private String lastUpdateTime = "";

	private String firstBuySummary = "buy_summary";
	private String lastBuySummary = "}";

	private String firstSellSummary = "sell_summary";
	private String lastSellSummary = "}";

	private String first = "pricePerUnit\":"; // sell summary ... pricePerUnit
	private String last = ",\"orders"; // sell summary ... orders

	private static DateFormat dateFormat = new SimpleDateFormat("kk:mm:ss  dd.MM.yyyy");

	private ArrayList<AllItems> selectedItems = new ArrayList<AllItems>();

	public void loadkey() {
		key = NikijulHypixel.configApiKey.getString("apikey", "ApiKey");
	}

	public String getLastUpdateTimeString() {
		return lastUpdateTime;
	}

	public void loadUpdateTime() {

		try {
			updateTime = Integer.parseInt(NikijulHypixel.configApiKey.getString("bazaarupdatetime", "Time"));
		} catch (NumberFormatException e) {
			if (NikijulHypixel.configApiKey.hasCategory("bazaarupdatetime")) {
				NikijulHypixel.configApiKey.removeConfig("bazaarupdatetime");
			}
			updateTime = 20;
			NikijulHypixel.configApiKey.writeConfig("bazaarupdatetime", "Time", "20");
		}
	}

	public boolean isKeyValid() {
		loadkey();
		if (key == null || key.equals("YOUR KEY") || key.equals("") || key.length() != 36) {
			return false;
		}
		return true;
	}

	public String getKey() {
		return key;
	}

	public void refreshPrices() {
		loadUpdateTime();
		Date date = new Date();
		long currentTimestamp = date.getTime();
		currentTimestamp = currentTimestamp / 1000;
		long diff = (currentTimestamp - lastTimestamp);
		if (!isKeyValid()) {
			return;
		}
		// ÄNDERN diff >= 60;
		System.out.println(updateTime);
		if (diff >= updateTime) {
			lastTimestamp = currentTimestamp;
			Timestamp ts = new Timestamp(lastTimestamp);
			Date dateUpdate = new Date(ts.getTime());

			lastUpdateTime = dateFormat.format(date);

			quickStatus();

		} else {
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(
					new ChatComponentText("Next update in " + (updateTime - diff) + " seconds!"));
		}
	}

	public void refreshPrice(AllItems item) {
			sendRequest(item.getID(), item.name());
	
	}

	public ArrayList<AllItems> loadItems() {
		ArrayList<String> selected = NikijulHypixel.activateItems.loadItems();
		selectedItems.clear();

		if (!selected.isEmpty()) {
			for (String s : selected) {
				AllItems item = AllItems.valueOf(s);
				selectedItems.add(item);
			}
		}
		return selectedItems;

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

		String buySummary = StringUtils.substringBetween(s, firstBuySummary, lastBuySummary);

		String sellSummary = StringUtils.substringBetween(s, firstSellSummary, lastSellSummary);

		String buyPriceString = StringUtils.substringBetween(buySummary, first, last);
		String sellPriceString = StringUtils.substringBetween(sellSummary, first, last);

		double buyPrice = toDouble(buyPriceString);
		double sellPrice = toDouble(sellPriceString);

		double diff = (sellPrice - 0.1d) - (buyPrice + 0.1d);
		diff = diff - (sellPrice * 0.01);

		buyPriceString = formatDouble(buyPrice);
		sellPriceString = formatDouble(sellPrice);

		String diffString = formatDouble(diff);

		if (NikijulHypixel.configItemsPrices.hasCategory(itemName)) {
			NikijulHypixel.configItems.removeConfig(itemName);
		}
		NikijulHypixel.configItemsPrices.writeConfig(itemName, "BuyPrice", buyPriceString);
		NikijulHypixel.configItemsPrices.writeConfig(itemName, "SellPrice", sellPriceString);
		NikijulHypixel.configItemsPrices.writeConfig(itemName, "Profit", diffString);
	}

	private void sendRequest(String itemID, String itemName) {
		BufferedReader br;
		String line;
		StringBuffer responseContent = new StringBuffer();
		try {
			URL url = new URL("https://api.hypixel.net/skyblock/bazaar/product?key=" + key + "&productId=" + itemID);
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
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
		return number;
	}

	private String formatDouble(double number) {
		String s = null;
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator(',');
		symbols.setGroupingSeparator('.');

		DecimalFormat df = new DecimalFormat();

		df.setDecimalFormatSymbols(symbols);
		df.setMaximumFractionDigits(1);

		s = df.format(number);

		return s;
	}

	public ArrayList<AllItems> getSelectedItems() {
		return loadItems();
	}

}
