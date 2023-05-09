package net.perpetualeve.perpetuallib.misc.mixin.common.world.entity;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event.Result;
import net.perpetualeve.perpetuallib.PerpetualLib;
import net.perpetualeve.perpetuallib.events.MobEffectLoadEffectEvent;
import net.perpetualeve.perpetuallib.events.MobEffectTickEvent;
import net.perpetualeve.perpetuallib.events.MobEffectWriteDetailsEvent;
import net.perpetualeve.perpetuallib.misc.MiscUtils;
import net.perpetualeve.perpetuallib.misc.interfaces.IMobEffectInstance;

@Mixin(value = MobEffectInstance.class, priority = 177013)
public class MobEffectInstanceMixin implements IMobEffectInstance {

	/**
	 * AppliedDuration gives the duration at the moment of being applied, useful for features that are based on how long the effect lasted<br>
	 * This value can only be found on the client-side<br>
	 * On re-joining a world this will reset
	 */
	int appliedDuration = 0;
	/**
	 * CreationDuration gives the duration the Effect was initially created, you can get the time mitigated (e.g. by Enchantments some Mod might add) when Subtracting this by appliedDuration<br>
	 * On re-joining a world this will reset
	 */
	int creationDuration = 0;
	/**
	 * ExistDuration gives the time how long the Effect was active on the entity<br>
	 * For accurate results, this should only be gotten on server-side<br>
	 * On re-joining a world this will reset
	 */
	int existDuration = 0;

	@Shadow
	public MobEffect effect;
	@Shadow
	public int duration;
	@Shadow
	public int amplifier;
	@Shadow
	public boolean visible;
	@Shadow
	public MobEffectInstance hiddenEffect;
	@Shadow
	public boolean ambient;
	@Shadow
	public boolean showIcon;
	@Shadow 
	public boolean noCounter;
	@Shadow
	public Optional<MobEffectInstance.FactorData> factorData;
	
	@Inject(method = "<init>(Lnet/minecraft/world/effect/MobEffect;IIZZZLnet/minecraft/world/effect/MobEffectInstance;Ljava/util/Optional;)V", at = @At(value = "TAIL"))
	public void effectCreation(MobEffect effect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible, boolean pShowIcon, @Nullable MobEffectInstance pHiddenEffect, Optional<MobEffectInstance.FactorData> pFactorData, CallbackInfo ci) {
		this.creationDuration = pDuration;
	}
	
	@Inject(method = "writeDetailsTo", at = @At(value = "TAIL")) 
	public void writeDetails(CompoundTag pNbt, CallbackInfo ci) {
		pNbt.putInt("Applied", appliedDuration);
		pNbt.putInt("Creation", creationDuration);
		pNbt.putInt("Exist", existDuration);
		MobEffectWriteDetailsEvent event = new MobEffectWriteDetailsEvent(this, pNbt);
		MinecraftForge.EVENT_BUS.post(event);
	}
	
	@Inject(method = "loadSpecifiedEffect", at = @At(value = "RETURN"), cancellable = true) 
	private static void loadEffect(MobEffect pEffect, CompoundTag pNbt, CallbackInfoReturnable<MobEffectInstance> cir) {
		IMobEffectInstance meim = (IMobEffectInstance)cir.getReturnValue();
		meim.setAppliedDuration(pNbt.getInt("Applied"));
		meim.setCreationDuration(pNbt.getInt("Creation"));
		meim.setExistDuration(pNbt.getInt("Exist"));
		MobEffectLoadEffectEvent event = new MobEffectLoadEffectEvent(meim, pNbt);
		MinecraftForge.EVENT_BUS.post(event);
		cir.cancel();
		cir.setReturnValue((MobEffectInstance)event.getMeim());
	}
	
	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffectInstance;tickDownDuration()I", shift = Shift.AFTER))
	public void effectTick(LivingEntity entity, Runnable pOnExpirationRunnable, CallbackInfoReturnable<Boolean> cir) {
		MobEffectTickEvent event = new MobEffectTickEvent(entity,1, MiscUtils.getMixinCast(this));
		MinecraftForge.EVENT_BUS.post(event);
		if(!event.isCanceled()) this.existDuration += event.getTickAmount();
	}
	
