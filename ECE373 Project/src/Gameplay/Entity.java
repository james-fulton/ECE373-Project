package Gameplay;

import Levels.FieldPoint;

public class Entity {
	//Variables
	protected FieldPoint location;
	protected double speed;
	protected Integer health;
	protected Integer meleeDamage;
	protected Integer points;
	protected boolean collision;
	protected boolean footstepTimer;

	//Sound
	protected boolean playingFootstep;
	protected float footstepLength;
	
	//Constructors
	public Entity() {
		speed = 1;
		health = 100;
		meleeDamage = 50;
		points = 0;
		collision = false;
		location = new FieldPoint();
		playingFootstep = false;
		footstepTimer = false;
		footstepLength = 0;
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
	public boolean getFootstepTimer() {
		return footstepTimer;
	}

	public void setFootstepTimer(boolean footstepTimer) {
		this.footstepTimer = footstepTimer;
	}
	public boolean getPlayingFootstep() {
		return playingFootstep;
	}

	public void setPlayingFootstep(boolean playingFootstep) {
		this.playingFootstep = playingFootstep;
	}

	public float getFootstepLength() {
		return footstepLength;
	}

	public void setFootstepLength(float footstepLength) {
		this.footstepLength = footstepLength;
	}
	public boolean getCollision() {
		return collision;
	}
	public void setCollision(boolean col) {
		this.collision = col;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
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
