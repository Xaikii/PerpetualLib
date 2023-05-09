package net.perpetualeve.perpetuallib.misc;

import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

public class NBTUtils {

	public static void getNBT(JsonObject obj, List<String> order) {
		if (obj.get("nbt") != null) {
			order.add(obj.get("nbt").getAsString());
			if (obj.get("nest") != null) {
				getNBT(obj.get("nest").getAsJsonObject(), order);
			}
		}
	}

	public static Tag findValue(Tag nbt, int index, List<String> order) {
		Tag result = nbt;
		for (int i = index; i < order.size(); i++) {
			String path = order.get(i);
			if (result instanceof CompoundTag) {
				result = ((CompoundTag) result).get(path);
			} else if (result instanceof ListTag) {
				ListTag ls = (ListTag) result;
				for (Tag k : ls) {
					Tag found = findValue(k, i, order);
					if (found != null)
						return found;
				}
			}
		}
		return result;
	}
}
