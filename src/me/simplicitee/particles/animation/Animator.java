package me.simplicitee.particles.animation;

import org.bukkit.Location;

import me.simplicitee.particles.util.Updateable;

public abstract class Animator {
	
	protected final Updateable<Location> updater;
	
	public Animator(Updateable<Location> updater) {
		this.updater = updater;
	}
	
	public abstract Location update();
}
