package net.perpetualeve.perpetuallib.misc.interfaces;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public interface IMobEffectInstance {
	
	boolean overwriteEffect(int amplifier);
	boolean overwriteEffect(int duration, int amplifier);
	boolean overwriteEffect(int duration, int amplifier, MobEffectInstance effect, LivingEntity ent, Entity en);

	public int getCreationDuration();
	public void setCreationDuration(int creationDuration);
	public int getAppliedDuration();
	public void setAppliedDuration(int appliedDuration);
	public int getExistDuration();
	public void setExistDuration(int existDuration);
}
