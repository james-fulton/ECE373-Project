package Gameplay;

import java.util.ArrayList;

public class Player extends Entity {

//Variables
private int lives;
private boolean bulletHit;
//private Integer bulletDamage;
private ArrayList<Gun> guns;
private int selectedGun;

//Constructors
public Player() {
	super();
	this.lives = 3;
	this.points = 0;
	bulletHit = false;
	ArrayList<Gun> guns = new ArrayList<Gun>();
	Gun firstGun = new Gun(70, 10, 7, 1, 20, "M1911");
	guns.add(firstGun);
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
	//return guns.get(selectedGun).getDamage();
	return 10;
}


public void setCollision(boolean collision) { // set Collision w/ debris
	this.collision = collision;
}

public void changeLives(int life) {
	this.lives = lives + life;
}

public int getLives() {
	return lives;
}

//Body

//public void setBulletDamage
//returns state for Game update; -1 is out of bounds or currently using
//-1 is no change, else value is index of new selected gun
public int selectGun(int value) {
	if(value > 0 && value <= guns.size()) {
		if(value != selectedGun) {
			selectedGun = value-1;
			return value-1;
		}
	}
	return -1;
}

public void addGun(Gun newGun) {
	guns.add(newGun);
}

public void removeGun(Gun newGun) {
	for(int i = 0; i < guns.size(); i++) {
		if(guns.get(i).getName() == newGun.getName()) {
			guns.remove(i);
			return;
		}
	}
}

}
