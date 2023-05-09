package net.perpetualeve.perpetuallib.misc.mixin.common.world.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;

@Mixin(value = AbstractArrow.class, priority = 177013)
public interface ArrowMixin {
	
	@Invoker("getPickupItem")
	public ItemStack getArrowItem();
}
