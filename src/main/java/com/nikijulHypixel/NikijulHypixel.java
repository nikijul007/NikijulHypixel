package com.nikijulHypixel;

import org.lwjgl.input.Keyboard;

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
	
	public static KeyBinding keyBinding = new KeyBinding("keyBinding.openZealotGui", Keyboard.KEY_P,  "category.nikijul");
	
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		instance = this;
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		
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
