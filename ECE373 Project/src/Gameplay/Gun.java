package Gameplay;

import java.util.ArrayList;

public class Gun {
	//Variables
	private ArrayList<Bullet> bullets;
	private int maxBullets;
	private int rateOfFire; //Bullets per second
	private int maxMags;
	private int maxMagCapacity;
	private int damagePerBullet;
	private String name;
	
	//Constructors
	public Gun() {
		this(-1, -1, -1, 1, 50, "Default");
	}
	
	public Gun(int maxBullets, int maxMags, int maxMagCap, int rOF, int damagePerBullet, String name) {
		this.maxBullets = maxBullets;
		this.maxMags = maxMags;
		this.maxMagCapacity = maxMagCap;
		this.rateOfFire = rOF;
		this.damagePerBullet = damagePerBullet;
		this.name = name;
		bullets = new ArrayList<Bullet>();
		
		for(int i = 0; i < maxBullets; i++) {
			Bullet temp = new Bullet(damagePerBullet);
			bullets.add(temp);
		}
	}

	
	
	//Setters & Getters
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}

	public int getMaxBullets() {
		return maxBullets;
	}

	public void setMaxBullets(int maxBullets) {
		this.maxBullets = maxBullets;
	}

	public int getRateOfFire() {
		return rateOfFire;
	}

	public void setRateOfFire(int rateOfFire) {
		this.rateOfFire = rateOfFire;
	}

	public int getMaxMags() {
		return maxMags;
	}

	public void setMaxMags(int maxMags) {
		this.maxMags = maxMags;
	}

	public int getMaxMagCapacity() {
		return maxMagCapacity;
	}

	public void setMaxMagCapacity(int maxMagCapacity) {
		this.maxMagCapacity = maxMagCapacity;
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
}
