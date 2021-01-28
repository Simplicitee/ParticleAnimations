package me.simplicitee.photon.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

@FunctionalInterface
public interface Updateable<T> {

	public T get();
	
	public static Updateable<Location> locationOf(Player player) {
		return () -> player.getLocation();
	}
	
	public static Updateable<Location> locationOf(Player player, Vector offset) {
		return () -> player.getLocation().add(offset);
	}
	
	public static Updateable<Location> unchanging(Location loc) {
		return () -> loc.clone();
	}
}
