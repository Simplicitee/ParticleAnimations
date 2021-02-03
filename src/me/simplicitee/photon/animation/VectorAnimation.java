package me.simplicitee.photon.animation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import me.simplicitee.photon.util.Updateable;

public class VectorAnimation extends Animation {

	private List<Vector> vectors;
	
	VectorAnimation(String name, List<Vector> vectors) {
		super(name);
		this.vectors = new ArrayList<>(vectors);
	}

	@Override
	public Animator instance(Updateable<Location> updater) {
		return new VectorAnimator(updater, vectors);
	}

	public static class VectorAnimator extends Animator {
		
		private int step;
		private List<Vector> vectors;
		
		VectorAnimator(Updateable<Location> updater, List<Vector> vectors) {
			super(updater);
			this.step = 0;
			this.vectors = vectors;
		}

		@Override
		public void update() {
			addLocation(updater.get().add(vectors.get(step)));
		}
		
		@Override
		public void postUpdate() {
			step = (step + 1) % vectors.size();
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
		
		public VectorAnimation build(String name) {
			return new VectorAnimation(name, vectors);
		}
	}
}
