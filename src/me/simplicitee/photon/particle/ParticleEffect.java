package me.simplicitee.photon.particle;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.google.common.collect.ImmutableList;

import me.simplicitee.photon.PhotonPlugin;
import me.simplicitee.photon.util.FileUtil;

public class ParticleEffect {
	
	private static File effectsFile;
	private static final Map<String, ParticleEffect> EFFECTS = new HashMap<>();
	private static final Map<Particle, Object> DEFAULTS = new HashMap<>();
	private static ImmutableList<String> names;
	
	static {
		// spigot accounted for data
		DEFAULTS.put(Particle.REDSTONE, new DustOptions(Color.RED, 0.6f));
		DEFAULTS.put(Particle.BLOCK_CRACK, Material.BARRIER.createBlockData());
		DEFAULTS.put(Particle.BLOCK_DUST, Material.BARRIER.createBlockData());
		DEFAULTS.put(Particle.FALLING_DUST, Material.BARRIER.createBlockData());
		DEFAULTS.put(Particle.ITEM_CRACK, new ItemStack(Material.BARRIER));
		
		// special stuff
		DEFAULTS.put(Particle.SPELL_MOB, Color.WHITE);
		DEFAULTS.put(Particle.SPELL_MOB_AMBIENT, Color.WHITE);
		DEFAULTS.put(Particle.NOTE, NoteColor.GREEN);
		Vector zero = new Vector();
		for (Directional directional : Directional.values()) {
			DEFAULTS.put(directional.particle, zero);
		}
	}

	private String name;
	private Particle particle;
	protected Object data;
	protected int amount = 1;
	protected double offsetX = 0, offsetY = 0, offsetZ = 0, extra = 0;
	
	protected ParticleEffect(String name, Particle particle, Object data) {
		this.name = name;
		this.particle = particle;
		this.data = DEFAULTS.containsKey(particle) ? (DEFAULTS.get(particle).getClass().isInstance(data) ? data : DEFAULTS.get(particle)) : null;
		if (this.data != null) {
			if (this.data instanceof Color) {
				Color color = (Color) this.data;
				amount = 0;
				offsetX = Math.max(1.0, color.getRed()) / 255D;
				offsetY = color.getGreen() / 255D;
				offsetZ = color.getBlue() / 255D;
				extra = 1;
				this.data = null;
			} else if (this.data instanceof Vector) {
				Vector v = (Vector) this.data;
				amount = 0;
				offsetX = v.getX();
				offsetY = v.getY();
				offsetZ = v.getZ();
				extra = v.length();
				this.data = null;
			} else if (this.data instanceof NoteColor) {
				NoteColor c = (NoteColor) this.data;
				amount = 0;
				offsetX = c.ordinal() / 24D;
				extra = 1;
				this.data = null;
			}
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void display(Location loc) {
		loc.getWorld().spawnParticle(particle, loc, amount, offsetX, offsetY, offsetZ, extra, data);
	}
	
	public static Optional<ParticleEffect> of(String name) {
		return Optional.ofNullable(EFFECTS.get(name.toLowerCase()));
	}
	
	public static Optional<ParticleEffect> create(String name, Particle particle, Object data) {
		if (EFFECTS.containsKey(name.toLowerCase())) {
			return Optional.empty();
		}
		
		ParticleEffect effect = new ParticleEffect(name, particle, data);
		EFFECTS.put(name.toLowerCase(), effect);
		names = ImmutableList.copyOf(EFFECTS.keySet());
		return Optional.of(effect);
	}
	
	public static <T extends ParticleEffect> Optional<T> register(T effect) {
		if (EFFECTS.containsKey(effect.getName().toLowerCase())) {
			return Optional.empty();
		}
		
		EFFECTS.put(effect.getName().toLowerCase(), effect);
		names = ImmutableList.copyOf(EFFECTS.keySet());
		return Optional.of(effect);
	}
	
	public static List<String> listNames() {
		return names;
	}
	
	public static void reload() {
		EFFECTS.clear();
		names = null;
		
		effectsFile = new File(PhotonPlugin.getFolder(), "effects.txt");
		if (!effectsFile.exists()) {
			loadDefault();
		}
		
		register(new RainbowDust());
		register(new RainbowNote());
		FileUtil.readEffects(effectsFile);
	}
	
	private static void loadDefault() {
		try {
			effectsFile.createNewFile();
			PrintWriter writer = new PrintWriter(new FileWriter(effectsFile, true));
			for (String effect : getDefaultEffects()) {
				writer.println(effect);
			}
			
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static List<String> getDefaultEffects() {
		List<String> effects = new ArrayList<>();
		
		effects.add("RedDust REDSTONE dust:255,85,85,1");
		effects.add("BlueDust REDSTONE dust:85,85,255,1");
		effects.add("GreenDust REDSTONE dust:85,255,85,1");
		effects.add("YellowDust REDSTONE dust:255,255,0,1");
		effects.add("MagentaDust REDSTONE dust:255,85,255,1");
		effects.add("AquaDust REDSTONE dust:85,255,255,1");
		effects.add("OrangeDust REDSTONE dust:255,170,0,1");
		effects.add("BlackDust REDSTONE dust:0,0,0,1");
		effects.add("WhiteDust REDSTONE dust:255,255,255,1");
		effects.add("Flames FLAME");
		effects.add("BlueFlames SOUL_FIRE_FLAME");
		
		return effects;
	}
}