	public MobEffectInstance getSelf() {
		return MiscUtils.getMixinCast(this);
	}
	
	@Override
	public boolean overwriteEffect(int amplifier) {
		boolean flag = false;
		if (amplifier >= 0) {
			this.amplifier = amplifier;
			flag = true;
		}
		if (hiddenEffect != null) {
			((IMobEffectInstance) (Object) hiddenEffect).overwriteEffect(amplifier);
		}
		if (flag) {
			factorData.ifPresent(T -> {
				T.update(MiscUtils.getSelfAs(MobEffectInstance.class, this));
			});
			return true;
		}
		return false;
	}

	@Override
	public boolean overwriteEffect(int duration, int amplifier) {
		boolean flag = false;
		if (duration >= 0) {
			this.duration = duration;
			flag = true;
		}
		if (amplifier >= 0) {
			this.amplifier = amplifier;
			flag = true;
		}
		if (hiddenEffect != null) {
			((IMobEffectInstance) (Object) hiddenEffect).overwriteEffect(duration, amplifier);
		}
		if (flag) {
			factorData.ifPresent(T -> {
				T.update(MiscUtils.getSelfAs(MobEffectInstance.class, this));
			});
			return true;
		}
		return false;
	}

	@Override
	public boolean overwriteEffect(int duration, int amplifier, MobEffectInstance effect, LivingEntity ent, Entity en) {
		boolean flag = false;
		if (duration >= 0) {
			this.duration = duration;
			flag = true;
		}
		if (amplifier >= 0) {
			this.amplifier = amplifier;
			flag = true;
		}
		if (effect != null && effect.getEffect() != this.effect && doUpdate(ent, effect, en)) {
			flag = true;
		}
		if (hiddenEffect != null) {
			((IMobEffectInstance) (Object) hiddenEffect).overwriteEffect(duration, amplifier, effect, ent, en);
		}
		if (flag) {
			factorData.ifPresent(T -> {
				T.update(MiscUtils.getSelfAs(MobEffectInstance.class, this));
			});
			return true;
		}
		return false;
	}

	public boolean doUpdate(LivingEntity ent, MobEffectInstance effect, Entity en) {
		net.minecraftforge.event.entity.living.MobEffectEvent.Applicable event = new net.minecraftforge.event.entity.living.MobEffectEvent.Applicable(
				ent, effect);
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY) return false;
		updateEffect(effect, ent, en);
		return true;
	}

	public void updateEffect(MobEffectInstance effect, LivingEntity ent, Entity en) {
		try {
			net.minecraftforge.common.MinecraftForge.EVENT_BUS
					.post(new net.minecraftforge.event.entity.living.MobEffectEvent.Added(ent,
							ent.getEffect(this.effect), effect, en));
			this.amplifier = Math.min(effect.getAmplifier(), this.amplifier);
			this.effect = effect.getEffect();
		} catch (Exception e) {
			PerpetualLib.LOGGER.error("Failed overwriting Effects");
		}
	}

	@Override
	public int getCreationDuration() {
		return creationDuration;
	}

	@Override
	public void setCreationDuration(int creationDuration) {
		this.creationDuration = creationDuration;
	}

	@Override
	public int getAppliedDuration() {
		return appliedDuration;
	}

	@Override
	public void setAppliedDuration(int appliedDuration) {
		this.appliedDuration = appliedDuration;
	}
	
	@Override
	public int getExistDuration() {
		return existDuration;
	}
	
	@Override
	public void setExistDuration(int existDuration) {
		this.existDuration = existDuration;
	}
}
