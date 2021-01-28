package me.simplicitee.photon.animation;

import org.bukkit.configuration.ConfigurationSection;

import me.simplicitee.photon.PhotonPlugin;

public abstract class ConfigurableAnimation extends Animation {

	protected final ConfigurationSection config;
	
	public ConfigurableAnimation(String name) {
		super(name);
		this.config = PhotonPlugin.getAnimationConfigSection(this);
		this.populate();
		PhotonPlugin.saveAnimationsConfig();
	}

	public abstract void populate();
}
