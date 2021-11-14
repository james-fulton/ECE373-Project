package Gameplay;

import Levels.FieldPoint;

public class Player {

private FieldPoint location;
private int lives;
private int totalPoints;
private int bulletDamage;
private boolean bulletHit;
private boolean collision;

public Player() {
	
	location = new FieldPoint(1.0, 1.0, 90.0); //ship starting location, bottom of the screen in the middle
	this.lives = 3; // default starter value
	this.bulletDamage = 1;
	this.totalPoints = 0;
	this.collision = false;
	this.bulletHit = false;
}

public void setLocation(FieldPoint loc) {
	location = loc;
}

public FieldPoint getLocation() {
	return location;
}

public boolean getBulletHit() {
	return this.bulletHit;
}

public void setBulletHit(boolean hit) { // set laser hit with debris
	this.bulletHit = hit;
}

public int getBulletDamage() {
	return this.bulletDamage;
}

public boolean getCollision() {
	return collision;
}

public void setCollision(boolean collision) { // set Collision w/ debris
	this.collision = collision;
	lives = lives-1;
}
public void addPoints(int num) {
	this.totalPoints += num;
}

public int getTotalPoints() {
	return this.totalPoints;
}

public void removeLives(Zombie target) {
	this.collision = false; // RESET COLLISION
	this.lives += target.getCollisionDmg();
}

public int getLives() {
	return lives;
}


}
