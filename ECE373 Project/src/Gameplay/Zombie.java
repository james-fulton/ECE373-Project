package Gameplay;

import Levels.FieldPoint;

public class Zombie {
	public int zombieHealth; // 0-3 
	public int points;  // 0-300 (increments of 100 respective to size);
	private FieldPoint location;
	
	public Zombie(int diffLevel) {
		if (diffLevel == 1) {
			this.zombieHealth = 1; 
			this.points = 100; 
		}
		else if (diffLevel == 2) {
			this.zombieHealth = 2; 
			this.points = 200; 
		}
		else if (diffLevel == 3) {
			this.zombieHealth = 3; 
			this.points = 300; 
		}
		this.location = new FieldPoint();
	}
	
	public int getZombieHealth() {
		return this.zombieHealth;
	}
	
	
	public void setZombieHealth(int num) {
		this.zombieHealth = num;
	}

	
	public void setPoint(int num) {
		this.points = num;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public int getCollisionDmg() {
			return -1; 
	}
	
	public void removeDebris(int health) {
		// TODO
	}

	public FieldPoint getLocation() {
		return this.location;
	}
		
	public void setLocation(FieldPoint targetLocation) {
		this.location = targetLocation;
	}
}
