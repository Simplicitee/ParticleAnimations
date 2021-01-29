package me.simplicitee.photon.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.simplicitee.photon.PhotonPlugin;
import net.md_5.bungee.api.ChatColor;

public class PhotonCommand implements CommandExecutor, TabCompleter {
	
	private static final Map<String, SubCommand> CMDS = new HashMap<>();
	private static String[] usages = new String[0];
	
	// register default commands
	static {
		registerSub(new AnimateCommand());
		registerSub(new ClearCommand());
		registerSub(new HelpCommand());
		registerSub(new ReloadCommand());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(PhotonPlugin.getMessagePrefix() + " Commands");
			sender.sendMessage(usages);
			return true;
		}
		
		SubCommand cmd = CMDS.get(args[0].toLowerCase());
		
		if (cmd != null && sender.hasPermission("photon.command." + cmd.getLabel().toLowerCase())) {
			sender.sendMessage(PhotonPlugin.getMessagePrefix() + cmd.execute(sender, Arrays.copyOfRange(args, 1, args.length)));
		} else {
			sender.sendMessage(PhotonPlugin.getMessagePrefix() + ChatColor.RED + "Unknown subcommand for /particle");
		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 0) {
			return null;
		}
		
		List<String> results = null;
		if (args.length == 1) {
			results = new ArrayList<>(CMDS.keySet());
		} else {
			if (CMDS.containsKey(args[0].toLowerCase()) && sender.hasPermission("photon.command." + args[0].toLowerCase())) {
				results = CMDS.get(args[0].toLowerCase()).getTabComplete(sender, args[args.length - 1], args.length - 1);
			}
		}
		
		List<String> tabComplete = new ArrayList<>();
		if (results != null && results.size() > 0) {
			for (String result : results) {
				if (result.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
					tabComplete.add(result);
				}
			}
		}
		
		return tabComplete;
	}
	
	public static boolean registerSub(SubCommand cmd) {
		if (CMDS.containsKey(cmd.getLabel().toLowerCase())) {
			return false;
		}
		
		List<String> current = new ArrayList<>(Arrays.asList(usages));
		current.add("/photon " + cmd.getLabel() + " " + cmd.getArgTemplate());
		usages = current.toArray(new String[0]);
		return CMDS.put(cmd.getLabel().toLowerCase(), cmd) == null;
	}
	
	public static SubCommand getSubCommand(String label) {
		return CMDS.get(label.toLowerCase());
	}
	
	public static List<String> getCommandLabels() {
		return new ArrayList<>(CMDS.keySet());
	}
}
