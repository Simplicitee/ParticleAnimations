package me.simplicitee.photon.animation;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;

import me.simplicitee.photon.util.Updateable;

public abstract class Animator {
	
	protected final Updateable<Location> updater;
	private Set<Location> locs;
	
	public Animator(Updateable<Location> updater) {
		this.updater = updater;
		this.locs = new HashSet<>();
	}
	
	public final Set<Location> getLocations() {
		return locs;
	}
	
	protected final void addLocation(Location loc) {
		locs.add(loc);
	}
	
	public abstract void update();
	public abstract void postUpdate();
}
