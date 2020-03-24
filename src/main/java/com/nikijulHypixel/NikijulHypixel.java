package com.nikijulHypixel;

import org.lwjgl.input.Keyboard;

import com.nikijulHypixel.bazaar.ActivateItems;
import com.nikijulHypixel.bazaar.BazaarMain;
import com.nikijulHypixel.config.ConfigNikijulHypixel;
import com.nikijulHypixel.utils.Events;
import com.nikijulHypixel.utils.KeyHandler;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = NikijulHypixel.MODID)
public class NikijulHypixel {

	public static final String MODID = "nikijulhypixel";
	public static NikijulHypixel instance;
	
	public static ConfigNikijulHypixel configApiKey = new ConfigNikijulHypixel();
	
	public static ConfigNikijulHypixel configItems = new ConfigNikijulHypixel();
	public static ConfigNikijulHypixel configItemsPrices = new ConfigNikijulHypixel();
	
	
	public static BazaarMain bazaarMain = new BazaarMain();
	
	public static ActivateItems activateItems = new ActivateItems();
	
	public static KeyBinding keyBinding = new KeyBinding("keyBinding.openZealotGui", Keyboard.KEY_P,  "category.nikijul");
	
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		instance = this;
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
		
		if(!configApiKey.hasCategory("apikey")) {
			configApiKey.writeConfig("apikey", "ApiKey", "YOUR KEY");
		}
		
		
		/* Load ApiKey */
		bazaarMain.loadkey();

		
		/* Events */
		MinecraftForge.EVENT_BUS.register(new Events());
		
		/* Key-Binding */
		ClientRegistry.registerKeyBinding(keyBinding);
		FMLCommonHandler.instance().bus().register(new KeyHandler());
		
		
		
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

}
