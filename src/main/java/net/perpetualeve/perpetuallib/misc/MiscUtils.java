package net.perpetualeve.perpetuallib.misc;

public class MiscUtils {
	
	@SuppressWarnings("unchecked")
	public static <V> V getMixinCast(Object obj) {
		return (V)obj;
	}

	@SuppressWarnings("unchecked")
	public static <V> V getSelfAs(Class<V> target, Object obj) {
		return (V)obj;
	}

}
