package Levels;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Core.Game;
import Core.HighScore;
import Gameplay.Bullet;
import Gameplay.Player;
import Gameplay.Zombie;



public class GameLevel extends Level{

	private Player player;
	private String playerName;
	private ArrayList<Zombie> zombies;
	private ArrayList<Bullet> bullets;
	private int maxZombies;
	private int Speed = 3; //number of pixels/tick each sprite falls, should we adjust with difficulty?
	private boolean isPaused, gameOver;
	
	
	public GameLevel(Game game) {
		super(game);
		playerName = "None";
		gameOver = false;
		zombies = new ArrayList<Zombie>();
		bullets = new ArrayList<Bullet>();
		player = new Player();
		difficultySet();
		generateLevel();
	}
	
	public GameLevel(Game game, String name) {
		super(game, name);
		zombies = new ArrayList<Zombie>();
		bullets = new ArrayList<Bullet>();
		player = new Player();
		difficultySet();
		generateLevel();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public ArrayList<Zombie> getZombies(){
		return zombies;
	}
	
	
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}
	
	public void exitGame() {
		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel ("Game over!"));
		JOptionPane.showMessageDialog(myPanel, "<HTML><center>Game over<BR> Player score: "+player.getTotalPoints()+"<HTML><center>");
		playerName = JOptionPane.showInputDialog("Enter Player Name: ", "Hall of fame");
		game.getFrame().setVisible(true);
		HighScore hs1 = new HighScore(playerName, player.getTotalPoints());
		game.getHighScores().add(hs1);
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void tick(float deltaTime) {
		//discuss implementation
		
		if(gameOver == false) {
			updatePositions();
			generateNewZombies();
			detectHits();
			if (player.getLives() == 0) {
				gameOver = true;
				exitGame();
			}
		}
		
		
		
		//can also implement methods increaseMaxObstacles and decreaseMaxPowerups here, if we choose
	}
	
	public void difficultySet() {
		//adjusting maxObstacles and maxPowerups based on game difficulty
				//assuming 1 is easy, 2 is regular, 3 is hard, defaulting to easy
				switch(game.getDifficulty()) {
				case 1:
					maxZombies = 5;
					break;
					
				case 2:
					maxZombies = 10;
					break;
					
				case 3:
					maxZombies = 15;
					break;
					
				default:
					maxZombies = 5;
					break;
				}	
	}
	
	public void generateLevel() {
		//This method should generate all the obstacles and powerups that are on the screen when the game starts, and then generateNewObstacles and generateNewPowerups should be called throughout play to continue generating sprites
		Random rand = new Random();
		int upperX = game.getFrameX();
		int upperY = game.getFrameY();
		double xcoord;
		double ycoord;
		
		for(int i=0; i<maxZombies;i++) {
		xcoord = rand.nextInt(upperX)+1;
		ycoord = rand.nextInt(upperY)+1;
		//FIXME need to instantiate a specific obstacle, so add method here to randomize which obstacle is instantiated
		Zombie tempZombie = new Zombie(game.getDifficulty());
		FieldPoint p1 = new FieldPoint(xcoord,ycoord, 90.0);
		tempZombie.setLocation(p1);
		zombies.add(tempZombie);
	    }
		
	}
	
	private void generateNewZombies() {
		//similar to generate level, but The obstacles will appear ion top of the screen as the player moves up and a previous obstacle disappears, so ycoord is always the upper pixel of the window and x is randomized
		Random rand = new Random();
		int upperX = game.getFrameX();
		//double ycoord = game.getFrameY();
		double xcoord;
		while(zombies.size() < maxZombies) {
			xcoord = rand.nextInt(upperX)+1;
			//FIXME need to instantiate a specific obstacle, so add method here to randomize which obstacle is instantiated
			Zombie tempZombie = new Zombie(game.getDifficulty());
			//Point2D p1 = new Point2D.Double(xcoord,ycoord);
			FieldPoint p1 = new FieldPoint(xcoord, 0.0, 90.0);
			tempZombie.setLocation(p1);
			zombies.add(tempZombie);
		}
	}
	
