package me.simplicitee.photon.particle;

import org.bukkit.Particle;

public class RainbowDust extends ParticleEffect {

	RainbowDust() {
		super("RainbowDust", Particle.REDSTONE, new RainbowDustOptions());
	}
}
