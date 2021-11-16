package Gameplay;

import Levels.FieldPoint;

public class Bullet {
	//Variables
	private FieldPoint location;
	private int damage;
	private int speed;
	
	//Constructors
	public Bullet() {
		this(0, 0);
	}
	public Bullet(int damage, int speed) {
		this.damage = damage;
		this.speed = speed;
	}
	public Bullet(int damage) {
		this.damage = damage;
		speed = 10;
	}
	
	
	//Setters & Getters
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public FieldPoint getLocation() {
		return location;
	}
	
	public void setLocation(FieldPoint location) {
		this.location = location;
	}
	
}
