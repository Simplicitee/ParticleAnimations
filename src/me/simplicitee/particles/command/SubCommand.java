package me.simplicitee.particles.command;

import org.bukkit.command.CommandSender;

public interface SubCommand {

	public String getLabel();
	public String getUsage();
	public String execute(CommandSender sender, String[] args);
}
