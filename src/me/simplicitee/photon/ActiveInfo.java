package me.simplicitee.photon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;

import com.google.common.collect.ImmutableList;

import me.simplicitee.photon.animation.Animation;
import me.simplicitee.photon.animation.Animator;
import me.simplicitee.photon.particle.ParticleEffect;
import me.simplicitee.photon.util.Updateable;

public class ActiveInfo {
	
	public static class EffectAnimator {
		private ParticleEffect effect;
		private Animator animator;
		
		public EffectAnimator(ParticleEffect effect, Animator animator) {
			this.effect = effect;
			this.animator = animator;
		}
	}

	private Updateable<Location> updater;
	private Map<String, EffectAnimator> actives;
	private ImmutableList<String> names;
	
	public ActiveInfo(Updateable<Location> updater) {
		this.updater = updater;
		this.actives = new HashMap<>();
	}
	
	public void set(Animation animation, ParticleEffect effect) {
		Validate.notNull(animation);
		Validate.notNull(effect);
		
		if (actives.containsKey(animation.getName())) {
			actives.get(animation.getName()).effect = effect;
		} else {
			actives.put(animation.getName(), new EffectAnimator(effect, animation.instance(updater)));
		}
		
		updateNames();
	}
	
	public void remove(Animation animation) {
		actives.remove(animation.getName());
		updateNames();
	}
	
	public void clear() {
		actives.clear();
		updateNames();
	}
	
	public boolean isPresent(Animation animation) {
		return actives.containsKey(animation.getName());
	}
	
	public List<String> getAnimations() {
		return names;
	}
	
	private void updateNames() {
		names = ImmutableList.copyOf(actives.keySet());
	}
	
	boolean updateAll() {
		for (EffectAnimator anim : actives.values()) {
			anim.effect.display(anim.animator.update());
			anim.animator.postUpdate();
		}
		return actives.isEmpty();
	}
}
