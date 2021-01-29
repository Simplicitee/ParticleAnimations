package me.simplicitee.photon.command;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.simplicitee.photon.ActiveInfo;
import me.simplicitee.photon.Manager;
import me.simplicitee.photon.PhotonPlugin;
import me.simplicitee.photon.animation.Animation;
import net.md_5.bungee.api.ChatColor;

public class ClearCommand implements SubCommand {

	@Override
	public String getLabel() {
		return "clear";
	}

	@Override
	public String getArgTemplate() {
		return "[animation] [player]";
	}
	
	@Override
	public String getHelpMessage() {
		return "Clear a specific animation or all animations for the target player, or yourself if not given.";
	}

	@Override
	public String execute(CommandSender sender, String[] args) {
		if (args.length > 2) {
			return ChatColor.RED + "too many args";
		}
		
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				return ChatColor.RED + "Console has no animations";
			}
			
			Manager.getInfo((Player) sender).clear();
			return ChatColor.AQUA + "Cleared all your active animations";
		} else if (args.length == 1) {
			Player player = Bukkit.getPlayer(args[0]);
			if (player == null && !(sender instanceof Player)) {
				return ChatColor.RED + "Console has no animations";
			} else if (player == null) {
				player = (Player) sender;
			}
			
			Animation anim = Animation.of(args[0]).orElse(null);
			if (anim == null && player == null) {
				return ChatColor.RED + "Unable to process given argument";
			} else if (anim == null || (player != null && player != sender)) {
				if (!sender.hasPermission("photon.command.others")) {
					return ChatColor.RED + "You do not have permission to use commands on other players";
				}
				player.sendMessage(PhotonPlugin.getMessagePrefix() + sender.getName() + " cleared all active animations for you");
				Manager.getInfo(player).clear();
				return ChatColor.AQUA + "Cleared all active animations for " + player.getName();
			} else {
				Manager.getInfo(player).remove(anim);
				return ChatColor.GREEN + "Cleared your " + anim.getName() + " animation";
			}
		} else {
			if (!sender.hasPermission("photon.command.others")) {
				return ChatColor.RED + "You do not have permission to use commands on other players";
			}
			
			Animation anim = Animation.of(args[0]).orElse(null);
			if (anim == null) {
				return ChatColor.RED + "Unknown animation";
			}
			
			Player player = Bukkit.getPlayer(args[1]);
			if (player == null) {
				return ChatColor.RED + "Unknown player";
			}
			
			ActiveInfo info = Manager.getInfo(player);
			if (!info.isPresent(anim)) {
				return ChatColor.WHITE + player.getName() + ChatColor.RED + " does not have that animation active";
			}
			
			player.sendMessage(PhotonPlugin.getMessagePrefix() + sender.getName() + ChatColor.GREEN + " has cleared your " + anim.getName() + " animation");
			return ChatColor.GREEN + "You have cleared " + player.getName() + "'s " + anim.getName() + " animation";
		}
	}

	@Override
	public List<String> getTabComplete(CommandSender sender, String arg, int argCount) {
		if (argCount == 1) {
			return Animation.listNames().stream().filter((s) -> sender.hasPermission("photon.animation." + s)).collect(Collectors.toList());
		} else if (argCount == 2) {
			return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
		}
		
		return null;
	}

}
