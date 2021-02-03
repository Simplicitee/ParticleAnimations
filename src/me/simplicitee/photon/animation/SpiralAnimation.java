package me.simplicitee.photon.animation;

import org.bukkit.Location;

import me.simplicitee.photon.util.Updateable;

public class SpiralAnimation extends ConfigurableAnimation {
	
	public SpiralAnimation() {
		super("Spiral");
	}

	@Override
	public void populate() {
		config.addDefault("Radius", 1);
		config.addDefault("Height", 1.84);
		config.addDefault("AngleIncrement", 6);
		config.addDefault("VerticalSpeed", 2);
	}
	
	@Override
	public Animator instance(Updateable<Location> updater) {
		return new SpiralAnimator(updater, Math.toRadians(config.getDouble("AngleIncrement")), Math.toRadians(config.getDouble("VerticalSpeed")), config.getDouble("Radius"), config.getDouble("Height"));
	}

	public static class SpiralAnimator extends Animator {

		private double angle, vertical, theta, phi, radius, height;
		
		public SpiralAnimator(Updateable<Location> updater, double theta, double phi, double radius, double height) {
			super(updater);
			this.angle = 0;
			this.vertical = -Math.PI / 2;
			this.theta = theta;
			this.phi = phi;
			this.radius = radius;
			this.height = height;
		}

		@Override
		public void update() {
			addLocation(updater.get().add(radius * Math.cos(angle), height/2 * (1 + Math.sin(vertical)), radius * Math.sin(angle)));
		}
		
		@Override
		public void postUpdate() {
			angle += theta;
			vertical += phi;
		}
	}
}
