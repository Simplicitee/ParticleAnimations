package me.simplicitee.photon.particle.data;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemGenerator extends EffectDataGenerator {

	private final Parameter<?>[] params = {STRING};
	
	ItemGenerator() {}
	
	@Override
	public String getLabel() {
		return "item";
	}

	@Override
	public Parameter<?>[] getParameterTypes() {
		return params;
	}

	@Override
	protected Object evaluate(Object... inputs) {
		return new ItemStack(Material.valueOf((String) inputs[0]));
	}

}
