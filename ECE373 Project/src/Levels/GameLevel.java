package Levels;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.Math;
import java.time.Clock;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Core.AudioHandler;
import Core.Game;
import Core.HighScore;
import Gameplay.Bullet;
import Gameplay.Gun;
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
	private boolean isPaused, gameOver;
	private int round, zombiesKilled;
	private double difficulty;
	
	//Audio
	private Clip reloadAudio;
	private Clip fireAudio;
	private long timeBetweenBullet, timeAtNextBullet;
	private TimerTask reloadTimer;
	private TimerTask bulletTimer;
	private AudioHandler audioSource;
	private ArrayList<ArrayList> audioClips;
	private ArrayList<ArrayList> audioLengths;
	private Timer auxTimer;
	
	//Clock
	private Clock clock;
	
	
	//Constructors
	public GameLevel(Game game, String name) {
		super(game, name);
		round = 1;
		zombiesKilled = 0;
		upperX = game.getFrameX();
		upperY = game.getFrameY();     
		player = new Player();
		timeBetweenBullet = 0;
		timeAtNextBullet = 0;
		zombies = new ArrayList<Zombie>();
		bullets = new ArrayList<Bullet>();
		difficultySet();
		generateLevel();
		
		audioSource = new AudioHandler();
		audioClips = audioSource.getAudioClips();
		audioLengths = audioSource.getAudioLengths();
		auxTimer = new Timer();
		reloadAudio = null;
		clock = Clock.systemDefaultZone();
		setupNewGun();
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
	
	public int getRound() {
		return round;
	}
	
	public void exitGame() {
		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel ("Game over!"));
		JOptionPane.showMessageDialog(myPanel, "<HTML><center>Game over<BR> Player score: "+player.getPoints()+"<HTML><center>");
		playerName = JOptionPane.showInputDialog("Enter Player Name: ", "Hall of fame");
		game.getFrame().setLocationRelativeTo(null);
		game.getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
			generateZombies();
			detectHits();
			
			if(zombiesKilled >= maxZombies) {
				round++;
				increaseDifficulty(0.1);
				if(round % 2 == 0) {
					if(maxZombies < 24) {
						maxZombies++;
					}
				}
				playRoundMusic();
				zombiesKilled = 0;
			}
			
			if(timeBetweenBullet != 0) {
				//System.out.println("Timeatnextbullet: " + timeAtNextBullet + " \\\\ " + clock.millis()  + " \\\\ " + timeBetweenBullet   );  
				if(clock.millis() >= timeAtNextBullet) {
					player.setGunFired(false); 
				}
			}
			
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
		difficulty = game.getDifficulty();
		
				switch((int)difficulty) {
				case 1:
					maxZombies = 5;
					break;
					
				case 2:
					maxZombies = 10;
					round = 10;
					break;
					
				case 3:
					maxZombies = 15;
					round = 20;
					break;
					
				default:
					maxZombies = 5;
					break;
				}	
	}
	
	private void increaseDifficulty(double value) {
		difficulty += value;
		game.setDifficulty(difficulty);
	}
	
	public void generateLevel() {
		//This method should generate all the obstacles and powerups that are on the screen when the game starts, and then generateNewObstacles and generateNewPowerups should be called throughout play to continue generating sprites
		Random rand = new Random();
		double xcoord;
		double ycoord;
		upperX = game.getFrameX();
		upperY = game.getFrameY();
		
		generateZombies();
		/*
		for(int i=0; i < maxZombies;i++) {
		xcoord = rand.nextInt(upperXZ)+2;
		ycoord = rand.nextInt(upperYZ)+2;
		//FIXME need to instantiate a specific obstacle, so add method here to randomize which obstacle is instantiated
		double randomSpeed = ThreadLocalRandom.current().nextDouble(game.getDifficulty()-0.5, game.getDifficulty() + 1);
		Zombie tempZombie = new Zombie(game.getDifficulty(), randomSpeed * 0.7);
		FieldPoint p1 = new FieldPoint(xcoord,ycoord, 90.0);
		tempZombie.setLocation(p1);
		zombies.add(tempZombie);
	    }
		*/
		
	}
	
	private void generateZombies() {
		double xcoord, ycoord, angle = 90.0;
		double boundaryOffset = 10;
		
		while(zombies.size() < maxZombies) {
			if(ThreadLocalRandom.current().nextInt(0, 1 + 1) == 1) {
				if(ThreadLocalRandom.current().nextInt(0, 1 + 1) == 1) { xcoord = 0; angle = 180.0; }
				else { xcoord = upperX - boundaryOffset; angle = 0.0; }
				ycoord = ThreadLocalRandom.current().nextDouble(0.0, upperY + 1.0);
			}
			else {
				if(ThreadLocalRandom.current().nextInt(0, 1 + 1) == 1) { ycoord = boundaryOffset; angle = 270.0; }
				else { ycoord = upperY - boundaryOffset; angle = 90.0; }
				xcoord = ThreadLocalRandom.current().nextDouble(0.0, upperX + 1.0);
			}
			
			double speedMultiplier = 1;
			if(round > 8) {
				if(ThreadLocalRandom.current().nextInt(0, 100 + 1) > 60) {
					speedMultiplier = 2;
				}
			}
			
			double randomSpeed = ThreadLocalRandom.current().nextDouble(speedMultiplier * difficulty-0.7, speedMultiplier * difficulty + 0.1 + 1);
			Zombie tempZombie = new Zombie(game.getDifficulty(), randomSpeed * 0.7);
			
			FieldPoint p1 = new FieldPoint(xcoord, ycoord, angle);
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
				
				//footStepSound
				if(!ot.getPlayingFootstep()) {
					int randomNum = ThreadLocalRandom.current().nextInt(0, 3 + 1);
					Clip clip = audioSource.getAudioClip(2, randomNum);
					ot.setFootstepLength(audioSource.getAudioLength(2, randomNum)); 
					clip.setFramePosition(0);
					clip.start();
					ot.setPlayingFootstep(true);
				}
				else if(!ot.getFootstepTimer()) {
					auxTimer.schedule(new TimerTask() {
						@Override
						public void run() { ot.setPlayingFootstep(false); ot.setFootstepTimer(false); }
						}, (long)(ot.getFootstepLength()*.8 * 1000 / (ot.getSpeed() * 1.4) ));
					ot.setFootstepTimer(true);
				}
				
				//ambient & taunt sound
				if(!ot.getPlayGrowl()) {
					if(ot.getLocation().getDistance(player.getLocation()) < 55) {
						int randomNum = ThreadLocalRandom.current().nextInt(48, 54 + 1);
						Clip clip = audioSource.getAudioClip(2, randomNum);
						ot.setGrowlLength(audioSource.getAudioLength(2, randomNum)); 
						clip.setFramePosition(0);
						clip.start();
						ot.setPlayGrowl(true);
					}
					else if(ot.getSpeed() > 1.8) {
						int randomNum = ThreadLocalRandom.current().nextInt(28, 42 + 1);
						Clip clip = audioSource.getAudioClip(2, randomNum);
						ot.setGrowlLength(audioSource.getAudioLength(2, randomNum)); 
						clip.setFramePosition(0);
						clip.start();
						ot.setPlayGrowl(true);
					}
					else {
						int randomNum = ThreadLocalRandom.current().nextInt(7, 27 + 1);
						Clip clip = audioSource.getAudioClip(2, randomNum);
						ot.setGrowlLength(audioSource.getAudioLength(2, randomNum)); 
						clip.setFramePosition(0);
						clip.start();
						ot.setPlayGrowl(true);
					}
				}
				else if(!ot.getGrowlTimer()) {
					double randomNum = ThreadLocalRandom.current().nextInt(2, 5 + 1);
					auxTimer.schedule(new TimerTask() {
						@Override
						public void run() { ot.setPlayGrowl(false); ot.setGrowlTimer(false); }
						}, (long)(ot.getGrowlLength() * randomNum * 0.8 * 1000 / (ot.getSpeed())));
					ot.setGrowlTimer(true);
				}
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
	private int newLocation(FieldPoint old, double speed) {
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
				player.addPoints(20);
				if(ot.getHealth() <=0) {
					player.addPoints(ot.getPoints());
					
					int randomNum = ThreadLocalRandom.current().nextInt(78, 88 + 1);
					Clip clip = audioSource.getAudioClip(2, randomNum); 
					clip.setFramePosition(0);
					clip.start();
					zombiesKilled++;
					
					try {
						itr.remove();
					}
					catch (IllegalStateException ex) {
						System.out.println("ERROR: Removing zombie -> detectHits");
						System.out.println("No error if current zombies = required zombies:");
						System.out.println("Current zombies: " + zombies.size() + "\nRequired zombies: " + (maxZombies - 1) + "\n");
					}

				}
			}
			}
			
		}
	}
	
	//audio control
	public void shootBullet() {
		if(!player.getGunFired()) {
			Gun playerGun = player.getCurrentGun();
			Bullet temp = playerGun.shootGun();
			
			if(temp == null) {
				if(playerGun.getCurrentBullets() == 0 || player.getSprint()) {
					
					//out of bullets, play empty gun sound
					Clip clip = audioSource.getAudioClip(playerGun.getAudioListIndex(), playerGun.getAudioDryIndex());
					clip.setFramePosition(0);
					clip.start();
					player.setGunFired(true);
					
					if(timeBetweenBullet != 0) {   
						timeAtNextBullet = clock.millis() + 750;     
					}
					
					if(!player.getSprint()) {
						if(player.getOtherGun(true)) { setupNewGun(); }
					}
					return;
				}
				else{ playerReload(); }
				return;
			}
			else {
				FieldPoint bulletLoc = new FieldPoint(player.getLocation().getX(), player.getLocation().getY(), player.getLookAngle());
				if(newLocation(bulletLoc, playerGun.getBulletSpeed()) == 0) {
					temp.setLocation(bulletLoc);
					bullets.add(temp);
					int tempTime = 0;
					if(fireAudio.isRunning() || fireAudio.isActive()) {
						fireAudio.stop();
						fireAudio.flush();
						try {
							Thread.sleep(1);
							tempTime = 4;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					fireAudio.setFramePosition(0);
					fireAudio.start();
					
					player.setGunFired(true);
					if(timeBetweenBullet != 0) {   
						timeAtNextBullet = clock.millis() + timeBetweenBullet - tempTime;
						System.out.println("Timeatnextbullet: " + timeAtNextBullet + " \\\\ " + clock.millis()  + " \\\\ " + timeBetweenBullet   );        
					}
				}
			}
		}
		else if(!player.getBulletTimer() && timeBetweenBullet != 0 && player.getCurrentBullets() != 0) {

			//bulletTimer = new TimerTask() {
				//@Override
				//public void run() { player.setGunFired(false); player.setBulletTimer(false); }
			//};
			//auxTimer.schedule(bulletTimer, (long)(6 / player.getGunROF()));
			//player.setBulletTimer(true);
		}
		else if(player.getCurrentBullets() == 0) {
			if(player.getOtherGun(true)) { setupNewGun(); }
		}
	}
	
	private void setupNewGun() {
		double rot = player.getGunROF();
		if(rot != -1.0) {
			timeBetweenBullet = (long)(45000 / player.getCurrentGun().getRateOfFire());
		}
		else {
			timeBetweenBullet = 0;
		}
		fireAudio = audioSource.getAudioClip(3, player.getCurrentGun().getAudioShootIndex());
	}     
	
	//audio and timer conifgurations
	private void playerReload() {

		Gun playerGun = player.getCurrentGun();
		if(player.getReload() || playerGun.fullMag() || playerGun.getCurrentBullets() == 0) { return; }
		
		int gunIndex = playerGun.getAudioFullReloadIndex();
		if(playerGun.getCurrentMag() > 0) {
			gunIndex = playerGun.getAudioHalfReloadIndex();
		}
		
		//play full reload sound, start timer to reload
		player.setReload(true);
		reloadAudio = audioSource.getAudioClip(playerGun.getAudioListIndex(), gunIndex);
		reloadAudio.setFramePosition(0);
		reloadAudio.start();
		
		reloadTimer = new TimerTask() {
			@Override
			public void run() {
				if(player.getBulletTimer()) {
					bulletTimer.cancel();
				}
				player.reload();
			}
		};
		
		auxTimer.schedule(reloadTimer, (long)(audioSource.getAudioLength(playerGun.getAudioListIndex(), gunIndex) * 1000 * 0.8));
	}
	
		//keyWASD = 0b0000; //move
    	//keyArrow = 0b0000; //look
		//keyAux = 0b00000; //interact; switchgun|shoot|use|run|reload
	public void updatePlayer(int keyWASD, int keyArrow, int keyAux) {
			double lookAngle = 0.0, moveAngle = 0.0;
			boolean move = false, look = false, shoot = false, use = false, sprint = false, reload = false, switchGun = false;
			//key 5 & 10 == do last pressed
			
			//interact
			if(keyAux == 1 || keyAux == 17) { reload = true; }
			else if(keyAux == 2 || keyAux == 18) { sprint = true; }
			else if(keyAux == 3 || keyAux == 19) { sprint = true; reload = true;}
			else if(keyAux == 4 || keyAux == 20) { use = true; }
			else if(keyAux == 5 || keyAux == 21) { use = true; reload = true; }
			else if(keyAux == 6 || keyAux == 22) { use = true; sprint = true; }
			else if(keyAux == 7 || keyAux == 23) { use = true; sprint = true; reload = true; }
			else if(keyAux == 8 || keyAux == 24) { shoot = true; }
			else if(keyAux == 9 || keyAux == 25) { shoot = true; reload = true; }
			else if(keyAux == 10 || keyAux == 26) { shoot = true; sprint = true; }
			else if(keyAux == 11 || keyAux == 27) { shoot = true; sprint = true; reload = true; }
			else if(keyAux == 12 || keyAux == 28) { use = true; shoot = true; }
			else if(keyAux == 13 || keyAux == 29) { use = true; shoot = true; reload = true; }
			else if(keyAux == 14 || keyAux == 30) { use = true; shoot = true; sprint = true; }
			else if(keyAux == 15 || keyAux == 31) { use = true; shoot = true; sprint = true; reload = true; }
			if(keyAux > 15) { switchGun = true; }
			
			player.setInteract(use);
			player.setSprint(sprint);
			
			//Reload
			if(sprint || switchGun) {
				reload = false;
				if(player.getReload()) {
					reloadTimer.cancel();
					reloadAudio.stop();
				}
				player.setReload(false);
			}
			else if(reload || player.getCurrentMag() == 0) { playerReload(); }
			
			//shoot
			if(shoot && !player.getReload() && !switchGun) {
				shootBullet();
			}
			else if(player.getGunROF() == -1.0 || player.getCurrentBullets() == 0) {
				player.setGunFired(false); 
			}
			
			//swtichGun
			if(switchGun) {
				if(player.getOtherGun(false)) {
					setupNewGun();
				}
			}
			
			
			//move
			if(keyWASD == 2 || keyWASD == 7) { moveAngle = 90.0; }
			else if(keyWASD == 3) { moveAngle = 45.0; }
			else if(keyWASD == 4 || keyWASD == 14) { moveAngle = 180.0; }  //keyWASD == 14 may not do anything
			else if(keyWASD == 6) { moveAngle = 135.0; }
			else if(keyWASD == 8) { moveAngle = 270.0; }
			else if(keyWASD == 9) { moveAngle = 315.0; }
			else if(keyWASD == 12) { moveAngle = 225.0; }
			else if(keyWASD == 13) { moveAngle = 270.0; }
			
			if(keyWASD > 0 && (keyWASD == 1 || keyWASD == 11 || moveAngle > 0.0)) { move = true; } //keyWASD == 11 may not do anything
			if(move) {
				FieldPoint nextPoint = player.getLocation();
				nextPoint.setAngleView(moveAngle);
				if(newLocation(nextPoint, player.getSpeed()) == 0) {
					player.setLocation(nextPoint);
					
					if(!player.getPlayingFootstep()) {
						if(sprint) {
							int randomNum = ThreadLocalRandom.current().nextInt(0, 5 + 1);
							Clip clip = audioSource.getAudioClip(1, randomNum);
							player.setFootstepLength(audioSource.getAudioLength(1, randomNum)); 
							clip.setFramePosition(0);
							clip.start();
							player.setFootstepTimer(false);
							player.setPlayingFootstep(true);;
						}
						else { //walk
							int randomNum = ThreadLocalRandom.current().nextInt(6, 11 + 1);
							Clip clip = audioSource.getAudioClip(1, randomNum);
							player.setFootstepLength(audioSource.getAudioLength(1, randomNum)); 
							clip.setFramePosition(0);
							clip.start();
							player.setFootstepTimer(false);
							player.setPlayingFootstep(true);
						}
					}
					else if(!player.getFootstepTimer()) {
						auxTimer.schedule(new TimerTask() {
							@Override
							public void run() { player.setPlayingFootstep(false); }
							}, (long)(player.getFootstepLength()/2.5 * 1000));
						player.setFootstepTimer(true);
					}
				}
			} 
			else if(keyWASD == 5 || keyWASD == 10) {
				FieldPoint nextPoint = player.getLocation();
				if(newLocation(nextPoint, player.getSpeed()) == 0) {
					player.setLocation(nextPoint);
					
					if(!player.getPlayingFootstep()) {
						if(sprint) {
							int randomNum = ThreadLocalRandom.current().nextInt(0, 5 + 1);
							Clip clip = audioSource.getAudioClip(1, randomNum);
							player.setFootstepLength(audioSource.getAudioLength(1, randomNum)); 
							clip.setFramePosition(0);
							clip.start();
							player.setFootstepTimer(false);
							player.setPlayingFootstep(true);
						}
						else { //walk
							int randomNum = ThreadLocalRandom.current().nextInt(6, 11 + 1);
							Clip clip = audioSource.getAudioClip(1, randomNum);
							player.setFootstepLength(audioSource.getAudioLength(1, randomNum)); 
							clip.setFramePosition(0);
							clip.start();
							player.setFootstepTimer(false);
							player.setPlayingFootstep(true);
						}
					}
					else if(!player.getFootstepTimer()) {
						auxTimer.schedule(new TimerTask() {
							@Override
							public void run() { player.setPlayingFootstep(false); }
							}, (long)(player.getFootstepLength()/2.5 * 1000));
						player.setFootstepTimer(true);
					}
				}
			}
			
			//look
			if(keyArrow == 2 || keyArrow == 7) { lookAngle = 90.0; }
			else if(keyArrow == 3) { lookAngle = 45.0; }
			else if(keyArrow == 4 || keyArrow == 14) { lookAngle = 180.0; }
			else if(keyArrow == 6) { lookAngle = 135.0; }
			else if(keyArrow == 8) { lookAngle = 270.0; }
			else if(keyArrow == 9) { lookAngle = 315.0; }
			else if(keyArrow == 12) { lookAngle = 225.0; }
			else if(keyArrow == 13) { lookAngle = 270.0; }
			if(keyArrow > 0 && (keyArrow == 1 || keyArrow == 11 || lookAngle > 0.0)) { look = true; }
			if(look) {
				player.setLookAngle(lookAngle);
				//update player model
			}
		}
	
	private void playRoundMusic() {
		
	}
	
	}


