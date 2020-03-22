package com.nikijulHypixel.config;

import java.io.File;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

public class ConfigNikijulHypixel {

	public static Configuration config;
	public static String file = "config/NikijulHypixel.cfg";

	public static void init() {
		config = new Configuration(new File(file));
		try {
			config.load();
		} catch (Exception e) {
			System.out.println("Cannot load configuration file!");

		} finally {
			config.save();
		}

	}

	public static void removeConfig(String category) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (config.hasCategory(category))
				config.removeCategory(new ConfigCategory(category));
		} catch (Exception e) {
			System.out.println("Cannot load configuration file!");
		} finally {
			config.save();
		}
	}

	/*
	 * Removes specific key in specific category from configuration file.
	 */
	public static void removeConfig(String category, String name) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (config.getCategory(category).containsKey(name))
				config.getCategory(category).remove(name);
		} catch (Exception e) {
			System.out.println("Cannot load configuration file!");
		} finally {
			config.save();
		}
	}

	public static String getString(String category, String name) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (config.getCategory(category).containsKey(name)) {
				return config.get(category, name, "").getString();
			}
		} catch (Exception e) {
			System.out.println("Cannot load configuration file!");
		} finally {
			config.save();
		}
		return "";
	}

	public static void writeConfig(String category, String name, String value) {
		config = new Configuration(new File(file));
		try {
			config.load();
			String set = config.get(category, name, value).getString();
			config.getCategory(category).get(name).set(value);
		} catch (Exception e) {
			System.out.println("Cannot load configuration file!");
		} finally {
			config.save();
		}
	}


	public static boolean hasCategory(String category) {
		config = new Configuration(new File(file));
		try {
			config.load();
			return config.hasCategory(category);
		} catch (Exception e) {
			System.out.println("Cannot load configuration file!");
		} finally {
			config.save();
		}
		return false;
	}

	public static boolean hasKey(String category, String name) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (!config.hasCategory(category))
				return false;
			return config.getCategory(category).containsKey(name);
		} catch (Exception e) {
			System.out.println("Cannot load configuration file!");
		} finally {
			config.save();
		}
		return false;
	}

	public static void setFile(String filename) {
		file = "config/" + filename;
	}

	public static String getFile() {
		return file;
	}

}
