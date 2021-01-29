package me.simplicitee.photon.particle.data;

import org.bukkit.Color;
import org.bukkit.Particle.DustOptions;

public class DustGenerator extends EffectDataGenerator {

	private final Parameter<?>[] params = {INT, INT, INT, FLOAT};
	
	DustGenerator() {}
	
	@Override
	public String getLabel() {
		return "dust";
	}

	@Override
	public Parameter<?>[] getParameterTypes() {
		return params;
	}

	@Override
	protected Object evaluate(Object... inputs) {
		return new DustOptions(Color.fromRGB((int) inputs[0], (int) inputs[1], (int) inputs[2]), (float) inputs[3]);
	}

}
