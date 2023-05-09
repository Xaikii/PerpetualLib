package net.perpetualeve.perpetuallib.handler;

import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandler {

	public static final EventHandler INSTANCE = new EventHandler();
	
	@SubscribeEvent
	public void effectTick(MobEffectEvent.Added e) {
	}
}
