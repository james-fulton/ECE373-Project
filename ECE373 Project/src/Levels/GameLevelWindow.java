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

	private int keyWASD; //move
	private int keyArrow; //look
	private int keyAux; //interact; switchgun|shoot|use|run|reload
	
	JLabel label;
	private final int TIME_DELAY = 30;
	
	public GameLevelWindow(String windowTitle, Game gamein)
	{
		super(windowTitle);
		game = gamein;
		game.setFrame(game.getFrameX() + 200, game.getFrameY() + 100);
		level = new GameLevel(game, "");
		createGUI();
		timer = new Timer(TIME_DELAY, new TimerListener());
		timer.start();
	}
	
	private void createGUI() {
		JLabel background = setBackground(this, "images/background.png");

		keyWASD = 0b0000; //move
	    keyArrow = 0b0000; //look
		keyAux = 0b00000; //interact; shoot|use|null|null

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
		
		try { player = ImageIO.read(new File("images/player.png"));}
		catch (IOException e) { System.out.println("ERROR: player image not found"); }
		try { zombie = ImageIO.read(new File("images/zombie.png")); } 
		catch (IOException e) { System.out.println("ERROR: zombie image not found"); }
		try { bullet = ImageIO.read(new File("images/bullet.png"));}
		catch (IOException e) { System.out.println("ERROR: bullet image not found"); }
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
			g.drawImage(zombie, (int)Math.ceil(tempPT.getX() - zombie.getWidth()/4), (int)Math.ceil(tempPT.getY() - zombie.getHeight()/4), null);
			/*g.setColor(Color.RED);
			g.fillOval((int)tempPT.getX(), (int)tempPT.getY(), 10, 10);
			*/
	}

		while (itr2.hasNext()) {
			Bullet lt = itr2.next();
			tempPT = lt.getLocation();
			g.drawImage(bullet, (int)Math.ceil(tempPT.getX() - bullet.getWidth()/4), (int)Math.ceil(tempPT.getY() - bullet.getHeight()/4), null);
			/*g.setColor(Color.RED);
			g.fillOval((int)tempPT.getX(), (int)tempPT.getY(), 10, 10);
			*/
	}
	g.drawImage(player, (int)Math.ceil(level.getPlayer().getLocation().getX() - player.getWidth()/4), (int)Math.ceil(level.getPlayer().getLocation().getY() - player.getHeight()/2 ), null);	
		
	}
	
	//@Override
    //public void keyTyped(KeyEvent e) {

//        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//            System.out.println((int)level.getPlayer().getLocation().getX());
//        }
//        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//            System.out.println("Left key typed");
//        }

    //}
	
	private class MyKeyListener extends KeyAdapter{
		
		public void keyPressed(KeyEvent event) {
			int key = event.getKeyCode();
			//keyWASD = 0b0000; //move
			//keyArrow = 0b0000; //look
			//keyAux = 0b00000; //interact; changeGun|shoot|use|run|reload
			
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
			if(key == KeyEvent.VK_1) { keyAux = keyAux | 0b10000; }
			if(key == KeyEvent.VK_SPACE) { keyAux = keyAux | 0b01000; }
			if(key == KeyEvent.VK_F) { keyAux = keyAux | 0b00100; }
			if(key == KeyEvent.VK_SHIFT) { keyAux = keyAux | 0b00010; }
			if(key == KeyEvent.VK_R) { keyAux = keyAux | 0b00001; }
			
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
			if(key == KeyEvent.VK_1) { keyAux = keyAux & 0b01111; }
			if(key == KeyEvent.VK_SPACE) { keyAux = keyAux & 0b10111; }
			if(key == KeyEvent.VK_F) { keyAux = keyAux & 0b11011; }
			if(key == KeyEvent.VK_SHIFT) { keyAux = keyAux & 0b11101; }
			if(key == KeyEvent.VK_R) { keyAux = keyAux & 0b11110; }
		 }
	}

	
    private class TimerListener implements ActionListener {
    	public void actionPerformed(ActionEvent e){
    		level.tick(1);
    		level.updatePlayer(keyWASD, keyArrow, keyAux);
	    	//Force a call to the paint method.
	    	repaint();
	        
	    	label.setText("<HTML><p style=\"color:white\">Lives: " + level.getPlayer().getLives() +
					"<BR>Score: " + level.getPlayer().getPoints() +
					"<BR>Gun: " + level.getPlayer().getGunName() +
					"<BR>Bullets: " + level.getPlayer().getCurrentBullets() + "  |  Mag: " + level.getPlayer().getCurrentMag() +
					"<BR>Level: " + level.getRound() +
	    			"</HTML></p>");
	    }
	}
}