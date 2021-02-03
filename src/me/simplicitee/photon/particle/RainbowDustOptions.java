package me.simplicitee.photon.particle;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Color;
import org.bukkit.Particle.DustOptions;

public class RainbowDustOptions extends DustOptions {

	private static int r = 255, g = 0, b = 0, parts = 20, counter = 0;
	private static Queue<Color> queue = new LinkedList<>();
	private static Color current = Color.RED;
	
	static {
		queue.add(Color.ORANGE);
		queue.add(Color.YELLOW);
		queue.add(Color.LIME);
		queue.add(Color.AQUA);
		queue.add(Color.BLUE);
		queue.add(Color.fromRGB(75, 0, 130));
		queue.add(Color.fromRGB(143, 0, 255));
		queue.add(Color.RED);
	}
	
	public RainbowDustOptions() {
		super(Color.WHITE, 1);
	}

	@Override
	public Color getColor() {
		return Color.fromRGB(r, g, b);
	}
	
	public static void update() {
		Color next = queue.peek();
		
		r += (next.getRed() - current.getRed()) / parts;
		r = Math.min(Math.max(1, r), 255);
		g += (next.getGreen() - current.getGreen()) / parts;
		g = Math.min(Math.max(0, g), 255);
		b += (next.getBlue() - current.getBlue()) / parts;
		b = Math.min(Math.max(0, b), 255);
		
		counter = (counter + 1) % parts;
		if (counter == 0) {
			current = queue.poll();
			queue.add(current);
			r = current.getRed();
			g = current.getGreen();
			b = current.getBlue();
		}
	}
}
