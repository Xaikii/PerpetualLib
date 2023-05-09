package net.perpetualeve.perpetuallib.events;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Event;
import net.perpetualeve.perpetuallib.misc.interfaces.IMobEffectInstance;

public class MobEffectTickEvent extends Event {

	LivingEntity entity;
	int tickAmount;
	IMobEffectInstance meim;
	
	public MobEffectTickEvent(LivingEntity entity, int tickAmount, IMobEffectInstance meim) {
		this.entity = entity;
		this.tickAmount = tickAmount;
		this.meim = meim;
	}
	
	public LivingEntity getEntity() {
		return entity;
	}
	
	public int getTickAmount() {
		return tickAmount;
	}
	
	public void setTickAmount(int tickAmount) {
		this.tickAmount = tickAmount;
	}
	
	public IMobEffectInstance getMeim() {
		return meim;
	}
}
