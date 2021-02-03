package me.simplicitee.photon.particle.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class EffectDataGenerator {

	private static final Map<String, EffectDataGenerator> CACHE = new HashMap<>();
	
	public static final EffectDataGenerator NULL = new EffectDataGenerator() {

		@Override
		public String getLabel() {
			return "null";
		}

		@Override
		public Parameter<?>[] getParameterTypes() {
			return new Parameter<?>[0];
		}

		@Override
		protected Object evaluate(Object... inputs) {
			return null;
		}
		
	};
	
	public static interface Parameter<T> {
		public T apply(String value);
	}
	
	public static final Parameter<String> STRING = (s) -> s;
	public static final Parameter<Integer> INT = (s) -> Integer.parseInt(s);
	public static final Parameter<Double> DOUBLE = (s) -> Double.parseDouble(s);
	public static final Parameter<Float> FLOAT = (s) -> Float.parseFloat(s);
	
	public abstract String getLabel();
	public abstract Parameter<?>[] getParameterTypes();
	protected abstract Object evaluate(Object...inputs);
	
	public final Object evaluate(String params) {
		String[] split = params.split(",");
		if (split.length != getParameterTypes().length) {
			return null;
		}
		
		Object[] objs = new Object[split.length];
		try {
			for (int i = 0; i < split.length; ++i) {
				objs[i] = getParameterTypes()[i].apply(split[i]);
			}
		} catch (Exception e) {
			return null;
		}
		
		return evaluate(objs);
	}
	
	public static boolean register(EffectDataGenerator gen) {
		if (CACHE.containsKey(gen.getLabel().toLowerCase())) {
			return false;
		}
		
		CACHE.put(gen.getLabel().toLowerCase(), gen);
		return true;
	}
	
	public static Optional<EffectDataGenerator> get(String label) {
		return Optional.ofNullable(CACHE.get(label.toLowerCase()));
	}
	
	public static Object parse(String data) {
		String[] split = data.split(":");
		if (split.length != 2) {
			return null;
		}
		
		return get(split[0]).orElse(NULL).evaluate(split[1]);
	}
	
	public static void reload() {
		CACHE.clear();
		register(new ColorGenerator());
		register(new DustGenerator());
		register(new BlockGenerator());
		register(new ItemGenerator());
		register(new VectorGenerator());
		register(new NoteGenerator());
	}
}
