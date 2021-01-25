package me.simplicitee.particles.util;

import java.io.File;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

import org.bukkit.util.Vector;

import me.simplicitee.particles.animation.CustomAnimation;

public final class FileUtil {

	private FileUtil() {}
	
	/**
	 * Reads an animation file and attempts to build a new {@link CustomAnimation} from it
	 * @param file animation file to read from
	 * @return Optional of the created {@link CustomAnimation}
	 * @throws IllegalArgumentException if file doesn't end in '.anim'
	 */
	public static Optional<CustomAnimation> read(File file) throws IllegalArgumentException {
		if (file.getName().endsWith(".anim")) {
			throw new IllegalArgumentException("Error with '" + file.getName() + "', animation file must end with '.anim'");
		}
		
		CustomAnimation.Builder builder = new CustomAnimation.Builder();
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
		
		return Optional.of(builder.build(file.getName().substring(0, file.getName().length() - 5)));
	}
	
	/**
	 * Applies {@link #read(File)} to all the files in the given folder,
	 * and passes them to the given consumer if created
	 * @param folder a folder containing animation files
	 * @param operation what to do with the created {@link CustomAnimation} objects
	 */
	public static void readAll(File folder, Consumer<CustomAnimation> operation) {
		for (File file : folder.listFiles()) {
			try {
				read(file).ifPresent(operation);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
