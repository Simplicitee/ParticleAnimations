package me.simplicitee.photon.particle.data;

import org.bukkit.Material;

public class BlockGenerator extends EffectDataGenerator {

	private final Parameter<?>[] params = {STRING};
	
	BlockGenerator() {}
	
	@Override
	public String getLabel() {
		return "block";
	}

	@Override
	public Parameter<?>[] getParameterTypes() {
		return params;
	}

	@Override
	protected Object evaluate(Object... inputs) {
		return Material.valueOf((String) inputs[0]).createBlockData();
	}

}
