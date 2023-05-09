package net.perpetualeve.perpetuallib.misc;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@SuppressWarnings("unchecked")
public class StatisticHelper {
	
	@SuppressWarnings("rawtypes")
	public static Map statTypes = new HashMap<>();
	public static Map<String, String> registryRelation = new HashMap<>();
	public static Map<String, Stat<ResourceLocation>> customTypes = new HashMap<>();
	
	public static <T> int getStat(ResourceLocation RL, T value, ServerStatsCounter statis) {
		StatType<T> type = (StatType<T>) ForgeRegistries.STAT_TYPES.getValue(RL);
		return statis.getValue(type.get(value));
	}
	
	public static <T> StatType<T> getStat(ResourceLocation RL, T value) {
		return (StatType<T>) ForgeRegistries.STAT_TYPES.getValue(RL);
	}
	
	public static <T> int getValue(StatType<T> type, T value,  ServerStatsCounter statis) {
		return statis.getValue(type.get(value));
	}
	
	public static void fillMap() {
		statTypes.putIfAbsent("blocks", ForgeRegistries.BLOCKS);
		statTypes.putIfAbsent("entity", ForgeRegistries.ENTITY_TYPES);
		statTypes.putIfAbsent("items", ForgeRegistries.ITEMS);

		registryRelation.putIfAbsent("minecraft:broken", "blocks");
		registryRelation.putIfAbsent("minecraft:crafted", "item");
		registryRelation.putIfAbsent("minecraft:dropped", "item");
		registryRelation.putIfAbsent("minecraft:killed", "entity");
		registryRelation.putIfAbsent("minecraft:killed_by", "entity");
		registryRelation.putIfAbsent("minecraft:mined", "blocks");
		registryRelation.putIfAbsent("minecraft:picked_up", "item");
		registryRelation.putIfAbsent("minecraft:used", "item");
		
		for(Stat<ResourceLocation> a: Stats.CUSTOM) {
			customTypes.put(a.getValue().toString(), a);
		}
	}

	public static IForgeRegistry<?> getFromMap(String name) {
		return (IForgeRegistry<?>) statTypes.get(name);
	}
	
	public static Stat<ResourceLocation> getCustomFromMap(String name) {
		return customTypes.get(name);
	}
	
	public static String getRelation(String name) {
		return registryRelation.get(name);
	}
	
}
