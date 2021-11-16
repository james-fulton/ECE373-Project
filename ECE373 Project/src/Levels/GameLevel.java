package Levels;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import java.lang.Math;

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
	private double upperX;
	private double upperY;
	private String playerName;
	private ArrayList<Zombie> zombies;
	private ArrayList<Bullet> bullets;
	private int maxZombies;
	private int Speed = 3; //number of pixels/tick each sprite falls, should we adjust with difficulty?
	private boolean isPaused, gameOver;
	
	
	public GameLevel(Game game) {
		super(game);
		playerName = "None";
		player = new Player();
		gameOver = false;
		zombies = new ArrayList<Zombie>();
		bullets = new ArrayList<Bullet>();
		difficultySet();
		generateLevel();
	}
	
	public GameLevel(Game game, String name) {
		super(game, name);
		player = new Player();
		zombies = new ArrayList<Zombie>();
		bullets = new ArrayList<Bullet>();
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
		JOptionPane.showMessageDialog(myPanel, "<HTML><center>Game over<BR> Player score: "+player.getPoints()+"<HTML><center>");
		playerName = JOptionPane.showInputDialog("Enter Player Name: ", "Hall of fame");
		game.getFrame().setVisible(true);
		HighScore hs1 = new HighScore(playerName, player.getPoints());
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
		double xcoord;
		double ycoord;
		upperX = game.getFrameX();
		upperY = game.getFrameY();
		int upperXZ = game.getFrameX();
		int upperYZ = game.getFrameY();
		
		for(int i=0; i<maxZombies;i++) {
		xcoord = rand.nextInt(upperXZ)+2;
		ycoord = rand.nextInt(upperYZ)+2;
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
		int upperXZ = game.getFrameX();
		
		double xcoord, ycoord;
		while(zombies.size() < maxZombies) {
			//xcoord = rand.nextInt(upperX)+2;
			//ycoord = rand.nextInt(upperY)+2;
			//FIXME need to instantiate a specific obstacle, so add method here to randomize which obstacle is instantiated
			Zombie tempZombie = new Zombie(game.getDifficulty());
			//Point2D p1 = new Point2D.Double(xcoord,ycoord);
			FieldPoint p1 = new FieldPoint(200.0, 200.0, 90.0);
			tempZombie.setLocation(p1);
			zombies.add(tempZombie);
		}
	}
	
	private void increaseMaxZombies(int addAmount) {
		maxZombies = maxZombies + addAmount;
	}

	
	private void updatePositions() {
		FieldPoint tempPT = new FieldPoint();
		
		//Zombies
		Iterator<Zombie> itr = zombies.iterator();
		while (itr.hasNext()) {
			Zombie ot = itr.next();
		    ot.findTarget(player.getLocation());
		    tempPT = ot.getLocation();
			if( newLocation(tempPT, ot.getSpeed()) == 0 ) {
				ot.setLocation(tempPT);
			}
		}
		
		
		//Bullets
		Iterator<Bullet> itr3 = bullets.iterator();
		while (itr3.hasNext()) {
			Bullet lt = itr3.next();
			tempPT = lt.getLocation();
			if( newLocation(tempPT, lt.getSpeed()) == 0 ) { lt.setLocation(tempPT); }
			else { itr3.remove(); }
		}
	}
	
	//1 = left, 2 = top, 3 = right, 4 = bottom.
	//5 = top/left, 6 = top/right, 7 = bottom/left, 8 = bottom/right
	//0 = in bounds
	private int outOfBounds(double x, double y) {
		int state = 0;
		if(x < 0) { state = 1; }
		else if(x > upperX) { state = 3; }
		
		if(y < 0) {
			if(state == 1) { return 5; }
			if(state == 3) { return 6; }
			return 2;
		}
		else if(y > upperY) {
			if(state == 1) { return 7; }
			if(state == 3) { return 8; }
			return 4;
		}
		return 0;
	}
	
	private int outOfBounds(FieldPoint temp) {
		return outOfBounds(temp.getX(), temp.getY());
	}
	
	//return spec: refer to outOfBounds
	private int newLocation(FieldPoint old, int speed) {
		double angle = old.getAngleView();
		angle = Math.toRadians(angle);
		double newX, newY;
		int state;
		
		newX = speed * Math.cos(angle) + old.getX();
		newY =  speed * Math.sin(angle) + old.getY();
		state = outOfBounds(newX, newY);

		
		if(state == 0) {
			old.setX(newX);
			old.setY(newY);
		}
		return state;
	}
	
	
	private void detectHits() {
		FieldPoint Playercoord1 = new FieldPoint();
		Playercoord1 = player.getLocation();
		FieldPoint Playercoord2 = new FieldPoint();
		Playercoord2.setLocation(Playercoord1.getX()+40, Playercoord1.getY()+18);
		Iterator<Zombie> itr = zombies.iterator();
		while (itr.hasNext()) {
			Zombie ot = itr.next();
			if(ot.getLocation().getX()>= Playercoord1.getX() && ot.getLocation().getY()>=Playercoord1.getY() && ot.getLocation().getX()<= Playercoord2.getX() && ot.getLocation().getY()<=Playercoord2.getY()) {
				//tests top left corner
				player.setCollision(true);
		}
			else if(ot.getLocation().getX()+20>= Playercoord1.getX() && ot.getLocation().getY()>=Playercoord1.getY() && ot.getLocation().getX()+20<= Playercoord2.getX() && ot.getLocation().getY()<=Playercoord2.getY()) {
				//tests top right corner
				player.setCollision(true);
		}
			else if(ot.getLocation().getX()>= Playercoord1.getX() && ot.getLocation().getY()+20>=Playercoord1.getY() && ot.getLocation().getX()<= Playercoord2.getX() && ot.getLocation().getY()+20<=Playercoord2.getY()) {
				//tests bottom left corner
				player.setCollision(true);
		}
			else if(ot.getLocation().getX()+20>= Playercoord1.getX() && ot.getLocation().getY()+20>=Playercoord1.getY() && ot.getLocation().getX()+20<= Playercoord2.getX() && ot.getLocation().getY()+20<=Playercoord2.getY()) {
				//tests bottom right corner
				player.setCollision(true);
		}
			
			Iterator<Bullet> itr3 = bullets.iterator();
			while (itr3.hasNext()) {
			Bullet lt = itr3.next();
			if(ot.getLocation().getX() <= lt.getLocation().getX() && ot.getLocation().getX()+20 >=lt.getLocation().getX() && ot.getLocation().getY() <= lt.getLocation().getY() && ot.getLocation().getY()+20 >=lt.getLocation().getY()) {
				
				ot.setHealth(ot.getHealth()-lt.getDamage());
				itr3.remove();
				if(ot.getHealth() <=0) {
					player.setPoints(ot.getPoints());
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
		FieldPoint bulletloc = new FieldPoint();
		bulletloc.setLocation(xloc, yloc);
		newBullet.setLocation(bulletloc);
		newBullet.setDamage(player.getBulletDamage());
		bullets.add(newBullet);
	}
	
	}


