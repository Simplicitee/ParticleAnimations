package me.simplicitee.photon.particle.data;

import org.bukkit.Color;

public class ColorGenerator extends EffectDataGenerator {

	public Parameter<?>[] params = {INT, INT, INT};
	
	@Override
	public String getLabel() {
		return "color";
	}

	@Override
	public Parameter<?>[] getParameterTypes() {
		return params;
	}

	@Override
	protected Object evaluate(Object... inputs) {
		return Color.fromRGB((int) inputs[0], (int) inputs[1], (int) inputs[2]);
	}

}
