package me.simplicitee.particles.particle;

import org.bukkit.Particle;

public enum Directional {
	EXPLOSION_NORMAL (Particle.EXPLOSION_NORMAL),
	FIREWORKS_SPARK (Particle.FIREWORKS_SPARK),
	WATER_BUBBLE (Particle.WATER_BUBBLE),
	WATER_WAKE (Particle.WATER_WAKE),
	CRIT (Particle.CRIT),
	CRIT_MAGIC (Particle.CRIT_MAGIC),
	SMOKE_NORMAL (Particle.SMOKE_NORMAL),
	SMOKE_LARGE (Particle.SMOKE_LARGE),
	PORTAL (Particle.PORTAL),
	ENCHANTMENT_TABLE (Particle.ENCHANTMENT_TABLE),
	FLAME (Particle.FLAME),
	CLOUD (Particle.CLOUD),
	DRAGON_BREATH (Particle.DRAGON_BREATH),
	END_ROD (Particle.END_ROD),
	DAMAGE_INDICATOR (Particle.DAMAGE_INDICATOR),
	TOTEM (Particle.TOTEM),
	SPIT (Particle.SPIT),
	SQUID_INK (Particle.SQUID_INK),
	BUBBLE_POP (Particle.BUBBLE_POP),
	BUBBLE_COLUMN_UP (Particle.BUBBLE_COLUMN_UP),
	NAUTILUS (Particle.NAUTILUS),
	CAMPFIRE_COSY_SMOKE (Particle.CAMPFIRE_COSY_SMOKE),
	CAMPFIRE_SIGNAL_SMOKE (Particle.CAMPFIRE_SIGNAL_SMOKE);
	
	public final Particle particle;
	
	private Directional(Particle particle) {
		this.particle = particle;
	}
}
