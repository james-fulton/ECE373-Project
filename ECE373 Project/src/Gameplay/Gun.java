package Gameplay;

import java.util.ArrayList;

public class Gun {
	//Variables
	private ArrayList<Bullet> bullets;
	private int maxBullets;
	private double rateOfFire; //Bullets per second
	private int maxMags;
	private int maxMagCapacity;
	private int damagePerBullet;
	private int bulletSpeed;
	private String name;
	private int gunType;
	
	private int currentMag;
	private int currentBullets;
	private int maxCollisions;
	private int speedReductionPerCollision;
	
	private boolean uniAmmo;
	
	private int audioFullReloadIndex;
	private int audioHalfReloadIndex;
	private int audioEmptyCaseIndex;
	private int audioShootIndex;
	private int audioListIndex;
	private int audioDryIndex;
	
	//Constructors
	public Gun() {
		this(-1, -1, -1, 1, 50, "Default");
		uniAmmo = true;
	}
	
	public Gun(int maxBullets, int maxMags, int maxMagCap, double rOF, int damagePerBullet, String name) {
		this.maxBullets = maxBullets;
		this.maxMags = maxMags;
		this.maxMagCapacity = maxMagCap;
		this.rateOfFire = rOF;
		this.damagePerBullet = damagePerBullet;
		this.name = name;
		bullets = new ArrayList<Bullet>();
		uniAmmo = false;
		
		for(int i = 0; i < maxBullets; i++) {
			Bullet temp = new Bullet(damagePerBullet);
			bullets.add(temp);
		}
		currentBullets = maxBullets;
		currentMag = maxMagCap;
		
		audioFullReloadIndex = 0;
		audioHalfReloadIndex = 0;
		audioEmptyCaseIndex = 0;
		audioShootIndex = 0;
		audioListIndex = 3;
		audioDryIndex = 0;
		gunType = -1;
		bulletSpeed = 10;
		maxCollisions = 1;
		speedReductionPerCollision = 1;
	}
	
	public Gun(int gunType) {
		//rate of fire is in rounds per minute
		this.gunType = gunType;
		maxBullets = -1;
		
		//Colt M1911
		if(gunType == 0) {
			maxBullets = 40;
			maxMags = 5;
			maxMagCapacity = 8;
			rateOfFire = -1;
			damagePerBullet = 20;
			bulletSpeed = 15;
			name = "M1911";
			maxCollisions = 1;
			speedReductionPerCollision = 5;
			
			audioFullReloadIndex = 11;
			audioHalfReloadIndex = 12;
			audioEmptyCaseIndex = 30;
			audioShootIndex = 10;
			audioListIndex = 3;
			audioDryIndex = 6;
		}
		
		//M1 Garand
		else if(gunType == 1) {
			maxBullets = 128;
			maxMagCapacity = 8;
			maxMags = maxBullets/maxMagCapacity;
			rateOfFire = -1;
			damagePerBullet = 75;
			bulletSpeed = 22;
			name = "M1 Garand";
			maxCollisions = 4;
			speedReductionPerCollision = 2;
			
			audioFullReloadIndex = 14;
			audioHalfReloadIndex = 15;
			audioEmptyCaseIndex = 28;
			audioShootIndex = 13;
			audioListIndex = 3;
			audioDryIndex = 9;
		}
		
		//MP40
		else if(gunType == 2) {
			maxBullets = 192;
			maxMagCapacity = 32;
			maxMags = maxBullets/maxMagCapacity;
			rateOfFire = 500;
			damagePerBullet = 25;
			bulletSpeed = 19;
			name = "MP40";
			maxCollisions = 3;
			speedReductionPerCollision = 2;
			
			audioFullReloadIndex = 36;
			audioHalfReloadIndex = 37;
			audioEmptyCaseIndex = 31;
			audioShootIndex = 35;
			audioListIndex = 3;
			audioDryIndex = 7;
		}
		
		//Thompson
		else if(gunType == 3) {
			maxBullets = 200;
			maxMagCapacity = 20;
			maxMags = maxBullets/maxMagCapacity;
			rateOfFire = 655;
			damagePerBullet = 30;
			bulletSpeed = 17;
			name = "M1A1 Thompson";
			maxCollisions = 3;
			speedReductionPerCollision = 2;
			
			audioFullReloadIndex = 17;
			audioHalfReloadIndex = 18;
			audioEmptyCaseIndex = 30;
			audioShootIndex = 16;
			audioListIndex = 3;
			audioDryIndex = 7;
		}
		
		//Double Barrel Shotgun
		else if(gunType == 4) {
			maxBullets = 60;
			maxMagCapacity = 2;
			maxMags = maxBullets/maxMagCapacity;
			rateOfFire = -1;
			damagePerBullet = 175;
			bulletSpeed = 16;
			name = "Double Barrel Shotgun";
			maxCollisions = 10;
			speedReductionPerCollision = 0;
			
			audioFullReloadIndex = 20;
			audioHalfReloadIndex = 21;
			audioEmptyCaseIndex = 29;
			audioShootIndex = 19;
			audioListIndex = 3;
			audioDryIndex = 8;
		}
		
		//Mauser K98k
		else if(gunType == 5) {
			maxBullets = 50;
			maxMagCapacity = 5;
			maxMags = maxBullets/maxMagCapacity;
			rateOfFire = -1;
			damagePerBullet = 100;
			bulletSpeed = 23;
			name = "K98k Mauser";
			maxCollisions = 5;
			speedReductionPerCollision = 2;
			
			audioFullReloadIndex = 23;
			audioHalfReloadIndex = 24;
			audioEmptyCaseIndex = 28;
			audioShootIndex = 22;
			audioListIndex = 3;
			audioDryIndex = 9;
		}
		
		//MG42
		else if(gunType == 6) {
			maxBullets = 500;
			maxMagCapacity = 125;
			maxMags = maxBullets/maxMagCapacity;
			rateOfFire = 1550;
			damagePerBullet = 75;
			bulletSpeed = 21;
			name = "MG42";
			maxCollisions = 3;
			speedReductionPerCollision = 2;
			
			audioFullReloadIndex = 26;
			audioHalfReloadIndex = 27;
			audioEmptyCaseIndex = 28;
			audioShootIndex = 25;
			audioListIndex = 3;
			audioDryIndex = 9;
		}
		
		bullets = new ArrayList<Bullet>();
		uniAmmo = false;
		
		for(int i = 0; i < maxBullets; i++) {
			Bullet temp = new Bullet(damagePerBullet, bulletSpeed, maxCollisions, speedReductionPerCollision);
			bullets.add(temp);
		}
		currentBullets = maxBullets;
		currentMag = maxMagCapacity;
	}

	
	
