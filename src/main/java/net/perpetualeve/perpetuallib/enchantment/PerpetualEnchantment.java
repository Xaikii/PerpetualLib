package net.perpetualeve.perpetuallib.enchantment;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class PerpetualEnchantment extends Enchantment {

	protected PerpetualEnchantment(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_) {
		super(p_44676_, p_44677_, p_44678_);
	}

	
	public static class EnchantmentData {
		/**
		 * Name of the Enchantment
		 */
		@NotNull String name;
		/**
		 * The lowest level the Enchantment can be, doesn't have to be 1
		 */
		int minLevel = 1;
		/**
		 * The highest level the Enchantment can be by normal means
		 */
		int maxLevel = 5;
		/**
		 * The highest level the Enchantment can be during the Enchantment Level getters.<br>
		 * Some mods make it possible to exceed the maxLevel.
		 */
		int levelCap = 100;
		/**
		 * The required Player Level to trigger 'Transcended' effects, if implemented
		 */
		int transcendingLevel = 1000;
		/**
		 * The required Enchantment Level to trigger 'Omega' effects, if implemented
		 */
		int omegaLevel = 2147483646;
		/**
		 * The rarity of said Enchantments.<br>
		 * Can be COMMON, UNCOMMON, RARE, EPIC.<br>
		 * With respective Weight of 10, 4, 2, 1.
		 */
		Rarity rarity = Rarity.UNCOMMON;
		/**
		 * The minimum Enchanting Power required to get the Enchantment
		 */
		int baseCost = 10;
		/**
		 * How much more Enchanting Power is required for each level
		 */
		int perLevelCost = 5;
		/**
		 * A value to decide in which cost are the Enchantment can still be obtained.<br>
		 * <b>MaxCost</b>: baseCost + level * perLevelCost + rangeCost<br>
		 * <b>MinCost</b>: baseCost + level + perLevelCost  
		 */
		int rangeCost = 25;
		/**
		 * If the Enchantment should be considered as a Treasure Enchantment and won't be obtained by enchanting
		 */
		boolean isTreasure = false;
		/**
		 * If the Enchantment is tradeable by Librarians/Villagers
		 */
		boolean isTradeable = true;
		/**
		 * If the Enchantment should be allowed to be on Enchanted Books.<br>
		 * Useful when you make Enchantments that should be obtained by special Methods.
		 */
		boolean isAllowedOnBooks = true;
		/**
		 * If the Enchantment is a Curse
		 */
		boolean isCurse = false;
		/**
		 * If the Enchantment can be obtained by loot
		 */
		boolean isDiscoverable = true;
		/**
		 * The priority of the Enchantment to color the Tooltips.<br>
		 * Lowest priority has to be lower than the items priority.<br>
		 * Item priorities:<br>
		 * <ul><li><b>COMMON</b>: 10
		 * <li><b>UNCOMMON</b>: 4
		 * <li><b>RARE</b>: 2
		 * <li><b>EPIC</b>: 1</ul>
		 */
		int colorPriority = 0;
		/**
		 * The color for the Enchantment Name.<br>
		 * Always applies even if doesnt have priority.
		 */
		int textColor = -1;
		/**
		 * The background color for the Tooltips when this Enchantment has the highest priority
		 */
		int backgroundColor = -1;
		/**
		 * The upper border color for the Tooltips when this Enchantment has the highest priority
		 */
		int borderStartColor = -1;
		/**
		 * The lower border color for the Tooltips when this Enchantment has the highest priority
		 */
		int borderEndColor = -1;
		
		public EnchantmentData() {
			
		}
		
	}
}
