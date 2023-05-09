package net.perpetualeve.perpetuallib.misc;

import java.util.function.Consumer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JSONUtils {

	public static void iterate(JsonElement element, Consumer<JsonObject> objects) {
		if (element.isJsonObject())
			objects.accept(element.getAsJsonObject());
		else if (element.isJsonArray())
			element.getAsJsonArray().forEach(T -> iterate(T, objects));
	}
	
	public static JsonArray getOrEmpty(JsonObject obj, String tag) {
		return obj.has(tag) ? obj.get(tag).getAsJsonArray() : new JsonArray();
	}

	public static JsonObject getOrSelf(JsonObject obj, String tag) {
		return obj.has(tag) ? obj.getAsJsonObject(tag) : obj;
	}

	public static long getOrDefault(JsonObject obj, String tag, long defaultValue) {
		return obj.has(tag) ? obj.get(tag).getAsLong() : defaultValue;
	}

	public static int getOrDefault(JsonObject obj, String tag, int defaultValue) {
		return obj.has(tag) ? obj.get(tag).getAsInt() : defaultValue;
	}

	public static float getOrDefault(JsonObject obj, String tag, float defaultValue) {
		return obj.has(tag) ? obj.get(tag).getAsFloat() : defaultValue;
	}

	public static double getOrDefault(JsonObject obj, String tag, double defaultValue) {
		return obj.has(tag) ? obj.get(tag).getAsDouble() : defaultValue;
	}

	public static boolean getOrDefault(JsonObject obj, String tag, boolean defaultValue) {
		return obj.has(tag) ? obj.get(tag).getAsBoolean() : defaultValue;
	}

	public static String getOrDefault(JsonObject obj, String tag, String defaultValue) {
		return obj.has(tag) ? obj.get(tag).getAsString() : defaultValue;
	}

}
