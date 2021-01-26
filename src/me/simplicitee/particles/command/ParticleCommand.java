package me.simplicitee.particles.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class ParticleCommand implements CommandExecutor {
	
	private static final Map<String, SubCommand> CMDS = new HashMap<>();
	private static String[] usages = new String[0];
	
	// register default commands
	static {
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(usages);
			return true;
		}
		
		SubCommand cmd = CMDS.get(args[0].toLowerCase());
		
		if (cmd != null) {
			sender.sendMessage(cmd.execute(sender, Arrays.copyOfRange(args, 1, args.length)));
		} else {
			sender.sendMessage(ChatColor.RED + "Unknown subcommand for /particle");
		}
		
		return true;
	}

	public static boolean registerSub(SubCommand cmd) {
		if (CMDS.containsKey(cmd.getLabel().toLowerCase())) {
			return false;
		}
		
		List<String> current = Arrays.asList(usages);
		current.add(cmd.getUsage());
		usages = current.toArray(new String[0]);
		return CMDS.put(cmd.getLabel().toLowerCase(), cmd) == null;
	}
}
