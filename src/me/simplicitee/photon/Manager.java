package me.simplicitee.photon;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import me.simplicitee.photon.util.Updateable;

public final class Manager {

	private static final Map<Player, ActiveInfo> PLAYERS = new HashMap<>();
	
	private Manager() {}
	
	/**
	 * Gets the cached {@link ActiveInfo} for the player, or creates
	 * and caches a new instance for the player and then returns it 
	 * if one isn't cached.
	 * @param player {@link Player} param
	 * @return non-null ActiveInfo
	 */
	public static ActiveInfo getInfo(Player player) {
		Validate.notNull(player, "Player cannot be null");
		
		ActiveInfo info = PLAYERS.get(player);
		if (info == null) {
			info = new ActiveInfo(Updateable.locationOf(player));
			PLAYERS.put(player, info);
		}
		
		return info;
	}
	
	static void tick() {
		Iterator<Entry<Player, ActiveInfo>> entries = PLAYERS.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<Player, ActiveInfo> entry = entries.next();
			if (!entry.getKey().isOnline()) {
				entries.remove();
				continue;
			} else if (entry.getKey().isDead()) {
				continue;
			}
			
			if (entry.getValue().updateAll()) {
				entries.remove();
			}
		}
	}
	
	static void clean() {
		PLAYERS.clear();
	}
}
