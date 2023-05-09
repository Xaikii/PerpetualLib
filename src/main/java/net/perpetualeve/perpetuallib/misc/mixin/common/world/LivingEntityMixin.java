package net.perpetualeve.perpetuallib.misc.mixin.common.world;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.perpetualeve.perpetuallib.misc.interfaces.IDamageSource;
import net.perpetualeve.perpetuallib.misc.interfaces.IMobEffectInstance;

@Mixin(value = LivingEntity.class, priority = 177013)
public class LivingEntityMixin {
	LivingEntity entity = ((LivingEntity)(Object)this);

	@Inject(method = "onEffectAdded", at=@At(value="HEAD"))
	public void effectAdded(MobEffectInstance pInstance, @Nullable Entity pEntity, CallbackInfo ci) {
		((IMobEffectInstance)pInstance).setAppliedDuration(pInstance.getDuration());
	}
	
	@Inject(method = "getDamageAfterArmorAbsorb", at=@At(value = "HEAD"))
	public void hurtInjection(DamageSource pSource, float pAmount, CallbackInfoReturnable<Float> cir) {
		System.out.println(entity.getDisplayName().getString());
		/*
		 * To Be Done
		 */
	}
	
	@ModifyArg(method = "getDamageAfterArmorAbsorb", at=@At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurtArmor(Lnet/minecraft/world/damagesource/DamageSource;F)V"))
	private float hurtArmorValueInjection(DamageSource pDamageSource, float pDamageAmount) {
		return pDamageAmount + ((IDamageSource)pDamageSource).getArmorDamage();
	}
}
