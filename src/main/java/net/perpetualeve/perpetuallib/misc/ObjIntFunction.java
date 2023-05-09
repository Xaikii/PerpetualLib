package net.perpetualeve.perpetuallib.misc;

@FunctionalInterface
public interface ObjIntFunction<T>
{
	public int apply(T t, int v);
}
