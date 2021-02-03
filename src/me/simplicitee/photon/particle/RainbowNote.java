package me.simplicitee.photon.particle;

import org.bukkit.Location;
import org.bukkit.Particle;

public class RainbowNote extends ParticleEffect {

	private static int counter = 0;
	
	protected RainbowNote() {
		super("RainbowNote", Particle.NOTE, null);
		amount = 0;
		extra = 1;
	}

	@Override
	public void display(Location loc) {
		offsetX = counter / 24D;
		super.display(loc);
	}
	
	public static void update() {
		counter = (counter + 1) % 25;
	}
}
