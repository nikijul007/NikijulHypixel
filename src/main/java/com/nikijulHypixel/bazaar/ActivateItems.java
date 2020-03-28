package com.nikijulHypixel.bazaar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.nikijulHypixel.NikijulHypixel;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ActivateItems {
	private ArrayList<String> selectedItems = new ArrayList<String>();

	public ArrayList<String> loadItems() {
		String items = null;
		ArrayList<String> itemList = new ArrayList<String>();

		if (NikijulHypixel.configItems.hasCategory("items") && NikijulHypixel.configItems.hasKey("items", "Items")) {
			items = NikijulHypixel.configItems.getString("items", "Items");
			String[] itemArray = items.split(",");

			for (String item : itemArray) {
				itemList.add(item);
			}

		}

		return itemList;
	}

	public void saveItems() {
		String items = "";
		
		if (NikijulHypixel.configItems.hasCategory("items")) {
			NikijulHypixel.configItems.removeConfig("items");
		}
		
		if(!selectedItems.isEmpty()) {
		for (String item : selectedItems) {
			items = items.concat(item + ",");
		}

		NikijulHypixel.configItems.writeConfig("items", "Items", items);
		}
	}

	public void addItem(AllItems item) {
		selectedItems = loadItems();

		String name = item.name();
		
		if (!selectedItems.contains(name)) {
			selectedItems.add(name);
			saveItems();
		}
	}
	
	public void removeItem(AllItems item) {
		selectedItems = loadItems();

		String name = item.name();
		if (selectedItems.contains(name)) {
			selectedItems.remove(name);
			saveItems();
		}
	}

	public ArrayList<String> getSelectedItems() {
		selectedItems = loadItems();
		return selectedItems;
	}

}