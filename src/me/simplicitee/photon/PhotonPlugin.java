package me.simplicitee.photon;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.simplicitee.photon.animation.Animation;
import me.simplicitee.photon.animation.HelixAnimation;
import me.simplicitee.photon.animation.SpiralAnimation;
import me.simplicitee.photon.command.PhotonCommand;
import me.simplicitee.photon.particle.data.EffectDataGenerator;
import me.simplicitee.photon.util.Config;
import me.simplicitee.photon.util.FileUtil;
import net.md_5.bungee.api.ChatColor;

public class PhotonPlugin extends JavaPlugin {

	private static PhotonPlugin instance;
	private static File animationFolder, effectsFile;
	private static String messagePrefix;
	private static Config properties, animations;
	
	@Override
	public void onEnable() {
		instance = this;
		loadPropertiesDefaults();
		messagePrefix = ChatColor.translateAlternateColorCodes('&', properties.bukkit().getString("MessagePrefix").trim()) + " " + ChatColor.RESET;
		animations = new Config(new File(getDataFolder(), "animations.yml"));
		loadBuiltInAnimations();
		animationFolder = new File(getDataFolder(), "/animations/");
		if (!animationFolder.exists()) {
			animationFolder.mkdir();
		} else {
			FileUtil.readAll(animationFolder, (a) -> Animation.register(a));
		}
		
		EffectDataGenerator.init();
		
		effectsFile = new File(getDataFolder(), "effects.txt");
		if (!effectsFile.exists()) {
			loadEffectsDefault();
		}
		FileUtil.readEffects(effectsFile);
		
		registerCommand(getServer().getPluginCommand("photon"), new PhotonCommand());
		getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> Manager.tick(), 0, 1);
	}
	
	@Override
	public void onDisable() {
		Manager.clean();
	}
	
	private void registerCommand(PluginCommand cmd, PhotonCommand command) {
		cmd.setExecutor(command);
		cmd.setTabCompleter(command);
	}
	
	private void loadBuiltInAnimations() {
		Animation.register(new SpiralAnimation());
		Animation.register(new HelixAnimation());
	}
	
	private void loadPropertiesDefaults() {
		properties = new Config(new File(getDataFolder(), "properties.yml"));
		FileConfiguration config = properties.bukkit();
		
		config.addDefault("MessagePrefix", "&e&l[&b&lPhoton&e&l]");
		
		properties.save();
	}
	
	private List<String> getDefaultEffects() {
		List<String> effects = new ArrayList<>();
		
		effects.add("RedDust REDSTONE dust:255,85,85,1");
		effects.add("BlueDust REDSTONE dust:85,85,255,1");
		effects.add("GreenDust REDSTONE dust:85,255,85,1");
		effects.add("YellowDust REDSTONE dust:255,255,0,1");
		effects.add("MagentaDust REDSTONE dust:255,85,255,1");
		effects.add("AquaDust REDSTONE dust:85,255,255,1");
		effects.add("OrangeDust REDSTONE dust:255,170,0,1");
		effects.add("BlackDust REDSTONE dust:0,0,0,1");
		effects.add("WhiteDust REDSTONE dust:255,255,255,1");
		effects.add("Flames FLAME");
		effects.add("BlueFlames SOUL_FIRE_FLAME");
		
		return effects;
	}
	
	private void loadEffectsDefault() {
		try {
			effectsFile.createNewFile();
			PrintWriter writer = new PrintWriter(new FileWriter(effectsFile, true));
			for (String effect : getDefaultEffects()) {
				writer.println(effect);
			}
			
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the prefix for messages sent by this plugin, with
	 * a space after the prefix
	 * @return message prefix
	 */
	public static String getMessagePrefix() {
		return messagePrefix;
	}
	
	public static FileConfiguration getPropertiesConfig() {
		return properties.bukkit();
	}
	
	public static FileConfiguration getAnimationsConfig() {
		return animations.bukkit();
	}
	
	public static ConfigurationSection getAnimationConfigSection(Animation animation) {
		ConfigurationSection section = animations.bukkit().getConfigurationSection(animation.getName());
		
		if (section == null) {
			section = animations.bukkit().createSection(animation.getName());
		}
		
		return section;
	}
	
	public static void saveAnimationsConfig() {
		animations.save();
	}
	
	public static void print(String message) {
		instance.getLogger().info(message);
	}
}
