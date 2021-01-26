package me.simplicitee.particles;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import me.simplicitee.particles.animation.Animation;
import me.simplicitee.particles.animation.Animator;
import me.simplicitee.particles.particle.ParticleEffect;
import me.simplicitee.particles.util.Updateable;

public final class Manager {

	private static final Map<Player, Map<ParticleEffect, Set<Animator>>> PLAYERS = new HashMap<>();
	
	private Manager() {}
	
	public static void animateForPlayer(Player player, ParticleEffect effect, Animation animation) {
		Validate.notNull(player, "Player cannot be null");
		Validate.notNull(effect, "Effect cannot be null");
		Validate.notNull(animation, "Animation cannot be null");
		
		if (!PLAYERS.containsKey(player)) {
			PLAYERS.put(player, new HashMap<>());
		} 
		
		if (!PLAYERS.get(player).containsKey(effect)) {
			PLAYERS.get(player).put(effect, new HashSet<>());
		}
		
		PLAYERS.get(player).get(effect).add(animation.instance(Updateable.locationOf(player)));
	}
	
	static void tick() {
		Iterator<Player> players = PLAYERS.keySet().iterator();
		while (players.hasNext()) {
			Iterator<Entry<ParticleEffect, Set<Animator>>> anims = PLAYERS.get(players.next()).entrySet().iterator();
			while (anims.hasNext()) {
				Entry<ParticleEffect, Set<Animator>> entry = anims.next();
				for (Animator animator : entry.getValue()) {
					entry.getKey().display(animator.update());
				}
			}
		}
	}
}
