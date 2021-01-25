package me.simplicitee.particles.animation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;

import me.simplicitee.particles.util.Updateable;

public abstract class Animation {
	
	private static final Map<String, Animation> ANIMATIONS = new HashMap<>();
	
	private String name;
	
	public Animation(String name) {
		this.name = name.toLowerCase();
	}
	
	public final String getName() {
		return name;
	}
	
	public abstract Animator instance(Updateable<Location> updater);
	
	public static void register(Animation animation) {
		Validate.notNull(animation, "Registration error: null animation");
		Validate.notNull(animation.getName(), "Registration error: null name");
		Validate.isTrue(!ANIMATIONS.containsKey(animation.name), "Registration error: duplicate name");
		ANIMATIONS.put(animation.name, animation);
	}
	
	public static Optional<Animation> of(String name) {
		return Optional.ofNullable(ANIMATIONS.get(name.toLowerCase()));
	}
	
	public static <T extends Animation> Optional<T> of(String name, Class<T> clazz) {
		if (!ANIMATIONS.containsKey(name.toLowerCase())) {
			return Optional.empty();
		}
		
		Animation anim = ANIMATIONS.get(name.toLowerCase());
		if (clazz.isInstance(anim)) {
			return Optional.of(clazz.cast(anim));
		}
		
		return Optional.empty();
	}
}
