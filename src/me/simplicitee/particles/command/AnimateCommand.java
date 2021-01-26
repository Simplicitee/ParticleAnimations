package me.simplicitee.particles.command;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.simplicitee.particles.Manager;
import me.simplicitee.particles.animation.Animation;
import me.simplicitee.particles.particle.ParticleEffect;
import net.md_5.bungee.api.ChatColor;

public class AnimateCommand implements SubCommand {

	@Override
	public String getLabel() {
		return "animate";
	}

	@Override
	public String getUsage() {
		return "/particle animate <particle> <animation> [target]";
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
		
		Optional<ParticleEffect> effect = ParticleEffect.of(args[0]);
		if (!effect.isPresent()) {
			return ChatColor.RED + "Unknown particle";
		}
		
		Optional<Animation> anim = Animation.of(args[1]);
		if (!anim.isPresent()) {
			return ChatColor.RED + "Unknown animation";
		}
		
		Manager.animateForPlayer(player, effect.get(), anim.get());
		return ChatColor.GREEN + "Started animation!";
	}

}
