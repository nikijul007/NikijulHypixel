package com.nikijulHypixel.config;

import java.io.File;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

public class ConfigApiKey {

	public static Configuration configApiKey;
	public static String fileApiKey = "config/ApiKey.cfg";

	public static void init() {
		configApiKey = new Configuration(new File(fileApiKey));
		try {
			configApiKey.load();
		} catch (Exception e) {
			System.out.println("Cannot load configuration file!");

		} finally {
			configApiKey.save();
		}

	}

	public static void removeConfig(String category) {
		configApiKey = new Configuration(new File(fileApiKey));
		try {
			configApiKey.load();
			if (configApiKey.hasCategory(category))
				configApiKey.removeCategory(new ConfigCategory(category));
		} catch (Exception e) {
			System.out.println("Cannot load configuration file!");
		} finally {
			configApiKey.save();
		}
	}

	/*
	 * Removes specific key in specific category from configuration file.
	 */
	public static void removeConfig(String category, String key) {
		configApiKey = new Configuration(new File(fileApiKey));
		try {
			configApiKey.load();
			if (configApiKey.getCategory(category).containsKey(key))
				configApiKey.getCategory(category).remove(key);
		} catch (Exception e) {
			System.out.println("Cannot load configuration file!");
		} finally {
			configApiKey.save();
		}
	}

	public static String getString(String category, String key) {
		configApiKey = new Configuration(new File(fileApiKey));
		try {
			configApiKey.load();
			if (configApiKey.getCategory(category).containsKey(key)) {
				return configApiKey.get(category, key, "").getString();
			}
		} catch (Exception e) {
			System.out.println("Cannot load configuration file!");
		} finally {
			configApiKey.save();
		}
		return "";
	}

	public static void writeConfig(String category, String key, String value) {
		configApiKey = new Configuration(new File(fileApiKey));
		try {
			configApiKey.load();
			String set = configApiKey.get(category, key, value).getString();
			configApiKey.getCategory(category).get(key).set(value);
		} catch (Exception e) {
			System.out.println("Cannot load configuration file!");
		} finally {
			configApiKey.save();
		}
	}


	public static boolean hasCategory(String category) {
		configApiKey = new Configuration(new File(fileApiKey));
		try {
			configApiKey.load();
			return configApiKey.hasCategory(category);
		} catch (Exception e) {
			System.out.println("Cannot load configuration file!");
		} finally {
			configApiKey.save();
		}
		return false;
	}

	public static boolean hasKey(String category, String key) {
		configApiKey = new Configuration(new File(fileApiKey));
		try {
			configApiKey.load();
			if (!configApiKey.hasCategory(category))
				return false;
			return configApiKey.getCategory(category).containsKey(key);
		} catch (Exception e) {
			System.out.println("Cannot load configuration file!");
		} finally {
			configApiKey.save();
		}
		return false;
	}

	public static void setFile(String filename) {
		fileApiKey = "config/" + filename;
	}

	public static String getFile() {
		return fileApiKey;
	}

}
