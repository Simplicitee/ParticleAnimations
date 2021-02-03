package me.simplicitee.photon.animation;

import org.bukkit.Location;

import me.simplicitee.photon.util.Updateable;

public class OrbitAnimation extends ConfigurableAnimation {

	public OrbitAnimation() {
		super("Orbit");
	}

	@Override
	public void populate() {
		config.addDefault("Radius.Inner", 1);
		config.addDefault("Radius.Outer", 1.2);
		config.addDefault("AngleIncrement", 6);
		config.addDefault("YOffset", 0.92);
	}

	@Override
	public Animator instance(Updateable<Location> updater) {
		return new OrbitAnimator(updater, config.getDouble("Radius.Inner"), config.getDouble("Radius.Outer"), Math.toRadians(config.getDouble("AngleIncrement")), config.getDouble("YOffset"));
	}

	public static class OrbitAnimator extends Animator {
		
		private static double VECTOR_ANGLE = 2 * Math.PI / 3;
		
		private double yOffset, inner, outer, theta, angle = 0;

		public OrbitAnimator(Updateable<Location> updater, double inner, double outer, double theta, double yOffset) {
			super(updater);
			this.inner = inner;
			this.outer = outer;
			this.theta = theta;
			this.yOffset = yOffset;
		}

		@Override
		public void update() {
			for (int i = 0; i < 3; ++i) {
				addLocation(updater.get()
					.add(Math.cos(angle) * inner * Math.cos(i * VECTOR_ANGLE), yOffset, Math.cos(angle) * inner * Math.sin(i * VECTOR_ANGLE))
					.add(Math.sin(angle) * outer * Math.cos(i * VECTOR_ANGLE - Math.PI / 2), 0, Math.sin(angle) * outer * Math.sin(i * VECTOR_ANGLE - Math.PI / 2)));
			}
		}

		@Override
		public void postUpdate() {
			angle += theta;
		}
		
	}
}
