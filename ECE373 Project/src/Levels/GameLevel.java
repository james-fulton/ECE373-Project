package Levels;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.image.BufferedImage;
import java.lang.Math;
import java.time.Clock;

import javax.sound.sampled.Clip;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	private int maxZombies, zombiesSpawnOnRound, zombiesOnScreen;
	private final AtomicBoolean pause = new AtomicBoolean(false);
	private final AtomicBoolean changeGun = new AtomicBoolean(false);
	private boolean gameOver;
	private int round, zombiesKilled, totalZombies, maxZombiesOnScreen;
	private double difficulty;
	private boolean incrementRound;
	
	//Audio
	private Clip reloadAudio;
	private Clip fireAudio;
	private long timeBetweenBullet, timeAtNextBullet;
	private long timeAtNextPlayerDamage;
	private TimerTask reloadTimer;
	private TimerTask bulletTimer;
	private AudioHandler audioSource;
	private Timer auxTimer;
	
	//Clock
	private Clock clock;
	
	
	//Constructors
	public GameLevel(Game game, String name) {
		super(game, name);
		incrementRound = false;
		round = 1;
		zombiesKilled = 0;
		zombiesSpawnOnRound = 0;
		zombiesOnScreen = 0;
		totalZombies = 0;
		upperX = game.getFrameX();
		upperY = game.getFrameY();     
		player = new Player(upperX/2, upperY/2);
		timeBetweenBullet = 0;
		timeAtNextBullet = 0;
		zombies = new ArrayList<Zombie>();
		bullets = new ArrayList<Bullet>();
		difficultySet();
		generateLevel();
		
		audioSource = new AudioHandler();
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
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}
	
	public int getRound() {
		return round;
	}
	
	public void exitGame() {
		audioSource.getAudioClip(0, 0).setFramePosition(0);
		audioSource.getAudioClip(0, 0).stop();
		audioSource.getAudioClip(4, 0).setFramePosition(0);
		audioSource.getAudioClip(4, 0).start();
		
		JOptionPane gameOverScreen = new JOptionPane(JOptionPane.PLAIN_MESSAGE);
		gameOverScreen.setMessage("Round: " + round + "\nPoints: " + player.getPoints() + "\nKills: " + (totalZombies - zombies.size()));
		JDialog dialog = gameOverScreen.createDialog(null, "Game Over");
		
		auxTimer.schedule(new TimerTask() {
			@Override
			public void run() { if(dialog.isVisible()) { dialog.dispose(); }  }
			}, (long)(audioSource.getAudioLength(4, 0) * 1000));
		
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		
		//JOptionPane.showMessageDialog(null, "Round: " + round + "\nPoints: " + player.getPoints(), "Game Over", JOptionPane.PLAIN_MESSAGE);
		//playerName = JOptionPane.showInputDialog("Player Name: ", "Game Over");
		
		int position = game.isHighScore(round, player.getPoints(), totalZombies - zombies.size());
		if((position < 1 && game.getHighScores().size() < 5) || position > 0) {
		
			JTextField newName = new JTextField();
			Object[] message = { "High Scores:\n" + game.scoreToString(position, round, player.getPoints(), totalZombies - zombies.size()), "Name: ", newName };
			int option = JOptionPane.showConfirmDialog(null,  message, "Game Over", JOptionPane.OK_CANCEL_OPTION);
			if(option == JOptionPane.OK_OPTION) {
				HighScore hs1 = new HighScore(newName.getText(), round, player.getPoints(), totalZombies - zombies.size());
				if(game.getHighScores().size() < 5) {
					if(position == -1) { position++; }
					game.getHighScores().add(position, hs1);
				}
				else {
					game.getHighScores().add(position, hs1);
					game.getHighScores().remove(0);
				}
			}
		}
		
		audioSource.endAudio();
		zombies.clear();
		bullets.clear();
		player.clearGuns();
		
		//game.getFrame().setLocationRelativeTo(null);
		//game.getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//game.getFrame().setVisible(true);
	}
	
	public void pauseGame() {
		if(pause.get()) {
			audioSource.getAudioClip(0, 0).stop(); //pause ambient sound
			
			String[] buttons = { "Continue Game", "Quit", "Reset Game", "Options", "No" };
			
			//int option = JOptionPane.showConfirmDialog(null,  "Continue Game?", "Game Paused", JOptionPane.OK_CANCEL_OPTION);
			int option = JOptionPane.showOptionDialog(null, "Having Fun?", "Game Paused", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, buttons, null);
			
			switch(option) {
				case 0: resumeGame(); break;
				case 1: {
					int quit = JOptionPane.showConfirmDialog(null,  "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
					if(quit == JOptionPane.YES_OPTION) { gameOver = true; break; }
					else { resumeGame(); break; }
				}
				case 2: resetGame(); break;
				case 3: openOptions(); break;
				case 4: {
					String[] funnyButtons = { "Yes", "Yes" };
					int funnyOption = JOptionPane.showOptionDialog(null, "You are having fun", "Game Paused", 
							JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, funnyButtons, null);
					resumeGame();
					break;
				}
				default: resumeGame();
			}
		}
		else {
			resumeGame();
		}
	}
	
	public void openOptions() {
		String[] buttons = { "Go Back", "Show Controls", "Make Game Easier", "Terminal" };
		
		//int option = JOptionPane.showConfirmDialog(null,  "Continue Game?", "Game Paused", JOptionPane.OK_CANCEL_OPTION);
		int option = JOptionPane.showOptionDialog(null, "Game Options", "Game Paused", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, buttons, null);
		
		if(option == 0) {
			pauseGame();
		}
		else if(option == 1) {
			JOptionPane.showMessageDialog(null, "Pause: Esc \nMove: WASD \nLook: Arrow Keys \nShoot: Space bar \nSwitch Weapon: 1 \nReload: R \nUse: F \nSprint: Shift", "Game Controls", JOptionPane.INFORMATION_MESSAGE);
			openOptions();
		}
		else if(option  == 2) {
			
			double previous = difficulty;
			if(difficulty <= 1) {
				JOptionPane.showMessageDialog(null, "Cannot reduce difficulty, it is too low. Play Better", "Make Game Easier", JOptionPane.WARNING_MESSAGE);
				pauseGame();
				return;
			}
			if(difficulty > 1) { difficulty -= 0.1; }
			else if(difficulty > 2) { difficulty -= 1.0; }
			
			String[] diffButtons = { "Go Back", "Continue Game"};
			int difOption = JOptionPane.showOptionDialog(null, "Game difficulty reduced to " + difficulty + " from " + previous, "Make Game Easier", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, diffButtons, null);
			if(difOption == 0) { openOptions(); return; }
			else if(difOption == 1) { resumeGame(); return; }
		}
		else if(option == 3) { openTerminal(); return; }
		else { pauseGame(); return; }
	}
	
	private void openTerminal() {
		JTextField newCommand = new JTextField();
		Object[] message = { "Enter Command: " , newCommand, };
		
		int comOption = JOptionPane.showConfirmDialog(null,  message, "Terminal", JOptionPane.OK_CANCEL_OPTION);
		if(comOption == JOptionPane.OK_OPTION) {
			if(newCommand.getText().equals("sf_use_ignoreammo 0")) { player.sf_use_ignoreammo(0); resumeGame(); return; }
			else if(newCommand.getText().equals("sf_use_ignoreammo 1")) { player.sf_use_ignoreammo(1); resumeGame(); return; }
			else if(newCommand.getText().equals("god")) { player.godMode(); resumeGame(); return; }
			else if(newCommand.getText().equals("setspeed 8.0")) { player.setSpeed(8.0); resumeGame(); return; }
			else if(newCommand.getText().equals("give all")) {player.giveAll(); setupNewGun(); resumeGame(); return; }
			else if(newCommand.getText().equals("help") || newCommand.getText().equals("-h") || newCommand.getText().equals("/h")) {
				JOptionPane.showMessageDialog(null, "Hint: sf_use_ignoreammo (int state)", "Terminal", JOptionPane.INFORMATION_MESSAGE);
				openTerminal();
				return;
			}
			else {
				JOptionPane.showMessageDialog(null, "Unknown command: " + newCommand.getText(), "Terminal", JOptionPane.WARNING_MESSAGE);
				openTerminal();
				return;
			}
		}
		else { openOptions(); return; }
	}
	
	public void resetGame() {
		String[] buttons = {"Go Back", "1: I'm to young to die", "2: Hurt me plenty", "3: You will not survive", "Resume Game"};
		
		//int option = JOptionPane.showConfirmDialog(null,  "Continue Game?", "Game Paused", JOptionPane.OK_CANCEL_OPTION);
		int diffOption = JOptionPane.showOptionDialog(null, "Select a difficulty", "Reset Game", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, buttons, null);
		if(diffOption == 0) { pauseGame(); return; }
		else if(diffOption == 4) { resumeGame(); return; }
		
		zombies.clear();
		bullets.clear();
		player.clearGuns();
		
		game.setDifficulty(diffOption);
		difficulty = diffOption;
		incrementRound = false;
		round = 1;
		zombiesKilled = 0;
		zombiesSpawnOnRound = 0;
		zombiesOnScreen = 0;
		totalZombies = 0;   
		player = new Player(upperX/2, upperY/2);
		timeBetweenBullet = 0;
		timeAtNextBullet = 0;
		difficultySet();
		generateLevel();
		
		reloadAudio = null;
		setupNewGun();
		resumeGame();
	}
	
	public void resumeGame() {
		audioSource.getAudioClip(0, 0).setFramePosition(0);
		audioSource.getAudioClip(0, 0).loop(-1);
		pause.set(false);
	}
	
	public AtomicBoolean isPaused() {
		return pause;
	}
	
	public AtomicBoolean changeGun() {
		return changeGun;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void tick(float deltaTime) {
		//discuss implementation
		
		if(!gameOver && !pause.get()) {
			if (player.getLives() == 0) {
				gameOver = true;
				return;
			}
			
			updatePositions();
			increasePlayerHealth();
			
			if(!incrementRound && zombiesSpawnOnRound < maxZombies) {
				generateZombies();
				//detectHits(); //done in gamelevelwindow
			}
			
			if(zombiesKilled >= maxZombies) {
				incrementRound = true;
				round++;
				increaseDifficulty(0.1);
				
				if(maxZombies < 24) { maxZombies++; }
					
					
				if(round % 7 == 0) {
					maxAmmo();
					if(maxZombiesOnScreen  < 15 && maxZombiesOnScreen <= maxZombies) {
						maxZombiesOnScreen += 1;
					}
				}
				playRoundMusic();
				zombiesKilled = 0;
				zombiesSpawnOnRound = 0;
			}
			
			//timers
			if(timeBetweenBullet != 0) {
				//System.out.println("Timeatnextbullet: " + timeAtNextBullet + " \\\\ " + clock.millis()  + " \\\\ " + timeBetweenBullet   );  
				if(clock.millis() >= timeAtNextBullet) {
					player.setGunFired(false); 
				}
			}
			if(clock.millis() >= timeAtNextPlayerDamage) { player.setHit(false); }
			
		}
		//can also implement methods increaseMaxObstacles and decreaseMaxPowerups here, if we choose
	}
	
	public void difficultySet() {
		//adjusting maxObstacles and maxPowerups based on game difficulty
		//assuming 1 is easy, 2 is regular, 3 is hard, defaulting to easy
		
				switch((int)game.getDifficulty()) {
				case 1:
					maxZombies = 5;
					maxZombiesOnScreen = 5;
					difficulty = 1;
					break;
					
				case 2:
					maxZombies = 10;
					maxZombiesOnScreen = 5;
					round = 10;
					difficulty = 2;
					break;
					
				case 3:
					maxZombies = 20;
					maxZombiesOnScreen = 10;
					round = 20;
					difficulty = 3;
					break;
					
				default:
					maxZombies = 5;
					maxZombiesOnScreen = 5;
					difficulty = 1;
					break;
				}	
	}
	
	private void increaseDifficulty(double value) {
		difficulty += value;
		if(round % 5 == 0) { game.setDifficulty(difficulty); }
	}
	
	public boolean incrementingRound() {
		return incrementRound;
	}
	
	public void generateLevel() {
		//This method should generate all the obstacles and powerups that are on the screen when the game starts, and then generateNewObstacles and generateNewPowerups should be called throughout play to continue generating sprite
		
		upperX = game.getFrameX();
		upperY = game.getFrameY();
		
		generateZombies();
	}
	
	private void generateZombies() {
		double xcoord, ycoord, angle = 90.0;
		double boundaryOffset = 10;
	
		while(zombiesOnScreen < maxZombiesOnScreen) {
			
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
				speedMultiplier += round/10 * difficulty;
				if(round < 15) {
					int chanceOfMethZombie = ThreadLocalRandom.current().nextInt(0, 100 + 1); //nazis actually used meth lol
					if(chanceOfMethZombie > 60 && (70+(round-8)) > chanceOfMethZombie) { //realistically should be 80% but game would be too hard haha
						speedMultiplier = 2 * difficulty;
					}
				}
				if(speedMultiplier > 4.75) {
					speedMultiplier = 4.75;
				}
			}
			else if(round < 3) {
				speedMultiplier += 0.8 * difficulty * round/10;
			}
			else if(round < 6) {
				speedMultiplier += 0.9 * difficulty * round/10;
			}
			
			double randomSpeed = ThreadLocalRandom.current().nextDouble(speedMultiplier-0.7, speedMultiplier);
			
			Zombie tempZombie = new Zombie(game.getDifficulty(), randomSpeed * 0.7, totalZombies);
			
			if(zombiesSpawnOnRound == 0) {
				tempZombie = new Zombie(game.getDifficulty(), 1, totalZombies);
			}
			
			
			FieldPoint p1 = new FieldPoint(xcoord, ycoord, angle);
			tempZombie.setLocation(p1);
			zombies.add(tempZombie);
			totalZombies++;
			zombiesSpawnOnRound++;
			zombiesOnScreen++;
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
			if( newLocation(tempPT, ot.getSpeed(), 10) == 0 ) {
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
				if(!ot.getPlayGrowl() || (!ot.getMissHit() && ot.getLocation().getDistance(player.getLocation()) < 40)) {
					if(!ot.getHit() && (!ot.getMissHit() && ot.getLocation().getDistance(player.getLocation()) < 40) && round < 15) {
						playZombieAttackSound(ot);
					}
					else if(ot.getLocation().getDistance(player.getLocation()) < 100 && round < 8) {
						int randomNum = ThreadLocalRandom.current().nextInt(48, 54 + 1);
						Clip clip = audioSource.getAudioClip(2, randomNum);
						ot.setGrowlLength(audioSource.getAudioLength(2, randomNum) / 3); 
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
					int randomNum = ThreadLocalRandom.current().nextInt(2, 5 + 1);
					auxTimer.schedule(new TimerTask() {
						@Override
						public void run() { ot.setPlayGrowl(false); ot.setGrowlTimer(false); ot.setMissHit(false); }
						}, (long)(ot.getGrowlLength() * randomNum * 0.8 * 1000 / (ot.getSpeed() )));
					ot.setGrowlTimer(true);
				}
			}
		}
		
		
		//Bullets
		Iterator<Bullet> itr3 = bullets.iterator();
		while (itr3.hasNext()) {
			Bullet lt = itr3.next();
			tempPT = lt.getLocation();
			if( newLocation(tempPT, lt.getSpeed(), 30) == 0 ) { lt.setLocation(tempPT); }
			else { itr3.remove(); }
		}
	}
	
	//1 = left, 2 = top, 3 = right, 4 = bottom.
	//5 = top/left, 6 = top/right, 7 = bottom/left, 8 = bottom/right
	//0 = in bounds
	private int outOfBounds(double x, double y, double offset) {
		int state = 0;
		if(x < 0 - offset) { state = 1; }
		else if(x > upperX + offset) { state = 3; }
		
		if(y < 0 - offset) {
			if(state == 1) { return 5; }
			if(state == 3) { return 6; }
			return 2;
		}
		else if(y > upperY + offset) {
			if(state == 1) { return 7; }
			if(state == 3) { return 8; }
			return 4;
		}
		return state;
	}
	
	private int outOfBounds(FieldPoint temp) {
		return outOfBounds(temp.getX(), temp.getY(), 0);
	}
	
	//return spec: refer to outOfBounds
	private int newLocation(FieldPoint old, double speed, double offset) {
		double angle = old.getAngleView();
		angle = Math.toRadians(angle);
		double newX, newY;
		int state;
		
		newX = speed * Math.cos(angle) + old.getX();
		newY =  speed * Math.sin(angle) + old.getY();
		state = outOfBounds(newX, newY, offset);

		
		if(state == 0) {
			old.setX(newX);
			old.setY(newY);
		}
		return state;
	}
	
	
	public void detectHits(BufferedImage playerImage, BufferedImage zombieImage, double bulletImageX, double bulletImageY) {
		//Temporary Objects & Iterators
		Zombie tempZombie;
		Bullet tempBullet;
		Iterator<Zombie> itrZombie = zombies.iterator();
		
		//Locations
		FieldPoint playerLoc = player.getLocation();
		FieldPoint zombieLoc, bulletLoc;
		
		//Boundaries
		double playerSizeX = playerImage.getWidth();
		double playerSizeY = playerImage.getHeight();
		double zombieSizeX = zombieImage.getWidth();
		double zombieSizeY = zombieImage.getHeight();
		double bulletImageDistance, zombieImageDistance, playerImageDistance;
		
		//Temp Variables
		double tempDistance;
		
		//find zombie & bullet conditions
		while (itrZombie.hasNext()){
			tempZombie = itrZombie.next();
			zombieLoc = tempZombie.getLocation();
			zombieImageDistance = zombieLoc.findImageDistance(playerLoc, zombieSizeX/2, zombieSizeY/2);
			playerImageDistance = playerLoc.findImageDistance(playerLoc, playerSizeX/2, playerSizeY/2);
			
			//find if zombie can attack player
			if(zombieLoc.getDistance(playerLoc) < zombieImageDistance + playerImageDistance - 5) {
				if(!tempZombie.getHit()) {
					if(player.zombieHit(tempZombie.getMeleeDamage())) {
						return;
					}
					tempZombie.hitPlayer(auxTimer);
					timeAtNextPlayerDamage = clock.millis() + 480;
					playZombieHitSound();
					playZombieAttackSound(null);
				}
			}
			
			Iterator<Bullet> itrBullet = bullets.iterator();
			//find bullet conditions
			boolean nextZombie = false;
			while(itrBullet.hasNext() && !nextZombie) {
				tempBullet = itrBullet.next();
				bulletLoc = tempBullet.getLocation();
				bulletImageDistance = tempBullet.findImageDistance(zombieLoc, bulletImageX/2, bulletImageY/2);
				zombieImageDistance = zombieLoc.findImageDistance(bulletLoc, zombieSizeX/2, zombieSizeY/2);
				tempDistance = bulletLoc.getDistance(zombieLoc);
				
				//find if bullet hits a zombie
				if(tempDistance < (bulletImageDistance + zombieImageDistance - 5) && tempBullet.findZombie(tempZombie.getIdentifer()) == -1) {
					player.addPoints(5);
					//if zombie is dead
					if(tempZombie.receivedDamage(tempBullet.getDamage())) {
						player.addPoints(tempZombie.getPoints());
						playZombieDeathSound();
						zombiesKilled++;
						zombiesOnScreen--;
						try { 
							itrZombie.remove(); 
							nextZombie = true;
						}
						catch (IllegalStateException ex) {
							System.out.println("ERROR: Removing zombie -> detectHits");
							System.out.println("No error if current zombies = required zombies:");
							System.out.println("Current zombies: " + zombies.size() + "\nRequired zombies: " + (maxZombies - 1) + "\n");
						}
					}
					
					//if bullet has no speed
					if(tempBullet.addCollision(tempZombie.getIdentifer(), auxTimer)) {
						try { itrBullet.remove(); }
						catch (IllegalStateException ex) { System.out.println("ERROR: Removing bullet -> detectHits"); }
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
				if(newLocation(bulletLoc, playerGun.getBulletSpeed(), 30) == 0) {
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
					//playBulletCase(playerGun.getAudioEmptyCaseIndex());
					
					player.setGunFired(true);
					if(timeBetweenBullet != 0) {   
						timeAtNextBullet = clock.millis() + timeBetweenBullet - tempTime;
						//System.out.println("Timeatnextbullet: " + timeAtNextBullet + " \\\\ " + clock.millis()  + " \\\\ " + timeBetweenBullet   );        
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
			if(changeGun.compareAndSet(true, switchGun)) {
				changeGun().set(false);
				if(player.getNextGun()) {
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
				if(newLocation(nextPoint, player.getSpeed(), 0) == 0) {
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
				if(newLocation(nextPoint, player.getSpeed(), 0) == 0) {
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
	

	
	public void maxAmmo() {
		player.maxAmmo();
	}
	
	private void increasePlayerHealth() {
		if(player.getHealth() < player.getMaxHealth() && !player.getHit()) {
			auxTimer.schedule(new TimerTask() {
				@Override
				public void run() { player.addHealth(10); }
				}, (long)(500));
		}
	}
	
	private void playBulletCase(int index) {
		auxTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				Clip clip = audioSource.getAudioClip(3, index); 
				if(clip.isRunning() || clip.isActive()) {
					clip.stop();
					clip.flush();
					try {
						Thread.sleep(1);
						if(timeAtNextBullet != 0) { timeAtNextBullet -= 4; }
					} 
					catch (InterruptedException e) { e.printStackTrace(); }
				}
				clip.setFramePosition(0);
				clip.start();
			}
			}, (long)(500));
	}
	
	private void playZombieDeathSound() {
		int randomNum = ThreadLocalRandom.current().nextInt(78, 88 + 1);
		Clip clip = audioSource.getAudioClip(2, randomNum); 
		if(clip.isRunning() || clip.isActive()) {
			clip.stop();
			clip.flush();
			try {
				Thread.sleep(1);
				if(timeAtNextBullet != 0) { timeAtNextBullet -= 4; }
			} 
			catch (InterruptedException e) { e.printStackTrace(); }
		}
		clip.setFramePosition(0);
		clip.start();
	}
	
	private void playZombieAttackSound(Zombie zomb) {
		int randomAttack = ThreadLocalRandom.current().nextInt(55, 77 + 1);
		Clip clipAttack = audioSource.getAudioClip(2, randomAttack); 
		clipAttack.setFramePosition(0);
		clipAttack.start();
		
		if(zomb != null) {
			zomb.setGrowlLength(audioSource.getAudioLength(2, randomAttack) / 13);
			zomb.setMissHit(true);
			zomb.setPlayGrowl(true);
		}
	}
	
	private void playZombieHitSound() {
		int randomHit = ThreadLocalRandom.current().nextInt(4, 6 + 1);
		Clip clipHit = audioSource.getAudioClip(2, randomHit);
		clipHit.setFramePosition(0);
		clipHit.start();
	}
	
	private void playRoundMusic() {
		auxTimer.schedule(new TimerTask() {
			@Override
			public void run() { incrementRound = false; }
			}, (long)(3000));
	}
	
	}


