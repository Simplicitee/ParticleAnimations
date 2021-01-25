package me.simplicitee.particles;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.entity.Player;

import me.simplicitee.particles.animation.Animator;
import me.simplicitee.particles.particle.ParticleEffect;

public final class Manager {

	private static final Map<Player, Map<ParticleEffect, Set<Animator>>> PLAYERS = new HashMap<>();
	
	private Manager() {}
	
	
	
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
