package me.simplicitee.photon.animation;

import org.bukkit.Location;

import me.simplicitee.photon.util.Updateable;

public class HelixAnimation extends ConfigurableAnimation {

	public HelixAnimation() {
		super("Helix");
	}

	@Override
	public void populate() {
		config.addDefault("Radius", 0.7);
		config.addDefault("Height", 1.84);
		config.addDefault("AngleIncrement", 2);
		config.addDefault("YStepIncrement", 0.1);
	}

	@Override
	public Animator instance(Updateable<Location> updater) {
		return new HelixAnimator(updater, Math.toRadians(config.getDouble("AngleIncrement")), config.getDouble("Radius"), config.getDouble("Height"), config.getDouble("YStepIncrement"));
	}

	public static class HelixAnimator extends Animator {
		
		private double angle = 0, theta, radius, step = 0, stepDelta, height;
		private boolean side = false;

		public HelixAnimator(Updateable<Location> updater, double theta, double radius, double height, double stepDelta) {
			super(updater);
			this.theta = theta;
			this.radius = radius;
			this.height = height;
			this.stepDelta = stepDelta;
		}

		@Override
		public Location update() {
			return updater.get().add(radius * Math.cos(angle), step, radius * Math.sin(angle));
		}

		@Override
		public void postUpdate() {
			if (side) {
				step += stepDelta;
				step %= height;
				angle += Math.PI;
				angle += theta;
			} else {
				angle -= Math.PI;
			}
			side = !side;
		}
		
	}
}
