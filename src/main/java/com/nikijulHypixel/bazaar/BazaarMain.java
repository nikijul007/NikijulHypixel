package com.nikijulHypixel.bazaar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.nikijulHypixel.NikijulHypixel;

import net.minecraft.util.ResourceLocation;

public class BazaarMain {
	public static final String BAZAAR_RESSOURCES_PATH = "src/main/resources/assets/nikijulhypixel/bazaar/";

	private static String key;

	public static void loadkey() {
		key = NikijulHypixel.configApiKey.getString("apikey", "ApiKey");
	}
	
	public static String getKey() {
		return key;
	}
}
