package Levels;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import Core.AudioHandler;
import Core.Game;
import Gameplay.Bullet;
import Gameplay.Zombie;

import java.util.Iterator;

public class GameLevelWindow extends JFrame{
	
	private Game game;
	private GameLevel level;
	private ArrayList<Graphics> Zombies;
	private ArrayList<Graphics> Bullets;
	private BufferedImage bf;
	private BufferedImage player;
	private BufferedImage bullet;
	private BufferedImage zombie;
	private Timer timer;
	private boolean reset;

	private int keyWASD; //move
	private int keyArrow; //look
	private int keyAux; //interact; switchgun|shoot|use|run|reload
	private int keyGame; //Game modification pause|null|null|null
	
	private double bulletDefaultX;
	private double bulletDefaultY;
	
	JLabel label;
	private final int TIME_DELAY = 30;
	
	public GameLevelWindow(String windowTitle, Game gamein)
	{
		super(windowTitle);
		game = gamein;
		game.setFrame(game.getFrameX(), game.getFrameY());
		level = new GameLevel(game, "");
		createGUI();
		timer = new Timer(TIME_DELAY, new TimerListener());
		timer.start();
	}
	
	private void createGUI() {
		JLabel background = setBackground(this, "images/background/background_00.png");

		keyWASD = 0b0000; //move
	    keyArrow = 0b0000; //look
		keyAux = 0b00000; //interact
		keyGame = 0b0000; //Game modification 

		setSize(game.getFrameX(), game.getFrameY());
		setLayout(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("<HTML><p style=\"color:red\">Lives: " + level.getPlayer().getLives() +
				"<BR>Score: " + game.getScore() + "<BR>Gun: " + level.getPlayer().getGunName() + "</HTML></p>");
		background.add(label);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		addKeyListener(new MyKeyListener());
		
		setVisible(true);
		
		try { player = ImageIO.read(new File("images/player/player_90.png"));}
		catch (IOException e) { System.out.println("ERROR: player image not found"); }
		try { zombie = ImageIO.read(new File("images/zombie/zombie_90.png")); } 
		catch (IOException e) { System.out.println("ERROR: zombie image not found"); }
		try {
			bullet = ImageIO.read(new File("images/bullet/normal/bullet_270.png"));
			bulletDefaultX = bullet.getWidth();
			bulletDefaultY = bullet.getHeight();
		}
		catch (IOException e) { System.out.println("ERROR: bullet image not found"); }
		reset = false;
	}
	
	public static JLabel setBackground(JFrame frame, String backgroundFilePath)  
    {  
         frame.setLayout(new BorderLayout());  
         JLabel background=new JLabel(new ImageIcon(backgroundFilePath));  
         frame.add(background);  
         background.setLayout(new FlowLayout(0));  
         return background;  
    }
	
	public void paint(Graphics g) {
		bf = new BufferedImage( this.getWidth(),this.getHeight(), BufferedImage.TYPE_INT_RGB);

	    try{
	    animation(bf.getGraphics());
	    g.drawImage(bf,0,0,null);
	    }catch(Exception ex){

	    }
	}
	
	public void animation(Graphics g) {
		super.paint(g);
		
		//Point2D tempPT = new Point2D.Double();
		FieldPoint tempPT = new FieldPoint();
		ArrayList<Zombie> zomblist = level.getZombies();
		ArrayList<Bullet> bullist = level.getBullets();
		Iterator<Zombie> itr = zomblist.iterator();
		Iterator<Bullet> itr2 = bullist.iterator();
		while (itr.hasNext()) {
			Zombie ot = itr.next();
			tempPT = ot.getLocation();
			loadNewZombieImage(tempPT.getAngleView());
			g.drawImage(zombie, (int)Math.ceil(tempPT.getX() - zombie.getWidth()/2), (int)Math.ceil(tempPT.getY() - zombie.getHeight()/2), null);
			/*g.setColor(Color.RED);
			g.fillOval((int)tempPT.getX(), (int)tempPT.getY(), 10, 10);
			*/
	}

		while (itr2.hasNext()) {
			Bullet lt = itr2.next();
			tempPT = lt.getLocation();
			if(lt.getCollisions() > 0) { loadNewRedBulletImage(tempPT.getAngleView()); }
			else { loadNewNormalBulletImage(tempPT.getAngleView()); }
			
			g.drawImage(bullet, (int)Math.ceil(tempPT.getX() - bullet.getWidth()/2), (int)Math.ceil(tempPT.getY() - bullet.getHeight()/2), null);

			//g.setColor(Color.RED);
			//g.fillOval((int)tempPT.getX(), (int)tempPT.getY(), 10, 10);
			
	}
	loadNewPlayerImage(level.getPlayer().getLookAngle());
	g.drawImage(player, (int)Math.ceil(level.getPlayer().getLocation().getX() - player.getWidth()/2), (int)Math.ceil(level.getPlayer().getLocation().getY() - player.getHeight()/2 ), null);	
		
	}
	
	public void loadNewPlayerImage(double playerAngle) {
		if(playerAngle < 45) {
			try {
				player = ImageIO.read(new File("images/player/player_0.png"));
			}
			catch (IOException e) { System.out.println("ERROR: player_0 image not found"); }
		}
		else if(playerAngle < 90) {
			try {
				player = ImageIO.read(new File("images/player/player_45.png"));
			}
			catch (IOException e) { System.out.println("ERROR: player_45 image not found"); }
		}
		else if(playerAngle < 135) {
			try {
				player = ImageIO.read(new File("images/player/player_90.png"));
			}
			catch (IOException e) { System.out.println("ERROR: player_90 image not found"); }
		}
		else if(playerAngle < 180) {
			try {
				player = ImageIO.read(new File("images/player/player_135.png"));
			}
			catch (IOException e) { System.out.println("ERROR: player_135 image not found"); }
		}
		else if(playerAngle < 225) {
			try {
				player = ImageIO.read(new File("images/player/player_180.png"));
			}
			catch (IOException e) { System.out.println("ERROR: player_180 image not found"); }
		}
		else if(playerAngle < 270) {
			try {
				player = ImageIO.read(new File("images/player/player_225.png"));
			}
			catch (IOException e) { System.out.println("ERROR: player_225 image not found"); }
		}
		else if(playerAngle < 315) {
			try {
				player = ImageIO.read(new File("images/player/player_270.png"));
			}
			catch (IOException e) { System.out.println("ERROR: player_270 image not found"); }
		}
		else if(playerAngle < 360) {
			try {
				player = ImageIO.read(new File("images/player/player_315.png"));
			}
			catch (IOException e) { System.out.println("ERROR: player_315 image not found"); }
		}
	}
	
	public void loadNewZombieImage(double zombieAngle) {
		if(zombieAngle < 45) {
			try {
				zombie = ImageIO.read(new File("images/zombie/zombie_0.png"));
			}
			catch (IOException e) { System.out.println("ERROR: zombie_0 image not found"); }
		}
		else if(zombieAngle < 90) {
			try {
				zombie = ImageIO.read(new File("images/zombie/zombie_45.png"));
			}
			catch (IOException e) { System.out.println("ERROR: zombie_45 image not found"); }
		}
		else if(zombieAngle < 135) {
			try {
				zombie = ImageIO.read(new File("images/zombie/zombie_90.png"));
			}
			catch (IOException e) { System.out.println("ERROR: zombie_90 image not found"); }
		}
		else if(zombieAngle < 180) {
			try {
				zombie = ImageIO.read(new File("images/zombie/zombie_135.png"));
			}
			catch (IOException e) { System.out.println("ERROR: zombie_135 image not found"); }
		}
		else if(zombieAngle < 225) {
			try {
				zombie = ImageIO.read(new File("images/zombie/zombie_180.png"));
			}
			catch (IOException e) { System.out.println("ERROR: zombie_180 image not found"); }
		}
		else if(zombieAngle < 270) {
			try {
				zombie = ImageIO.read(new File("images/zombie/zombie_225.png"));
			}
			catch (IOException e) { System.out.println("ERROR: zombie_225 image not found"); }
		}
		else if(zombieAngle < 315) {
			try {
				zombie = ImageIO.read(new File("images/zombie/zombie_270.png"));
			}
			catch (IOException e) { System.out.println("ERROR: zombie_270 image not found"); }
		}
		else if(zombieAngle < 360) {
			try {
				zombie = ImageIO.read(new File("images/zombie/zombie_315.png"));
			}
			catch (IOException e) { System.out.println("ERROR: zombie_315 image not found"); }
		}
	}
	
	
	public void loadNewNormalBulletImage(double bulletAngle) {
		if(bulletAngle < 45) {
			try {
				bullet = ImageIO.read(new File("images/bullet/normal/bullet_0.png"));
			}
			catch (IOException e) { System.out.println("ERROR: normal bullet_0 image not found"); }
		}
		else if(bulletAngle < 90) {
			try {
				bullet = ImageIO.read(new File("images/bullet/normal/bullet_45.png"));
			}
			catch (IOException e) { System.out.println("ERROR: normal bullet_45 image not found"); }
		}
		else if(bulletAngle < 135) {
			try {
				bullet = ImageIO.read(new File("images/bullet/normal/bullet_90.png"));
			}
			catch (IOException e) { System.out.println("ERROR: normal bullet_90 image not found"); }
		}
		else if(bulletAngle < 180) {
			try {
				bullet = ImageIO.read(new File("images/bullet/normal/bullet_135.png"));
			}
			catch (IOException e) { System.out.println("ERROR: normal bullet_135 image not found"); }
		}
		else if(bulletAngle < 225) {
			try {
				bullet = ImageIO.read(new File("images/bullet/normal/bullet_180.png"));
			}
			catch (IOException e) { System.out.println("ERROR: normal bullet_180 image not found"); }
		}
		else if(bulletAngle < 270) {
			try {
				bullet = ImageIO.read(new File("images/bullet/normal/bullet_225.png"));
			}
			catch (IOException e) { System.out.println("ERROR: normal bullet_225 image not found"); }
		}
		else if(bulletAngle < 315) {
			try {
				bullet = ImageIO.read(new File("images/bullet/normal/bullet_270.png"));
			}
			catch (IOException e) { System.out.println("ERROR: normal bullet_270 image not found"); }
		}
		else if(bulletAngle < 360) {
			try {
				bullet = ImageIO.read(new File("images/bullet/normal/bullet_315.png"));
			}
			catch (IOException e) { System.out.println("ERROR: normal bullet_315 image not found"); }
		}
	}
	
	public void loadNewRedBulletImage(double bulletAngle) {
		if(bulletAngle < 45) {
			try {
				bullet = ImageIO.read(new File("images/bullet/red/bullet_0_red.png"));
			}
			catch (IOException e) { System.out.println("ERROR: bullet_0_red image not found"); }
		}
		else if(bulletAngle < 90) {
			try {
				bullet = ImageIO.read(new File("images/bullet/red/bullet_45_red.png"));
			}
			catch (IOException e) { System.out.println("ERROR: bullet_45_red image not found"); }
		}
		else if(bulletAngle < 135) {
			try {
				bullet = ImageIO.read(new File("images/bullet/red/bullet_90_red.png"));
			}
			catch (IOException e) { System.out.println("ERROR: bullet_90_red image not found"); }
		}
		else if(bulletAngle < 180) {
			try {
				bullet = ImageIO.read(new File("images/bullet/red/bullet_135_red.png"));
			}
			catch (IOException e) { System.out.println("ERROR: bullet_135_red image not found"); }
		}
		else if(bulletAngle < 225) {
			try {
				bullet = ImageIO.read(new File("images/bullet/red/bullet_180_red.png"));
			}
			catch (IOException e) { System.out.println("ERROR: bullet_180_red image not found"); }
		}
		else if(bulletAngle < 270) {
			try {
				bullet = ImageIO.read(new File("images/bullet/red/bullet_225_red.png"));
			}
			catch (IOException e) { System.out.println("ERROR: bullet_225_red image not found"); }
		}
		else if(bulletAngle < 315) {
			try {
				bullet = ImageIO.read(new File("images/bullet/red/bullet_270_red.png"));
			}
			catch (IOException e) { System.out.println("ERROR: bullet_270_red image not found"); }
		}
		else if(bulletAngle < 360) {
			try {
				bullet = ImageIO.read(new File("images/bullet/red/bullet_315_red.png"));
			}
			catch (IOException e) { System.out.println("ERROR: bullet_315_red image not found"); }
		}
	}
	
	private class MyKeyListener extends KeyAdapter{
		
		public void keyPressed(KeyEvent event) {
			int key = event.getKeyCode();
			//keyWASD = 0b0000; //move
			//keyArrow = 0b0000; //look
			//keyAux = 0b00000; //interact; changeGun|shoot|use|run|reload
			//keyGame = 0b0000; //Game Modification  pause|null|null|null
			
			//move
			if(key == KeyEvent.VK_W) { keyWASD = keyWASD | 0b1000; }
			if(key == KeyEvent.VK_A) { keyWASD = keyWASD | 0b0100; }
			if(key == KeyEvent.VK_S) { keyWASD = keyWASD | 0b0010; }
			if(key == KeyEvent.VK_D) { keyWASD = keyWASD | 0b0001; }
			
			//look
			if(key == KeyEvent.VK_UP) { keyArrow = keyArrow | 0b1000; }
			if(key == KeyEvent.VK_LEFT) { keyArrow = keyArrow | 0b0100; }
			if(key == KeyEvent.VK_DOWN) { keyArrow = keyArrow | 0b0010; }
			if(key == KeyEvent.VK_RIGHT) { keyArrow = keyArrow | 0b0001; }
			
			//interact
			if(key == KeyEvent.VK_1) { keyAux = keyAux | 0b10000;}
			if(key == KeyEvent.VK_SPACE) { keyAux = keyAux | 0b01000; }
			if(key == KeyEvent.VK_F) { keyAux = keyAux | 0b00100; }
			if(key == KeyEvent.VK_SHIFT) { keyAux = keyAux | 0b00010; }
			if(key == KeyEvent.VK_R) { keyAux = keyAux | 0b00001; }
			
			//game
			if(key == KeyEvent.VK_ESCAPE) {
				if(level.isPaused().compareAndSet(false, true)) {
					//keyGame = keyGame | 0b1000; 
					//level.isPaused().set(!level.isPaused().get());
					level.pauseGame();
				}
			}
			
			 //System.out.println("PMove: " + keyWASD);
			 //System.out.println("PLook: " + keyArrow);
			 //System.out.println("PInteract: " + keyAux);
		}

		@Override
		 public void keyReleased(KeyEvent event) {
			int key = event.getKeyCode();
			 
			//move
			if(key == KeyEvent.VK_W) { keyWASD = keyWASD & 0b0111; }
			if(key == KeyEvent.VK_A) { keyWASD = keyWASD & 0b1011; }
			if(key == KeyEvent.VK_S) { keyWASD = keyWASD & 0b1101; }
			if(key == KeyEvent.VK_D) { keyWASD = keyWASD & 0b1110; }
				
			//look
			if(key == KeyEvent.VK_UP) { keyArrow = keyArrow & 0b0111; }
			if(key == KeyEvent.VK_LEFT) { keyArrow = keyArrow & 0b1011; }
			if(key == KeyEvent.VK_DOWN) { keyArrow = keyArrow & 0b1101; }
			if(key == KeyEvent.VK_RIGHT) { keyArrow = keyArrow & 0b1110; }
				
			//interact
			if(key == KeyEvent.VK_1) {
				keyAux = keyAux & 0b01111;
				level.changeGun().set(true);
			}
			if(key == KeyEvent.VK_SPACE) { keyAux = keyAux & 0b10111; }
			if(key == KeyEvent.VK_F) { keyAux = keyAux & 0b11011; }
			if(key == KeyEvent.VK_SHIFT) { keyAux = keyAux & 0b11101; }
			if(key == KeyEvent.VK_R) { keyAux = keyAux & 0b11110; }
			
			//game
			if(key == KeyEvent.VK_ESCAPE) { keyGame = keyGame & 0b0111; }
		 }
	}

	
    private class TimerListener implements ActionListener {
    	public void actionPerformed(ActionEvent e){
    		if(reset) { return; }
    		level.tick(1);
    		if(!level.isGameOver()) {
    			if(!level.isPaused().get()) {
    				level.updatePlayer(keyWASD, keyArrow, keyAux);
    				level.detectHits(player, zombie, bulletDefaultX, bulletDefaultY);
    				
    				
    	    		label.setText("<HTML><p style=\"color:white\">Lives: " + level.getPlayer().getLives() +
    	    				"<BR>Score: " + level.getPlayer().getPoints() +
    	    				"<BR>Gun: " + level.getPlayer().getGunName() +
    	    				"<BR>Bullets: " + level.getPlayer().getDisplayBullets() + "  |  Mag: " + level.getPlayer().getCurrentMag() +
    	    				"<BR>Round: " + level.getRound() +
    	    				"<BR>Health: " + level.getPlayer().getHealth() +
    	    				"</HTML></p>");
    	    		
    	    		repaint();
    			}
    		}
    		else {
    			label.setText("");
				level.exitGame();
				reset = true;
				game.endGame();
    		}
	    	//Force a call to the paint method.

	    }
	}
}