	//Setters & Getters
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public int getMaxBullets() {
		return maxBullets;
	}

	public void setMaxBullets(int maxBullets) {
		this.maxBullets = maxBullets;
	}

	public double getRateOfFire() {
		return rateOfFire;
	}

	public void setRateOfFire(int rateOfFire) {
		this.rateOfFire = rateOfFire;
	}
	
	public int getBulletSpeed() {
		return bulletSpeed;
	}

	public int getMaxMags() {
		return maxMags;
	}

	public boolean setMaxMags(int maxMags) {
		if(maxBullets % maxMags != 0) { return false; }
		maxMagCapacity = maxBullets/maxMags;
		this.maxMags = maxMags;
		return true;
	}

	public int getMaxMagCapacity() {
		return maxMagCapacity;
	}

	public boolean setMaxMagCapacity(int maxMagCapacity) {
		if(maxBullets % maxMagCapacity != 0) { return false; }
		maxMags = maxBullets/maxMagCapacity;
		this.maxMagCapacity = maxMagCapacity;
		return true;
	}
	
	public int getGunType() {
		return gunType;
	}

	public int getDamage() {
		return damagePerBullet;
	}

	public void setDamage(int damagePerBullet) {
		this.damagePerBullet = damagePerBullet;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public int getAudioFullReloadIndex() {
		return audioFullReloadIndex;
	}
	public int getAudioHalfReloadIndex() {
		return audioHalfReloadIndex;
	}
	public int getAudioEmptyCaseIndex() {
		return audioEmptyCaseIndex;
	}
	public int getAudioShootIndex() {
		return audioShootIndex;
	}
	public int getAudioListIndex() {
		return audioListIndex;
	}
	public int getAudioDryIndex() {
		return audioDryIndex;
	}
	
	public int getCurrentMag(){
		return currentMag;
	}
	public int getCurrentBullets() {
		return bullets.size();
	}
	public boolean fullMag() {
		return maxMagCapacity == currentMag;
	}
	
	//false = empty mag
	public Bullet shootGun() {
		Bullet temp;
		if(uniAmmo) {
			temp = new Bullet(damagePerBullet + 100, 20, 1, 1);
			return temp;
		}
		
		if(currentMag == 0 || bullets.size() == 0) { return null; }
		currentMag--;
		temp = bullets.get(0);
		bullets.remove(0);
		currentBullets = bullets.size();
		return temp;
	}
	//return false = no ammo
	public boolean reload() {
		if(currentBullets == 0 || uniAmmo || fullMag()) {
			return false;
		}
		
		if(currentBullets / maxMagCapacity == 0) {
			currentMag = currentBullets;
			currentBullets = 0;
		}
		else {
			currentMag = maxMagCapacity;
			currentBullets -= maxMagCapacity;
		}
		return true;
	}
	
	public void maxAmmo() {
		currentBullets = maxBullets;
		int refilMax = currentBullets - (maxMagCapacity - currentMag);
		for(int i = 0; i < refilMax; i++) {
			Bullet temp = new Bullet(damagePerBullet);
			bullets.add(temp);
		}
	}
	
	public void sf_use_ignoreammo(int set) {
		if(set == 0) { uniAmmo = false; }
		else {uniAmmo = true; }
	}
	
	public void clearBullets() {
		bullets.clear();
	}
}
