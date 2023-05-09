package net.perpetualeve.perpetuallib.misc.mixin.common.item;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.perpetualeve.perpetuallib.events.ItemStackDurabilityChangeEvent;
import net.perpetualeve.perpetuallib.events.ItemStackDurabilityChangeEvent.Post;
import net.perpetualeve.perpetuallib.events.ItemStackDurabilityChangeEvent.Pre;

@Mixin(value = ItemStack.class, priority = 177013)
public class ItemStackMixin {

	int preVal;
	int postVal;

	@ModifyConstant(method = "hurt", constant = @Constant(intValue = 0, ordinal = 3))
	public int unbreakingRequirement(int value) {
		return -1;
	}
	
	/**
	 * A mixin to expose the durability damage to take before mitigation happened<br>
	 * initalResult and durabilityDamage are the exact same values
	 * @param initialResult
	 * @param durabilityDamage
	 * @param worldRandom
	 * @param serverPlayer
	 * @return
	 */
	@ModifyVariable(method = "hurt", at = @At(value = "HEAD"), ordinal = 0, argsOnly = true)
	public int durabilityChangePre(int initialResult, int durabilityDamage, RandomSource worldRandom,
			@Nullable ServerPlayer serverPlayer) {
		ItemStackDurabilityChangeEvent.Pre event = new Pre(durabilityDamage, worldRandom, serverPlayer);
		MinecraftForge.EVENT_BUS.post(event);
		return (preVal = event.getDurabilityDamage());
	}
	
	/**
	 * A mixin to expose the durability damage to take after mitigation happened<br>
	 * also provides the amount of damage mitigated<br>
	 * initalResult and durabilityDamage are the exact same values
	 * @param initialResult
	 * @param durabilityDamage
	 * @param worldRandom
	 * @param serverPlayer
	 * @return
	 */
	@ModifyVariable(method = "hurt", at = @At(value = "INVOKE_ASSIGN", ordinal = 3, shift = Shift.AFTER), argsOnly = true)
	public int durabilityChangePost(int initialResult, int durabilityDamage, RandomSource worldRandom,
			@Nullable ServerPlayer serverPlayer) {
		ItemStackDurabilityChangeEvent.Post event = new Post(preVal - durabilityDamage, durabilityDamage, worldRandom,
				serverPlayer);
		MinecraftForge.EVENT_BUS.post(event);
		return (postVal = event.getDurabilityDamage());
	}

	public int getPreVal() {
		return preVal;
	}

	public int getPostVal() {
		return postVal;
	}

}
