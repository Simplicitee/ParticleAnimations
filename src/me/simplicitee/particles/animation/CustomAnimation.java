package me.simplicitee.particles.animation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import me.simplicitee.particles.util.Updateable;

public class CustomAnimation extends Animation {

	private List<Vector> vectors;
	
	CustomAnimation(String name, List<Vector> vectors) {
		super(name);
		this.vectors = new ArrayList<>(vectors);
	}

	@Override
	public Animator instance(Updateable<Location> updater) {
		return new CustomAnimator(updater, vectors);
	}

	public static class CustomAnimator extends Animator {
		
		private int step;
		private List<Vector> vectors;
		
		CustomAnimator(Updateable<Location> updater, List<Vector> vectors) {
			super(updater);
			this.step = 0;
			this.vectors = vectors;
		}

		@Override
		public Location update() {
			Location loc = updater.get();
			loc.add(vectors.get(step));
			step = (step + 1) % vectors.size();
			return loc;
		}
	}
	
	public static class Builder {
		
		private List<Vector> vectors;
		private Vector compound;
		
		public Builder() {
			this.vectors = new ArrayList<>();
			this.compound = new Vector();
		}
		
		public Builder add(Vector vector) {
			double x = vector.getX(), y = vector.getY(), z = vector.getZ();
			this.vectors.add(vector.add(compound));
			this.compound.setX(compound.getX() + x);
			this.compound.setY(compound.getY() + y);
			this.compound.setZ(compound.getZ() + z);
			return this;
		}
		
		public CustomAnimation build(String name) {
			return new CustomAnimation(name, vectors);
		}
	}
}
