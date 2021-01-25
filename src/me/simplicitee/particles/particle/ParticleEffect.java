package me.simplicitee.particles.particle;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ParticleEffect {
	
	private static final Map<String, ParticleEffect> EFFECTS = new HashMap<>();
	private static final Map<Particle, Object> DEFAULTS = new HashMap<>();
	
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
	private Object data;
	private int amount = 1;
	private double offsetX = 0, offsetY = 0, offsetZ = 0, extra = 0;
	
	private ParticleEffect(String name, Particle particle, Object data) {
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
	
	public static boolean create(String name, Particle particle, Object data) {
		if (EFFECTS.containsKey(name.toLowerCase())) {
			return false;
		}
		
		EFFECTS.put(name.toLowerCase(), new ParticleEffect(name, particle, data));
		return true;
	}
}
