package com.nikijulHypixel.commands;

import com.nikijulHypixel.NikijulHypixel;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandApiKey extends CommandBase{

	@Override
	public String getCommandName() {
		return "apikey";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "apikey [APIKEY]";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if(sender instanceof EntityPlayer) {
			if(args.length == 1) {
				if(NikijulHypixel.configApiKey.hasCategory("apikey")) {
					NikijulHypixel.configApiKey.removeConfig("apikey");
				}
				
				NikijulHypixel.configApiKey.writeConfig("apikey", "ApiKey", args[0]);
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Set apikey successful!"));
				
			} else {
				sender.addChatMessage(new ChatComponentText("Please use /apikey [APIKEY]"));
			}
			
			
		} 
	}
	
	public boolean canCommandSenderUseCommand(final ICommandSender sender) {
		return true;
	}

}
