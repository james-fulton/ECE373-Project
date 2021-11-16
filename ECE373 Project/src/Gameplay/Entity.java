package Gameplay;

import Levels.FieldPoint;

public class Entity {
	//Variables
	protected FieldPoint location;
	protected Integer speed;
	protected Integer health;
	protected Integer meleeDamage;
	protected Integer points;
	protected boolean collision;
	
	//Constructors
	public Entity() {
		speed = 1;
		health = 100;
		meleeDamage = 50;
		points = 0;
		collision = false;
		location = new FieldPoint();
	}
	
	//Setters & Getters
	public FieldPoint getLocation() {
		return location;
	}
	public void setLocation(FieldPoint loc) {
		location.setLocation(loc);
	}
	public void setLocation(double x, double y) {
		location.setLocation(x, y);
	}
	public boolean getCollision() {
		return collision;
	}
	public void setCollision(boolean col) {
		this.collision = col;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public Integer getHealth() {
		return health;
	}
	public void setHealth(Integer health) {
		this.health = health;
	}
	public Integer getMeleeDamage() {
		return meleeDamage;
	}
	public void setMeleeDamage(Integer damage) {
		this.meleeDamage = damage;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	//Body
	
}
