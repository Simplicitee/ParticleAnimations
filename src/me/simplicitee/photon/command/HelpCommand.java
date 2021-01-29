package me.simplicitee.photon.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class HelpCommand implements SubCommand {

	@Override
	public String getLabel() {
		return "help";
	}

	@Override
	public String getArgTemplate() {
		return "[topic]";
	}

	@Override
	public String getHelpMessage() {
		return "Get useful information about topics in the plugin such as commands.";
	}

	@Override
	public String execute(CommandSender sender, String[] args) {
		if (args.length > 1) {
			return ChatColor.RED + "too many args";
		}
		
		SubCommand cmd = PhotonCommand.getSubCommand(args[0]);
		if (cmd != null) {
			return "/photon " + cmd.getLabel() + " " + cmd.getArgTemplate() + "\n" + cmd.getHelpMessage();
		}
		
		return ChatColor.RED + "Unknown topic";
	}

	@Override
	public List<String> getTabComplete(CommandSender sender, String arg, int argCount) {
		if (argCount == 1) {
			List<String> results = new ArrayList<>();
			
			results.addAll(PhotonCommand.getCommandLabels());
			
			return results;
		}
		
		return null;
	}

}
