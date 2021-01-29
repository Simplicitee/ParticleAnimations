package me.simplicitee.photon.particle.data;

import me.simplicitee.photon.particle.NoteColor;

public class NoteGenerator extends EffectDataGenerator {

	private final Parameter<?>[] params = {STRING};
	
	NoteGenerator() {}
	
	@Override
	public String getLabel() {
		return "note";
	}

	@Override
	public Parameter<?>[] getParameterTypes() {
		return params;
	}

	@Override
	protected Object evaluate(Object... inputs) {
		return NoteColor.valueOf((String) inputs[0]);
	}

}
