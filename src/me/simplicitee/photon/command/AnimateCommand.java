package me.simplicitee.photon.command;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.simplicitee.photon.ActiveInfo;
import me.simplicitee.photon.Manager;
import me.simplicitee.photon.animation.Animation;
import me.simplicitee.photon.particle.ParticleEffect;
import net.md_5.bungee.api.ChatColor;

public class AnimateCommand implements SubCommand {

	@Override
	public String getLabel() {
		return "animate";
	}

	@Override
	public String getArgTemplate() {
		return "<effect> <animation> [target]";
	}

	@Override
	public String execute(CommandSender sender, String[] args) {
		if (args.length > 3) {
			return ChatColor.RED + "too many args";
		}
		
		Player player = null;
		if (!(sender instanceof Player) && args.length != 2) {
			return ChatColor.RED + "Must specify target player";
		} else if (args.length == 3) {
			player = Bukkit.getPlayer(args[2]);
		} else {
			player = (Player) sender;
		}
		
		ParticleEffect effect = ParticleEffect.of(args[0]).orElse(null);
		if (effect == null) {
			return ChatColor.RED + "Unknown particle effect";
		} else if (!sender.hasPermission("photon.effect." + effect.getName())) {
			return ChatColor.RED + "You do not have permission to use that particle effect";
		}
		
		Animation anim = Animation.of(args[1]).orElse(null);
		if (anim == null) {
			return ChatColor.RED + "Unknown animation";
		} else if (!sender.hasPermission("photon.animation." + anim.getName())) {
			return ChatColor.RED + "You do not have permission to use that animation";
		}
		
		String msg = ChatColor.GREEN + "Started animation " + anim.getName() + " with effect " + effect.getName();
		ActiveInfo info = Manager.getInfo(player);
		if (info.isPresent(anim)) {
			msg = ChatColor.AQUA + "Animation " + anim.getName() + " effect replaced with " + effect.getName();
		}
		
		info.set(anim, effect);
		
		if (sender != player) {
			player.sendMessage(sender.getName() + " " + msg);
		}
		return msg;
	}

	@Override
	public List<String> getTabComplete(CommandSender sender, String arg, int argCount) {
		if (!sender.hasPermission("animations.use")) {
			return null;
		}
		
		if (argCount == 1) {
			return ParticleEffect.listNames();
		} else if (argCount == 2) {
			return Animation.listNames();
		} else if (argCount == 3) {
			return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
		}
		return null;
	}
}
