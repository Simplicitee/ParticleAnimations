package me.simplicitee.photon;

import java.io.File;

import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.simplicitee.photon.animation.Animation;
import me.simplicitee.photon.command.PhotonCommand;
import me.simplicitee.photon.particle.ParticleEffect;
import me.simplicitee.photon.particle.RainbowDustOptions;
import me.simplicitee.photon.particle.data.EffectDataGenerator;
import me.simplicitee.photon.util.Config;
import net.md_5.bungee.api.ChatColor;

public class PhotonPlugin extends JavaPlugin {

	private static PhotonPlugin instance;
	private static String messagePrefix;
	private static Config properties, animations;
	
	@Override
	public void onEnable() {
		instance = this;
		loadPropertiesDefaults();
		messagePrefix = ChatColor.translateAlternateColorCodes('&', properties.bukkit().getString("MessagePrefix").trim()) + " " + ChatColor.RESET;
		animations = new Config(new File(getDataFolder(), "animations.yml"));
		
		Animation.reload();
		EffectDataGenerator.reload();
		ParticleEffect.reload();
		
		registerCommand(getServer().getPluginCommand("photon"), new PhotonCommand());
		getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> Manager.tick(), 0, 1);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> RainbowDustOptions.update(), 0, 1);
	}
	
	@Override
	public void onDisable() {
		Manager.clean();
	}
	
	private void registerCommand(PluginCommand cmd, PhotonCommand command) {
		cmd.setExecutor(command);
		cmd.setTabCompleter(command);
	}
	
	private void loadPropertiesDefaults() {
		properties = new Config(new File(getDataFolder(), "properties.yml"));
		FileConfiguration config = properties.bukkit();
		
		config.addDefault("MessagePrefix", "&e&l[&b&lPhoton&e&l]");
		
		properties.save();
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
	
	public static File getFolder() {
		return instance.getDataFolder();
	}
	
	public static void reload(CommandSender sender) {
		instance.getServer().getScheduler().scheduleSyncDelayedTask(instance, () -> {
			// rload configs
			properties.reload();
			animations.reload();
			messagePrefix = ChatColor.translateAlternateColorCodes('&', properties.bukkit().getString("MessagePrefix").trim()) + " " + ChatColor.RESET;
			
			// clean out the active animations
			Manager.clean();
			Animation.reload();
			EffectDataGenerator.reload();
			ParticleEffect.reload();
			
			sender.sendMessage(messagePrefix + "Reload complete!");
		});
	}
}
