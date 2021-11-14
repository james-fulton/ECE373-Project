package Levels;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import Core.Game;
import Gameplay.Bullet;
import Gameplay.Zombie;

import java.util.Iterator;

public class GameLevelWindow extends JFrame implements KeyListener{
	
	private Game game;
	private GameLevel level;
	private ArrayList<Graphics> Zombies;
	private ArrayList<Graphics> Bullets;
	private BufferedImage bf;
	private Image player;
	private Image bullet;
	private Image zombie;
	private Timer timer;
	
	JLabel label;
	private final int TIME_DELAY = 30;
	
	public GameLevelWindow(String windowTitle, Game gamein) 
	{
		super(windowTitle);
		JLabel background = setBackground(this, "images/background.png");
		timer = new Timer(TIME_DELAY, new TimerListener());
		timer.start();
		

		game = gamein;
		level = new GameLevel(game);

		setSize(game.getFrameX(), game.getFrameY());
		setLayout(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("<HTML><p style=\"color:red\">Lives: " +level.getPlayer().getLives() +
				"<BR>Score: " + game.getScore() +"<BR> Laser damage: "+level.getPlayer().getBulletDamage()+ "</HTML></p>");
		background.add(label);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		addKeyListener(this);
		
		setVisible(true);
		
		
		
		try {
		player = ImageIO.read(new File("images/player.png"));} catch (IOException e) {
			
		}
		try {
			zombie = ImageIO.read(new File("images/zombie.png"));} catch (IOException e) {
				
			}
		try {
			bullet = ImageIO.read(new File("images/bullet.png"));} catch (IOException e) {
				
			}

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
			g.drawImage(zombie, (int)tempPT.getX(), (int)tempPT.getY(), null);
			/*g.setColor(Color.RED);
			g.fillOval((int)tempPT.getX(), (int)tempPT.getY(), 10, 10);
			*/
	}

		while (itr2.hasNext()) {
			Bullet lt = itr2.next();
			tempPT = lt.getLocation();
			g.drawImage(bullet, (int)tempPT.getX(), (int)tempPT.getY(), null);
			/*g.setColor(Color.RED);
			g.fillOval((int)tempPT.getX(), (int)tempPT.getY(), 10, 10);
			*/
	}
	g.drawImage(player, (int)level.getPlayer().getLocation().getX(), (int)level.getPlayer().getLocation().getY(), null);	
		
	}
	
	@Override
    public void keyTyped(KeyEvent e) {

//        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//            System.out.println((int)level.getPlayer().getLocation().getX());
//        }
//        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//            System.out.println("Left key typed");
//        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
    	FieldPoint temp; 

        if (e.getKeyCode() == KeyEvent.VK_D & (double)level.getPlayer().getLocation().getX() < game.getFrameX() - 50) {
        	temp = new FieldPoint();
        	temp.setLocation((double)level.getPlayer().getLocation().getX() + 5.0, (double)level.getPlayer().getLocation().getY());
        	level.getPlayer().setLocation(temp);
//        	System.out.println((int)temp.getX());
        }
        if (e.getKeyCode() == KeyEvent.VK_A & (double)level.getPlayer().getLocation().getX() > 15.0) {
        	temp = new FieldPoint();
        	temp.setLocation((double)level.getPlayer().getLocation().getX() - 5.0, (double)level.getPlayer().getLocation().getY());
        	level.getPlayer().setLocation(temp);
//        	System.out.println((int)temp.getX());
        }
        if (e.getKeyCode() == KeyEvent.VK_W & (double)level.getPlayer().getLocation().getY() > 15.0) {
        	temp = new FieldPoint();
        	temp.setLocation((double)level.getPlayer().getLocation().getX(), (double)level.getPlayer().getLocation().getY()-5.0);
        	level.getPlayer().setLocation(temp);
        }
        if (e.getKeyCode() == KeyEvent.VK_S & (double)level.getPlayer().getLocation().getY() < game.getFrameY() - 50) {
        	temp = new FieldPoint();
        	temp.setLocation((double)level.getPlayer().getLocation().getX(), (double)level.getPlayer().getLocation().getY()+5.0);
        	level.getPlayer().setLocation(temp);
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        	level.shootBullet();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
//        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//            System.out.println("Right key Released");
//        }
//        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//            System.out.println("Left key Released");
//        }
    }
	
	
    private class TimerListener implements ActionListener
	   {
	      public void actionPerformed(ActionEvent e)
	      {
	    	  level.tick(1);
	    	  
	            
	         // Force a call to the paint method.
	         repaint();
	         
			label.setText("<HTML><p style=\"color:red\">Lives: " +level.getPlayer().getLives() +
					"<BR>Score: " + game.getScore() +"<BR> Laser damage: "+level.getPlayer().getBulletDamage()+ "</HTML></p>");
	      }
	   }
	  
	/*public void updatePositions() {
		level.tick(1);
		repaint();
	}*/
}

