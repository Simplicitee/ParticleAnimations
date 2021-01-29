package me.simplicitee.photon.command;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;

import me.simplicitee.photon.PhotonPlugin;
import net.md_5.bungee.api.ChatColor;

public class ReloadCommand implements SubCommand {

	@Override
	public String getLabel() {
		return "reload";
	}

	@Override
	public String getArgTemplate() {
		return "";
	}

	@Override
	public String getHelpMessage() {
		return "Safely reload the Photon plugin, allowing any changes to files to take effect.";
	}

	@Override
	public String execute(CommandSender sender, String[] args) {
		PhotonPlugin.reload(sender);
		return ChatColor.RED + "Reloading...";
	}

	@Override
	public List<String> getTabComplete(CommandSender sender, String arg, int argCount) {
		return Collections.emptyList();
	}

}
