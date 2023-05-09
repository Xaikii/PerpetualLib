package net.perpetualeve.perpetuallib.events;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

public class ItemStackDurabilityLossEvent extends Event {

	float value;
	int unbreakingLevel;
	ItemStack stack;
	RandomSource rand;

	/**
	 * An event called when Unbreaking stuff is happening.<br>
	 * Can also be triggered manually!!!<br>
	 * If you want to guarantee that it procs (Durability is NOT used), cancel this event!
	 * @param value
	 * @param unbreakingLevel
	 * @param stack
	 */
	public ItemStackDurabilityLossEvent(float value, int unbreakingLevel, ItemStack stack, RandomSource rand) {
		super();
		this.value = value;
		this.unbreakingLevel = unbreakingLevel;
		this.stack = stack;
		this.rand = rand;
	}
	
	public RandomSource getRand() {
		return rand;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public int getUnbreakingLevel() {
		return unbreakingLevel;
	}

	public ItemStack getStack() {
		return stack;
	}
}
