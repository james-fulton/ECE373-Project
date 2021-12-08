package Gameplay;

import Levels.FieldPoint;
import java.lang.Math;
import java.util.Timer;
import java.util.TimerTask;

public class Zombie extends Entity {
	//Variables
	private boolean playGrowl;
	private boolean growlTimer;
	private float growlLength;
	private int identifier;
	private boolean hit;
	private boolean missHit;
	private float hitTime;

	//Constructors
	public Zombie(double diffLevel, double speed, int identifier) {
		super();
		this.maxHealth = (int)(50 * diffLevel);
		this.health = this.maxHealth;
		this.points = 50;
		this.speed = speed;
		this.meleeDamage = 25;
		this.identifier = identifier;
		hit = false;
		missHit = false;
		playGrowl = false;
		growlTimer = false;
		growlLength = 1;
	}

	
	//Setters & Getters
	public void setHit(boolean hit) {
		this.hit = hit;
	}
	public boolean getHit() {
		return hit;
	}
	public void setMissHit(boolean missHit) {
		this.missHit = missHit;
	}
	public boolean getMissHit() {
		return missHit;
	}
	
	public void getIdentifier(int identifier) {
		this.identifier = identifier;
	}
	
	public int getIdentifer() {
		return identifier;
	}
	public boolean getPlayGrowl() {
		return playGrowl;
	}

	public void setPlayGrowl(boolean playGrowl) {
		this.playGrowl = playGrowl;
	}

	public boolean getGrowlTimer() {
		return growlTimer;
	}

	public void setGrowlTimer(boolean growlTimer) {
		this.growlTimer = growlTimer;
	}

	public float getGrowlLength() {
		return growlLength;
	}

	public void setGrowlLength(float growlLength) {
		this.growlLength = growlLength;
	}
	
	
	//Void
	public void removeDebris(int health) {
		// TODO
	}
	
	public void findTarget(FieldPoint target) {
		location.findAngle(target, true, true);
	}
	
	//return is if dead
	public boolean receivedDamage(int increment) {
		health -= increment;
		if(health <= 0) { return true; }
		return false;
	}
	
	public void hitPlayer(Timer auxTimer) {
		if(!hit) {
			auxTimer.schedule(new TimerTask() {
				@Override
				public void run() { hit = false; }
				}, (long)(480));
		}
		hit = true;
	}
	

}
