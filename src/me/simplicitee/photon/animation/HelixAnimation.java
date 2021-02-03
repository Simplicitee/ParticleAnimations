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
		config.addDefault("AngleIncrement", 30);
		config.addDefault("YStepIncrement", 0.1);
	}

	@Override
	public Animator instance(Updateable<Location> updater) {
		return new HelixAnimator(updater, Math.toRadians(config.getDouble("AngleIncrement")), config.getDouble("Radius"), config.getDouble("Height"), config.getDouble("YStepIncrement"));
	}

	public static class HelixAnimator extends Animator {
		
		private double angle = 0, theta, radius, step = 0, stepDelta, height;

		public HelixAnimator(Updateable<Location> updater, double theta, double radius, double height, double stepDelta) {
			super(updater);
			this.theta = theta;
			this.radius = radius;
			this.height = height;
			this.stepDelta = stepDelta;
		}

		@Override
		public void update() {
			for (int i = 0; i < 2; ++i) {
				addLocation(updater.get().add(radius * Math.cos(angle + i * Math.PI), step, radius * Math.sin(angle + i * Math.PI)));
			}
		}

		@Override
		public void postUpdate() {
			angle += theta;
			step = (step + stepDelta) % height;
		}
		
	}
}
