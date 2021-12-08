package Gameplay;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Levels.FieldPoint;

public class Bullet {
	//Variables
	private FieldPoint location;
	private int damage;
	private int speed;
	private int collisions;
	private int maxCollisions;
	private int speedReductionPerCollision;
	private ArrayList<Integer> zombiesHit;
	
	//Constructors
	public Bullet() {
		this(10);
	}
	public Bullet(int damage, int speed, int maxCollisions, int speedReductionPerCollision) {
		this.damage = damage;
		this.speed = speed;
		collisions = 0;
		this.maxCollisions = maxCollisions;
		this.speedReductionPerCollision = speedReductionPerCollision;
		zombiesHit = new ArrayList<Integer>();
	}
	public Bullet(int damage) {
		this.damage = damage;
		speed = 10;
		collisions = 0;
		maxCollisions = 1;
		zombiesHit = new ArrayList<Integer>();
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
	
	//return if bullet has no speed
	public boolean addCollision(int currentZombie, Timer auxTimer) {
		collisions++;
		zombiesHit.add(currentZombie);
		if(collisions == 1) { speed -= 10; }
		speed -= speedReductionPerCollision;
		if(collisions >= maxCollisions) {
			return true;
		}
		
		auxTimer.schedule(new TimerTask() {
			@Override
			public void run() { zombiesHit.remove(findZombie(currentZombie)); }
			}, (long)(200));
		
		
		return false;
	}
	
	public int getMaxCollisions() {
		return maxCollisions;
	}
	public int getCollisions() {
		return collisions;
	}
	
	public int findZombie(int zomNum) {
		for(int i = 0; i < zombiesHit.size(); i++) {
			if(zombiesHit.get(i) == zomNum) { return i; }
		}
		return -1;
	}
	
	public double findImageDistance(FieldPoint target, double xMax, double yMax) {
		//variables
		double finalDistance = 0;
		double targetAngle = location.findAngle(target, false, false);
		double rightAngle = location.getAngleView() + 90.0;
		double zombAngle = targetAngle;
		double mathAngle;
		
		//Find angle to right side of bullet
		if(location.getAngleView() == 270) { rightAngle = 0; }
		else if(rightAngle > 360) {
			int amount = (int)(rightAngle / 360.0);
			rightAngle -= 360.0 * amount;
		}
		
		//Find zombAngle -> angle from zombie to right side of bullet
		if(rightAngle > targetAngle) {
			zombAngle = 360.0 - (rightAngle - targetAngle);
		}
		else if(rightAngle < targetAngle) {
			zombAngle = targetAngle - rightAngle;
		}
		
		//find distance of bullet image at zombAngle
		//(xMax & yMax image for bullet are at 270 degrees)
		mathAngle = zombAngle;
		if(zombAngle == 270 || zombAngle == 90) { finalDistance = yMax; }
		else if(zombAngle == 0 || zombAngle == 180) { finalDistance = xMax; }
		else if(zombAngle > 315 && zombAngle < 360) {
			mathAngle = 360.0 - zombAngle; 
			finalDistance = xMax / Math.cos(Math.toRadians(mathAngle));
		}
		else if(zombAngle >= 225) {
			if(zombAngle > 270) { mathAngle = zombAngle - 270; }
			else { mathAngle = 270 - zombAngle; }
			finalDistance = yMax / Math.cos(Math.toRadians(mathAngle));
		}
		else if(zombAngle >= 135) {
			if(zombAngle > 180) { mathAngle = zombAngle - 180; }
			else { mathAngle = 180 - zombAngle; }
			finalDistance = xMax / Math.cos(Math.toRadians(mathAngle));
		}
		else if(zombAngle >= 45) {
			if(zombAngle > 90) { mathAngle = zombAngle - 90; }
			else { mathAngle = 90 - zombAngle; }
			finalDistance = yMax / Math.cos(Math.toRadians(mathAngle));
		}
		else if(zombAngle >= 0) {
			finalDistance = xMax / Math.cos(Math.toRadians(mathAngle));
		}
		
		return finalDistance;
	}
	
}
