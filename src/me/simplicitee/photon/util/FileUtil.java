package me.simplicitee.photon.util;

import java.io.File;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

import org.bukkit.Particle;
import org.bukkit.util.Vector;

import me.simplicitee.photon.animation.VectorAnimation;
import me.simplicitee.photon.particle.ParticleEffect;
import me.simplicitee.photon.particle.data.EffectDataGenerator;

public final class FileUtil {

	private FileUtil() {}
	
	/**
	 * Reads an animation file and attempts to build a new {@link VectorAnimation} from it
	 * @param file animation file to read from
	 * @return Optional of the created {@link VectorAnimation}
	 * @throws IllegalArgumentException if file doesn't end in '.txt'
	 */
	public static Optional<VectorAnimation> readAnimation(File file) throws IllegalArgumentException {
		if (!file.getName().endsWith(".txt")) {
			throw new IllegalArgumentException("Error with '" + file.getName() + "', animation file must end with '.txt'");
		}
		
		VectorAnimation.Builder builder = new VectorAnimation.Builder();
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.startsWith("#")) {
					continue;
				}
				
				String[] split = line.split(",");
				builder.add(new Vector(Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2])));
			}
			scanner.close();
		} catch (Exception e) {
			if (scanner != null) {
				scanner.close();
			}
			e.printStackTrace();
			return Optional.empty();
		}
		
		return Optional.of(builder.build(file.getName().substring(0, file.getName().length() - 4)));
	}
	
	public static void readEffects(File file) throws IllegalArgumentException {
		if (!file.getName().endsWith(".txt")) {
			throw new IllegalArgumentException("Error with '" + file.getName() + "', effect file must end with '.txt'");
		}
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.startsWith("#")) {
					continue;
				}
				// name is 0, particle is 1, extra stuff is 2
				String[] split = line.split(" ", 3);
				if (split.length < 2 || split.length > 3) {
					continue;
				}
				
				Object data = null;
				if (split.length == 3) {
					data = EffectDataGenerator.parse(split[2]);
				}
				
				Particle particle;
				try {
					particle = Particle.valueOf(split[1].toUpperCase());
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				
				ParticleEffect.create(split[0], particle, data);
			}
			
			scanner.close();
		} catch (Exception e) {
			if (scanner != null) {
				scanner.close();
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * Applies {@link #readAnimation(File)} to all the files in the given folder,
	 * and passes them to the given consumer if created
	 * @param folder a folder containing animation files
	 * @param operation what to do with the created {@link VectorAnimation} objects
	 */
	public static void readAll(File folder, Consumer<VectorAnimation> operation) {
		for (File file : folder.listFiles()) {
			try {
				readAnimation(file).ifPresent(operation);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
