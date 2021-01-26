package me.simplicitee.particles;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import me.simplicitee.particles.animation.Animation;
import me.simplicitee.particles.util.FileUtil;

public class ParticlePlugin extends JavaPlugin {

	public File animationFolder;
	
	@Override
	public void onEnable() {
		
		animationFolder = new File(getDataFolder(), "/animations/");
		if (!animationFolder.exists()) {
			animationFolder.mkdir();
		} else {
			FileUtil.readAll(animationFolder, (a) -> Animation.register(a));
		}
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> Manager.tick(), 0, 1);
	}
	
	@Override
	public void onDisable() {
		
	}
}
