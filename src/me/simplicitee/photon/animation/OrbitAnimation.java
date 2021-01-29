package me.simplicitee.photon.animation;

import org.bukkit.Location;

import me.simplicitee.photon.PhotonPlugin;
import me.simplicitee.photon.util.Updateable;

public class OrbitAnimation extends ConfigurableAnimation {

	public OrbitAnimation() {
		super("Orbit");
	}

	@Override
	public void populate() {
		config.addDefault("Radius", 1.2);
		config.addDefault("AngleIncrement", 6);
		config.addDefault("YOffset", 0.92);
	}

	@Override
	public Animator instance(Updateable<Location> updater) {
		return new OrbitAnimator(updater, config.getDouble("Radius"), Math.toRadians(config.getDouble("AngleIncrement")), config.getDouble("YOffset"));
	}

	public static class OrbitAnimator extends Animator {
		
		private static double VECTOR_ANGLE = 2 * Math.PI / 3;
		
		private int step = 0;
		private double yOffset, radius, theta;
		private double[] angles = {0, 0, 0};

		public OrbitAnimator(Updateable<Location> updater, double radius, double theta, double yOffset) {
			super(updater);
			this.radius = radius;
			this.theta = theta;
			this.yOffset = yOffset;
		}

		@Override
		public Location update() {
			return updater.get().add(0, yOffset, 0)
					.add(Math.cos(angles[step]) * radius * Math.cos(step * VECTOR_ANGLE), 0, Math.cos(angles[step]) * radius * Math.sin(step * VECTOR_ANGLE))
					.add(Math.sin(angles[step]) * 0.6 * radius * Math.cos(step * VECTOR_ANGLE - Math.PI / 2), 0, Math.sin(angles[step]) * 0.6 * radius * Math.sin(step * VECTOR_ANGLE - Math.PI / 2));
		}

		@Override
		public void postUpdate() {
			PhotonPlugin.print(theta + " " + radius);
			PhotonPlugin.print(angles[0] + " " + angles[1] + " " + angles[2]);
			angles[step] += theta;
			step = (step + 1) % 3;
		}
		
	}
}
