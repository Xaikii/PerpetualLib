package net.perpetualeve.perpetuallib.misc.mixin.common.enchantments;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DigDurabilityEnchantment;
import net.perpetualeve.perpetuallib.misc.UnbreakingUtils;

@Mixin(value = DigDurabilityEnchantment.class, priority = 177013)
public class UnbreakingEnchantmentMixin {
	
	@Inject(method ="shouldIgnoreDurabilityDrop", at = @At("HEAD"), cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private static void negateDurability(ItemStack pStack, int pLevel, RandomSource pRandom, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(UnbreakingUtils.tryNegateDurabilityCost(pStack, pLevel, pRandom, cir));
	}
}
