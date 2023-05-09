package net.perpetualeve.perpetuallib.misc;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.perpetualeve.perpetuallib.events.ItemStackDurabilityLossEvent;
import net.perpetualeve.perpetuallib.events.ItemStackMultiDurabilityLossEvent;

public class UnbreakingUtils {

	public static boolean tryNegateDurabilityCost(ItemStack pStack, int pLevel, RandomSource pRandom,
			CallbackInfoReturnable<?> cir) {
		float num = getBase(pStack);
		if (num == 0.0f)
			return false;
		ItemStackDurabilityLossEvent event = new ItemStackDurabilityLossEvent(num, pLevel, pStack, pRandom);
		cir.cancel();
		return MinecraftForge.EVENT_BUS.post(event)
				|| (pRandom.nextFloat() > getUnbreakingChance(event.getValue(), pLevel));
	}

	public static boolean tryNegateDurabilityCost(ItemStack pStack, int pLevel, RandomSource pRandom) {
		float num = getBase(pStack);
		if (num == 0.0f)
			return false;
		ItemStackDurabilityLossEvent event = new ItemStackDurabilityLossEvent(num, pLevel, pStack, pRandom);
		return MinecraftForge.EVENT_BUS.post(event)
				|| (pRandom.nextFloat() > getUnbreakingChance(event.getValue(), pLevel));
	}

	public static float getBase(ItemStack pStack) {
		return StackUtils.getFloat(pStack, "unbreaking", 0.0f);
	}

	public static ItemStack setBase(ItemStack pStack, float amount) {
		StackUtils.setFloat(pStack, "unbreaking", amount);
		return pStack;
	}

	public static float getUnbreakingValue(float unbreakingChance, float unbreakingLevel) {
		return (100 + unbreakingChance) * (1 + unbreakingLevel);
	}

	/**
	 * This works inverse, goes from 100% to <1% down<br>
	 * So your RandomFloat has to be higher to suceed, not lower
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static float getUnbreakingChance(float unbreakingChance, float unbreakingLevel) {
		return 100 / Math.max(100, getUnbreakingValue(unbreakingChance, unbreakingLevel));
	}

	/**
	 * To be used if you want a more controllable outcome.<br>
	 * Will usually stay within the expected range.<br>
	 * Can be cancelled to mitigate all DurabilityCost
	 * 
	 * @param amount
	 * @param pStack
	 * @param pLevel
	 * @param pRandom
	 * @return
	 */
	public static int tryReduceMultiDurabilityCost(int amount, ItemStack pStack, int pLevel, RandomSource pRandom) {
		ItemStackMultiDurabilityLossEvent event = new ItemStackMultiDurabilityLossEvent(amount, getBase(pStack), pLevel,
				pStack, pRandom);
		if (MinecraftForge.EVENT_BUS.post(event))
			return 0;
		float base = (int) (amount * getUnbreakingChance(event.getValue(), pLevel));
		int ib = (int) base;
		return ib + (((base - ib) >= pRandom.nextFloat()) ? 1 : 0);
	}
}
