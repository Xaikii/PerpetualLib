package net.perpetualeve.perpetuallib.misc.mixin.common.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.damagesource.DamageSource;
import net.perpetualeve.perpetuallib.misc.interfaces.IDamageSource;

@Mixin(value = DamageSource.class, priority = 177013)
public class DamageSourceMixin implements IDamageSource {
	/**
	 * If the damage source does not bypasses armor this vault will be added to the armor damage
	 */
	int armorDamage = 0;
	
	@Shadow
	public float exhaustion;

	@Override
	public float setExhaustion(float exhaustion) {
		return (this.exhaustion = exhaustion);
	}

	@Override
	public int getArmorDamage() {
		return this.armorDamage;
	}
	
	@Override
	public int setArmorDamage(int armorDamage) {
		return (this.armorDamage = armorDamage);
	}
}
