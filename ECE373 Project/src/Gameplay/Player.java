package Gameplay;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Player extends Entity {

//Variables
private int lives;
private boolean bulletHit;
//private Integer bulletDamage;
private ArrayList<Gun> guns;
private int displayBullets;
private int maxGuns;
private int selectedGun;
private double lookAngle; //different from move angle in FieldPoint
private boolean use;
private boolean gunFired;
private boolean reload;
private boolean sprint;
private boolean hit;
private boolean god;

private boolean bulletTimer;

//Constructors
public Player(double x, double y) {
	super();
	this.lives = 3;
	this.speed = 3.0;
	this.points = 0;
	maxGuns = 2;
	bulletHit = false;
	gunFired = false;
	bulletTimer = false;
	reload = false;
	sprint = false;
	use = false;
	lookAngle = 0.0;
	this.guns = new ArrayList<Gun>();
	int randomGun = ThreadLocalRandom.current().nextInt(0, 6 + 1);
	if(randomGun == 5) { randomGun++; }
	else if(randomGun == 2) { randomGun++; }
	Gun firstGun = new Gun(randomGun);
	//firstGun.sf_use_ignoreammo(1);
	randomGun = ThreadLocalRandom.current().nextInt(0, 6 + 1);
	if(randomGun == 5) { randomGun++; }
	else if(randomGun == 2) { randomGun++; }
	Gun secondGun = new Gun(randomGun);
	//secondGun.sf_use_ignoreammo(1);
	guns.add(firstGun);
	guns.add(secondGun);
	selectedGun = 0;
	location.setLocation(x, y);
	displayBullets = getCurrentBullets() - getCurrentMag();
}

//Setters & Getters
public boolean getBulletHit() {
	return this.bulletHit;
}

public void setBulletHit(boolean hit) { // set laser hit with debris
	this.bulletHit = hit;
}

public int getBulletDamage() {
	return guns.get(selectedGun).getDamage();
}

public boolean getReload() {
	return reload;
}

public void setReload(boolean reload) {
	this.reload = reload;
}

public void setHit(boolean hit) {
	this.hit = hit;
}

public boolean getHit() {
	return hit;
}

public boolean selectGun(int index) {
	if(index > guns.size()-1 || index == selectedGun || index < 0) {
		return false;
	}
	selectedGun = index;
	displayBullets = getCurrentBullets() - getCurrentMag();
	return true;
}

public String getGunName() {
	if(guns.size() > 0) { return guns.get(selectedGun).getName(); }
	return "none";
}

public double getGunROF() {
	return guns.get(selectedGun).getRateOfFire();
}

public boolean getGunFired() {
	return gunFired;
}

public Gun getCurrentGun() {
	return guns.get(selectedGun);
}
public int getCurrentBullets() {
	return getCurrentGun().getCurrentBullets();
}
public int getCurrentMag() {
	return guns.get(selectedGun).getCurrentMag();
}
public void setMaxGuns(int value) {
	this.maxGuns = value;
}


//if withBullets == true; will find a gun with bullets
public boolean getOtherGun(boolean withBullets) {
	
	for(int i = 0; i < guns.size(); i++) {
		if(i != selectedGun && (!withBullets || guns.get(i).getCurrentBullets() != 0)) {
			selectGun(i);
			bulletTimer = false;
			gunFired = false;
			reload = false;
			return true;
		}
	}
	return false;
}

public boolean getNextGun() {
	int index = selectedGun + 1;
	if(selectedGun + 1 == guns.size()) {
		if(selectedGun == 0) { return false; }
		index = 0; 
	}
	return selectGun(index);
}

public void setGunFired(boolean gunFired) {
	this.gunFired = gunFired;
}

public boolean reload() {
	boolean temp = guns.get(selectedGun).reload();
	if(temp) { displayBullets = getCurrentBullets() - getCurrentMag(); }
	bulletTimer = false;
	gunFired = false;
	reload = false;
	return temp;
}

public boolean getBulletTimer() {
	return bulletTimer;
}

public void setBulletTimer(boolean bulletTimer) {
	this.bulletTimer = bulletTimer;
}

public double getLookAngle() {
	return lookAngle;
}

public void setLookAngle(double lookAngle) {
	this.lookAngle = lookAngle;
}

public boolean getInteract() {
	return use;
}

public void setInteract(boolean use) {
	this.use = use;
}

public void changeLives(int life) {
	this.lives = lives + life;
}

public int getLives() {
	return lives;
}

public boolean getSprint() {
	return sprint;
}

public void setSprint(boolean state) {
	if(state == sprint) { return; }
	
	sprint = state;
	if(state) {
		speed = 6.5;
	}
	else {
		speed = 3.0;
	}
}

public void addGun(Gun newGun) {
	if(guns.size() == maxGuns) {
		removeGun(guns.get(selectedGun));
	}
	guns.add(newGun);
	selectedGun = 1;
	displayBullets = getCurrentBullets() - getCurrentMag();
}

public void removeGun(Gun newGun) {
	for(int i = 0; i < guns.size(); i++) {
		if(guns.get(i).getName() == newGun.getName()) {
			guns.remove(i);
			return;
		}
	}
}

public void addPoints(int points) {
	this.points += points;
}

public void maxAmmo() {
	for(int i = 0; i < guns.size(); i++) {
		guns.get(i).maxAmmo();
	}
	displayBullets = getCurrentBullets() - getCurrentMag();
}

//return is if game is over
public boolean zombieHit(int meleeDamage) {
	health -= meleeDamage;
	hit = true;
	if(health <= 0) {
		if(god) {
			health = maxHealth;
			return false;
		}
		lives--;
		if(lives <= 0) {
			return true;
		}
		health = maxHealth;
	}
	return false;
}

//return is if health is at max
public boolean addHealth(int increment) {
	if(health < maxHealth) {
		health += increment;
		if(health >= maxHealth) {
			health = maxHealth;
			return true;
		}
		return false;
	}
	return true;
}

public int getDisplayBullets() {
	return displayBullets;
}

public void clearGuns() {
	for(int i = 0; i < guns.size(); i++) {
		guns.get(i).clearBullets();
	}
	guns.clear();
}

public void sf_use_ignoreammo(int option) {
	for(int i = 0; i < guns.size(); i++) {
		guns.get(i).sf_use_ignoreammo(option);
	}
}

public void godMode() {
	health = 69420;
	maxHealth = 69420;
}

public void giveAll() {
	guns.clear();
	maxGuns = 6;
	for(int i = 0; i <= 6; i++) {
		if(i != 2) {
			Gun newGun = new Gun(i);
			guns.add(newGun);
		}
	}
}

}
