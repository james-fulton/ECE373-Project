package Gameplay;

import java.util.ArrayList;

public class Player extends Entity {

//Variables
private int lives;
private boolean bulletHit;
//private Integer bulletDamage;
private ArrayList<Gun> guns;
private int selectedGun;
private double lookAngle; //different from move angle in FieldPoint
private boolean use;
private boolean gunFired;
private boolean reload;
private boolean sprint;

private boolean bulletTimer;

//Constructors
public Player() {
	super();
	this.lives = 3;
	this.speed = 3.0;
	this.points = 0;
	bulletHit = false;
	gunFired = false;
	bulletTimer = false;
	reload = false;
	sprint = false;
	use = false;
	lookAngle = 0.0;
	this.guns = new ArrayList<Gun>();
	Gun firstGun = new Gun(6);
	//firstGun.sf_use_ignoreammo(1);
	Gun secondGun = new Gun(0);
	//secondGun.sf_use_ignoreammo(1);
	guns.add(firstGun);
	guns.add(secondGun);
	selectedGun = 0;
	location.setLocation(100.0, 100.0);
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

public boolean selectGun(int index) {
	if(index > guns.size()-1 || index == selectedGun || index < 0) {
		return false;
	}
	selectedGun = index;
	return true;
}

public String getGunName() {
	return guns.get(selectedGun).getName();
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

public void setGunFired(boolean gunFired) {
	this.gunFired = gunFired;
}

public boolean reload() {
	bulletTimer = false;
	gunFired = false;
	reload = false;
	return guns.get(selectedGun).reload();
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
	if(guns.size() == 2) {
		removeGun(guns.get(selectedGun));
	}
	guns.add(newGun);
	selectedGun = 1;
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

}
