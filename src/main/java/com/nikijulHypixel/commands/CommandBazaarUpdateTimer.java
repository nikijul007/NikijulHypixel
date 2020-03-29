package com.nikijulHypixel.commands;

import com.nikijulHypixel.NikijulHypixel;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandBazaarUpdateTimer extends CommandBase{

	@Override
	public String getCommandName() {
		return "bazaartime";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "bazaartime [TIME TO NEXT UPDATE]";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if(sender instanceof EntityPlayer) {
			if(args.length == 1) {
				if(NikijulHypixel.configApiKey.hasCategory("bazaarupdatetime")) {
					NikijulHypixel.configApiKey.removeConfig("bazaarupdatetime");
				}
				
				NikijulHypixel.configApiKey.writeConfig("bazaarupdatetime", "Time", args[0]);
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Set bazaar update time successful!"));
				
			} else {
				sender.addChatMessage(new ChatComponentText("Please use /bazaartime [TIME TO NEXT UPDATE]"));
			}
			
			
		} 
	}
	
	public boolean canCommandSenderUseCommand(final ICommandSender sender) {
		return true;
	}
}
