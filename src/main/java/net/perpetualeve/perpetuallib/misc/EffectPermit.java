package net.perpetualeve.perpetuallib.misc;

public class EffectPermit {

	boolean perm = true;
	int level = 0;
	
	public EffectPermit(int level) {
		this.level = level;
	}
	
	public void setPerm(boolean perm) {
		this.perm = perm;
	}
	
	public boolean getPerm() {
		return this.perm;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return this.level;
	}
}
