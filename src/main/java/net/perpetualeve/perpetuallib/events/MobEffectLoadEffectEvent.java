package net.perpetualeve.perpetuallib.events;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.eventbus.api.Event;
import net.perpetualeve.perpetuallib.misc.interfaces.IMobEffectInstance;

public class MobEffectLoadEffectEvent extends Event {

	IMobEffectInstance meim;
	CompoundTag tag;

	public MobEffectLoadEffectEvent(IMobEffectInstance meim, CompoundTag tag) {
		super();
		this.meim = meim;
		this.tag = tag;
	}

	public IMobEffectInstance getMeim() {
		return meim;
	}
	
	public IMobEffectInstance setMeim(IMobEffectInstance meim) {
		return (this.meim = meim);
	}

	public CompoundTag getTag() {
		return tag;
	}

	public CompoundTag setTag(CompoundTag tag) {
		return (this.tag = tag);
	}
}
