package me.simplicitee.photon.particle.data;

import org.bukkit.util.Vector;

public class VectorGenerator extends EffectDataGenerator {

	private final Parameter<?>[] params = {DOUBLE, DOUBLE, DOUBLE};
	
	VectorGenerator() {}
	
	@Override
	public String getLabel() {
		return "vector";
	}

	@Override
	public Parameter<?>[] getParameterTypes() {
		return params;
	}

	@Override
	protected Object evaluate(Object... inputs) {
		return new Vector((double) inputs[0], (double) inputs[1], (double) inputs[2]);
	}

}
