package me.simplicitee.photon.command;

import java.util.List;

import org.bukkit.command.CommandSender;

public interface SubCommand {

	public String getLabel();
	public String getArgTemplate();
	public String getHelpMessage();
	public String execute(CommandSender sender, String[] args);
	public List<String> getTabComplete(CommandSender sender, String arg, int argCount);
}
