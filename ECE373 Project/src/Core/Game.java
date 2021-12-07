package Core;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.swing.*;
import Core.HighScore;
import Levels.GameLevelWindow;
import java.util.Collections;

import java.util.ArrayList;

public class Game{
	private int frameX;
	private int frameY;
	private double difficulty;
	private int score; 
	private ArrayList<HighScore> highScores;
	
	private static JFrame frame; 
	private static JPanel panel;
	
	private JButton play;
	private JButton diff;
	private JButton scores;
	private JButton tutorial;
	
	//private KeyInputs inputs; 
	
	
	public Game() {
		frameX = 600;
		frameY = 450; 
		difficulty = 1;
		score = 0; 
		highScores = new ArrayList<HighScore>();
		
		//inputs = new KeyInputs(this);
		
		GridLayout layout = new GridLayout(0,1);
		panel = new JPanel();
		panel.setLayout(layout);
		
		play = new JButton("Play");
		diff = new JButton("Difficulty");
		scores = new JButton("High Scores");
		tutorial = new JButton("Tutorial");
		
		play.setBounds(100,50, 100, 50);
		diff.setBounds(100, 175, 100, 50);
		scores.setBounds(100, 300, 130, 50);
		tutorial.setBounds(100, 300, 130, 50);
		
		play.addActionListener(new ButtonListener());
		diff.addActionListener(new ButtonListener());
		scores.addActionListener(new ButtonListener());
		tutorial.addActionListener(new ButtonListener());
		
		panel.add(play);
		panel.add(diff);
		panel.add(scores);
		panel.add(tutorial);
		
	}
	
	public static void main(String[] args) {
		//initialize the new window for us to draw on
		Game g = new Game();
		frame = new JFrame("ECE373 Game");
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{System.exit(0);}});
		
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(g.frameX, g.frameY);
		
		
		///////
		HighScore hs1 = new HighScore("Person A", 50);
		HighScore hs2 = new HighScore("Person B", 40);
		HighScore hs3 = new HighScore("Person C", 30);
		HighScore hs4 = new HighScore("Person D", 20);
		HighScore hs5 = new HighScore("Person E", 10);
		
		////////
		
		g.getHighScores().add(hs1);
		g.getHighScores().add(hs2);
		g.getHighScores().add(hs3);
		g.getHighScores().add(hs4);
		g.getHighScores().add(hs5);
		
	}
	
	
	private class ButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == diff) {
				handleDifficulty();
			}
			else if(e.getSource() == play) {
				GameLevelWindow window = new GameLevelWindow("Zombies", getGame());
				
			}
			else if(e.getSource() == scores) {
				handleScores();
			}
			else if(e.getSource() == tutorial) {
				handleTutorial();
			}
		}
		
		private void handleDifficulty() {
			String[] choices = { "1", "2", "3" };
		    String input = (String) JOptionPane.showInputDialog(null, "Choose your difficulty...",
		        "The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null,
		        choices, // Array of choices
		        choices[1]); // Initial choice
		    //System.out.println(input);
		    if(input != null) {
				setDifficulty(Integer.parseInt(input));
			}
		}
		
		private void handleScores() {
			JFrame gameScores =  new JFrame("High Scores");
			gameScores.setSize(500,300);
			gameScores.setVisible(true);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			PrintStream printStream = new PrintStream(outStream);
			System.setOut(printStream);
			printScores();
			System.setOut(System.out);
			JTextArea display = new JTextArea(outStream.toString());
			JScrollPane scroll = new JScrollPane(display);
			display.setMargin(new Insets(0,5,0,0));
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			//scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			gameScores.add(scroll, BorderLayout.CENTER);
			gameScores.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		private void handleTutorial() {
			JFrame Tutorial =  new JFrame("Tutorial");
			JLabel test = new JLabel("<html><body>Press W - Move up<br>"
					+ "Press A - Move right"
					+ "<br>Press A - Move left"
					+ "<br>Press D - Move right"
					+ "<br>Press S - Move down"
					+ "<br>Arrow Key Up - Change direction up"
					+ "<br>Arrow Key Left - Change direction left"
					+ "<br>Arrow Key Right - Change direction right"
					+ "<br>Arrow Key Down - Change direction down"
					+ "<br>Press Spacebar to fire gun"
					+ "<br>Press R to reload your gun"
					+ "<br>Press 1 to change gun<html>");
			JPanel p = new JPanel();
			p.add(test);
			Tutorial.add(p);
			Tutorial.setSize(300, 300);
			//Tutorial.show();
		}
	}
	
	public Game getGame() {
		return this;
	}
	
	public JFrame getFrame() {
		return Game.frame;
	}
	
	public void printScores() {		
		int temp = 1, j = 0, k = this.highScores.size();
	
		int max = this.highScores.get(0).getScore(); 
		
		ArrayList<HighScore> tempList = new ArrayList<HighScore>();
		
		for(int i = 0; i < this.highScores.size(); i++) {
			tempList.add(this.highScores.get(i));
		}
		
		while(k > 0) {
			for(int i = 0; i < tempList.size(); i++) {
				if(max < tempList.get(i).getScore()) {
					max = highScores.get(i).getScore();
					j = i;
				}
			}
			System.out.println(temp + ". " + tempList.get(j).getName() + ": " + tempList.get(j).getScore());
			temp += 1;
			k--;
			max = 0;
			tempList.remove(j);
		}
	}
	
	public void setDifficulty(double skill) {
		this.difficulty = skill;
	}
	
	public int getFrameX() {
		return this.frameX;
	}
	
	public int getFrameY() {
		return this.frameY;
	}
	
	public double getDifficulty() {
		return this.difficulty;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public ArrayList<HighScore> getHighScores(){
		return this.highScores;
	}
	
	public JPanel getPanel() {
		return Game.panel;
	}
	public void setFrame(int x, int y) {
		frameX = x;
		frameY = y;
		frame.setSize(x, y);
	}
	
}