	private void increaseMaxZombies(int addAmount) {
		maxZombies = maxZombies + addAmount;
	}

	
	private void updatePositions() {
		FieldPoint tempPT = new FieldPoint();
		double newY, xStore;
		Iterator<Zombie> itr = zombies.iterator();
		while (itr.hasNext()) {
			Zombie ot = itr.next();
			tempPT = ot.getLocation();
			xStore = tempPT.getX();
			newY = tempPT.getY() + Speed;
			//if obstacle goes to the bottom of screen, it must be removed from the array list
			if(newY >= game.getFrameY()) {
				itr.remove();
			}
			else {
				tempPT.setLocation(xStore, newY);
				ot.setLocation(tempPT);
			}
		}
		//for bullets but in the opposite direction.
		Iterator<Bullet> itr3 = bullets.iterator();
		while (itr3.hasNext()) {
			Bullet lt = itr3.next();
			tempPT = lt.getLocation();
			xStore = tempPT.getX();
			newY = tempPT.getY() - Speed;
			if(newY <= 0) {
				itr3.remove();
			}
			else {
				tempPT.setLocation(xStore, newY);
				lt.setLocation(tempPT);
			}
		}
	}	
	
	private void detectHits() {
		FieldPoint Playercoord1 = new FieldPoint();
		Playercoord1 = player.getLocation();
		FieldPoint Playercoord2 = new FieldPoint();
		Playercoord2.setLocation(Playercoord1.getX()+40, Playercoord1.getY()+18);
		Iterator<Zombie> itr = zombies.iterator();
		//test if each of 4 corners is colliding with ship, if at least one is colliding then detect collision
		while (itr.hasNext()) {
			Zombie ot = itr.next();
			if(ot.getLocation().getX()>= Playercoord1.getX() && ot.getLocation().getY()>=Playercoord1.getY() && ot.getLocation().getX()<= Playercoord2.getX() && ot.getLocation().getY()<=Playercoord2.getY()) {
				//tests if top left corner of obstacle is in ship's hitbox
				player.setCollision(true);
				itr.remove();
		}
			else if(ot.getLocation().getX()+20>= Playercoord1.getX() && ot.getLocation().getY()>=Playercoord1.getY() && ot.getLocation().getX()+20<= Playercoord2.getX() && ot.getLocation().getY()<=Playercoord2.getY()) {
				//tests if top right corner of obstacle is in ship's hitbox
				player.setCollision(true);
				itr.remove();
		}
			else if(ot.getLocation().getX()>= Playercoord1.getX() && ot.getLocation().getY()+20>=Playercoord1.getY() && ot.getLocation().getX()<= Playercoord2.getX() && ot.getLocation().getY()+20<=Playercoord2.getY()) {
				//tests if bottom left corner of obstacle is in ship's hitbox
				player.setCollision(true);
				itr.remove();
		}
			else if(ot.getLocation().getX()+20>= Playercoord1.getX() && ot.getLocation().getY()+20>=Playercoord1.getY() && ot.getLocation().getX()+20<= Playercoord2.getX() && ot.getLocation().getY()+20<=Playercoord2.getY()) {
				//tests if bottom right corner of obstacle is in ship's hitbox
				player.setCollision(true);
				itr.remove();
		}
			//then, check collisions between laser and debris
			Iterator<Bullet> itr3 = bullets.iterator();
			while (itr3.hasNext()) {
			Bullet lt = itr3.next();
			if(ot.getLocation().getX() <= lt.getLocation().getX() && ot.getLocation().getX()+20 >=lt.getLocation().getX() && ot.getLocation().getY() <= lt.getLocation().getY() && ot.getLocation().getY()+20 >=lt.getLocation().getY()) {
				//tests if laser is on the line from bottom left corner of obstacle to bottom right
				ot.setZombieHealth(ot.getZombieHealth()-lt.getDamage());
				itr3.remove();
				if(ot.getZombieHealth() <=0) {
					player.addPoints(ot.getPoints());
					itr.remove();
				}
			}
			}
			
		}
	}
	
	public void shootBullet() {
		Bullet newBullet = new Bullet();
		Double xloc = player.getLocation().getX() + 18;
		Double yloc = player.getLocation().getY();
		FieldPoint laserloc = new FieldPoint();
		laserloc.setLocation(xloc, yloc);
		newBullet.setLocation(laserloc);
		newBullet.setDamage(player.getBulletDamage());
		bullets.add(newBullet);
	}
	
	}


