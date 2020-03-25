package com.nikijulHypixel.config;

import java.io.File;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

public class ConfigNikijulHypixel {

	private Configuration config;
	private String file;

	public void init() {
		config = new Configuration(new File(file));
		try {
			config.load();
		} catch (Exception e) {
			System.out.println("Cannot load configuration file!");

		} finally {
			config.save();
		}

	}

	public void removeConfig(String category) {
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
	public void removeConfig(String category, String name) {
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

	public String getString(String category, String name) {
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
	
	public double getDouble(String category, String key) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (config.getCategory(category).containsKey(key)) {
				return config.get(category, key, 0D).getDouble();
			}
		} catch (Exception e) {
			System.out.println("Cannot load configuration file!");
		} finally {
			config.save();
		}
		return 0D;
	}

	//String
	public void writeConfig(String category, String name, String value) {
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
	
	//double
	public void writeConfig(String category, String key, double value) {
		config = new Configuration(new File(file));
		try {
			config.load();
			double set = config.get(category, key, value).getDouble();
			config.getCategory(category).get(key).set(value);
		} catch (Exception e) {
			System.out.println("Cannot load configuration file!");
		} finally {
			config.save();
		}
	}


	public boolean hasCategory(String category) {
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

	public boolean hasKey(String category, String name) {
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

	public void setFile(String filename) {
		file = "config/" + filename;
	}

	public String getFile() {
		return file;
	}

}
