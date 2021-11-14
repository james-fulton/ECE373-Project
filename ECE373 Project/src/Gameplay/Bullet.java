package Gameplay;

import Levels.FieldPoint;

public class Bullet {
	private FieldPoint location;
	private int damage;
	
	public Bullet() {
		
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public FieldPoint getLocation() {
		return location;
	}
	
	public void setLocation(FieldPoint location) {
		this.location = location;
	}
	
}
