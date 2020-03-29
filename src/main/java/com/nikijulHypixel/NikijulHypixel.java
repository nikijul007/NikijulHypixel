package com.nikijulHypixel;

import org.lwjgl.input.Keyboard;

import com.nikijulHypixel.bazaar.ActivateItems;
import com.nikijulHypixel.bazaar.BazaarManager;
import com.nikijulHypixel.commands.CommandApiKey;
import com.nikijulHypixel.commands.CommandBazaarUpdateTimer;
import com.nikijulHypixel.config.ConfigNikijulHypixel;
import com.nikijulHypixel.utils.KeyHandler;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = NikijulHypixel.MODID)
public class NikijulHypixel {

	public static final String MODID = "nikijulhypixel";

	public static ConfigNikijulHypixel configApiKey = new ConfigNikijulHypixel();

	public static ConfigNikijulHypixel configItems = new ConfigNikijulHypixel();
	public static ConfigNikijulHypixel configItemsPrices = new ConfigNikijulHypixel();

	public static BazaarManager bazaarManager = new BazaarManager();

	public static ActivateItems activateItems = new ActivateItems();

	public static KeyBinding keyBindingCategory = new KeyBinding("keyBinding.openCategoryGui", Keyboard.KEY_P,
			"category.nikijul");
	public static KeyBinding keyBindingPrices = new KeyBinding("keyBinding.openPricesGui", Keyboard.KEY_O,
			"category.nikijul");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModMetadata data = event.getModMetadata();
		data.autogenerated = false;

		data.name = EnumChatFormatting.BLUE + "Nikijul Hypixel Bazaar";
		data.description = EnumChatFormatting.BLUE + "Show current item prices from any location";
		data.credits = EnumChatFormatting.RED + "Nikijul" + EnumChatFormatting.RESET + ", " + EnumChatFormatting.GRAY
				+ "Jan052000";
		data.authorList.add(EnumChatFormatting.RED + "nikijul");
		data.authorList.add(EnumChatFormatting.GRAY + "Jan052000");
		data.version = "1.0";
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		/* Make Config */

		configApiKey.setFile("ApiKey.cfg");
		configApiKey.init();

		configItems.setFile("ShowedItems.cfg");
		configItems.init();

		configItemsPrices.setFile("ItemsPrices.cfg");
		configItemsPrices.init();

		if (!configApiKey.hasCategory("apikey")) {
			configApiKey.writeConfig("apikey", "ApiKey", "YOUR KEY");
		}
		
		if(!configApiKey.hasCategory("bazaarupdatetime")) {
			configApiKey.writeConfig("bazaarupdatetime", "Time", "20");
		}

		/* Load ApiKey */
		bazaarManager.loadkey();

		/* Key-Binding */
		ClientRegistry.registerKeyBinding(keyBindingCategory);
		ClientRegistry.registerKeyBinding(keyBindingPrices);
		FMLCommonHandler.instance().bus().register(new KeyHandler());
		
		/* Register Commands */
		ClientCommandHandler.instance.registerCommand(new CommandApiKey());
		ClientCommandHandler.instance.registerCommand(new CommandBazaarUpdateTimer());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

}
