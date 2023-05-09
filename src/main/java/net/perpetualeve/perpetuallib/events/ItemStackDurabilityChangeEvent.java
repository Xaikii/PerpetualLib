package net.perpetualeve.perpetuallib.events;

import javax.annotation.Nullable;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraftforge.eventbus.api.Event;

public class ItemStackDurabilityChangeEvent extends Event {
	
	int durabilityDamage;
	RandomSource worldRandom;
	@Nullable
	ServerPlayer serverPlayer;

	public ItemStackDurabilityChangeEvent(int durabilityDamage, RandomSource worldRandom, @Nullable ServerPlayer serverPlayer) {
		this.durabilityDamage = durabilityDamage;
		this.worldRandom = worldRandom;
		this.serverPlayer = serverPlayer;
	}

	public int getDurabilityDamage() {
		return durabilityDamage;
	}

	public void setDurabilityDamage(int durabilityDamage) {
		this.durabilityDamage = durabilityDamage;
	}

	public RandomSource getWorldRandom() {
		return worldRandom;
	}

	public @Nullable ServerPlayer getServerPlayer() {
		return serverPlayer;
	}

	/**
	 * An event that will be run BEFORE Durability Damage mitigation happens
	 *
	 */
	public static class Pre extends ItemStackDurabilityChangeEvent {

		public Pre(int durabilityDamage, RandomSource worldRandom,
				@Nullable ServerPlayer serverPlayer) {
			super(durabilityDamage, worldRandom, serverPlayer);
		}
	}

	/**
	 * An event that will be run AFTER Durability Damage mitigation happens<br>
	 * Also provides a value how much damage was actually mitigated
	 *
	 */
	public static class Post extends ItemStackDurabilityChangeEvent {

		int mitigated;

		public Post(int mitigate, int durabilityDamage, RandomSource worldRandom,
				@Nullable ServerPlayer serverPlayer) {
			super(durabilityDamage, worldRandom, serverPlayer);
			this.mitigated = mitigate;
		}

		public int getMitigated() {
			return mitigated;
		}
	}
}
