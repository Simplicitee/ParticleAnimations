package me.simplicitee.photon.animation;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;

import com.google.common.collect.ImmutableList;

import me.simplicitee.photon.PhotonPlugin;
import me.simplicitee.photon.util.FileUtil;
import me.simplicitee.photon.util.Updateable;

public abstract class Animation {
	
	private static File animationFolder;
	private static final Map<String, Animation> ANIMATIONS = new HashMap<>();
	private static ImmutableList<String> names;
	
	private String name;
	
	public Animation(String name) {
		this.name = name;
	}
	
	public final String getName() {
		return name;
	}
	
	public abstract Animator instance(Updateable<Location> updater);
	
	public static void register(Animation animation) {
		Validate.notNull(animation, "Registration error: null animation");
		Validate.notNull(animation.getName(), "Registration error: null name");
		Validate.isTrue(!ANIMATIONS.containsKey(animation.name.toLowerCase()), "Registration error: duplicate name");
		ANIMATIONS.put(animation.name.toLowerCase(), animation);
		names = ImmutableList.copyOf(ANIMATIONS.keySet());
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
	
	public static List<String> listNames() {
		return names;
	}
	
	public static void reload() {
		ANIMATIONS.clear();
		names = null;
		
		register(new SpiralAnimation());
		register(new HelixAnimation());
		register(new OrbitAnimation());
		
		animationFolder = new File(PhotonPlugin.getFolder(), "/animations/");
		if (!animationFolder.exists()) {
			animationFolder.mkdir();
		} else {
			FileUtil.readAll(animationFolder, (a) -> Animation.register(a));
		}
	}
}
