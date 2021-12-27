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
	private GameLevelWindow window;
	
	private static JFrame frame; 
	private static JPanel panel;
	
	private JButton play;
	private JButton diff;
	private JButton scores;
	private JButton tutorial;
	
	//private KeyInputs inputs; 
	
	
	public Game() {
		frameX = 800;
		frameY = 550; 
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(g.frameX, g.frameY);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		
		
		
		///////
		HighScore hs1 = new HighScore("Jim", 23, 13605, 325);
		//HighScore hs2 = new HighScore("Person B", 40);
		//HighScore hs3 = new HighScore("Person C", 30);
		//HighScore hs4 = new HighScore("Person D", 20);
		//HighScore hs5 = new HighScore("Person E", 10);
		
		////////
		
		g.getHighScores().add(hs1);
		//g.getHighScores().add(hs2);
		//g.getHighScores().add(hs3);
		//g.getHighScores().add(hs4);
		//g.getHighScores().add(hs5);
		
	}
	
	
	private class ButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == diff) {
				handleDifficulty();
			}
			else if(e.getSource() == play) {
				window = new GameLevelWindow("Zombies", getGame());
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
			String temp = "";
			for(int i = highScores.size() - 1; i >= 0; i--) {
				if(i != highScores.size()- 1) { temp += "\n"; }
				temp += Integer.toString(highScores.size() - i) + ": " +  highScores.get(i).toString();
				
			}
			JOptionPane.showMessageDialog(null, temp, "High Scores", JOptionPane.PLAIN_MESSAGE);
			
			/*
			JFrame gameScores =  new JFrame("High Scores");
			gameScores.setLocationRelativeTo(null);
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
			*/
		}
		private void handleTutorial() {
			JOptionPane.showMessageDialog(null, "Pause: Esc \nMove: WASD \nLook: Arrow Keys \nShoot: Space bar \nSwitch Weapon: 1 \nReload: R \nUse: F \nSprint: Shift", "Game Controls", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public Game getGame() {
		return this;
	}
	
	public JFrame getFrame() {
		return Game.frame;
	}
	
	public void endGame() {
		window.dispose();
	}
	
	public String scoreToString(int newHighScore, int newRounds, int newPoints, int newKills ) {
		String finalString = "";
		for(int i = highScores.size()-1; i >= 0; i--) {
			if(i != highScores.size()-1) { finalString += "\n"; }
			if(newHighScore <= 0 && i == 0 && highScores.size() < 5) {
				finalString += Integer.toString(highScores.size() - i) + ": " + highScores.get(i).toString() + "\n";
				finalString += Integer.toString(highScores.size() + 1) + ": Your Name | Points: " + Integer.toString(newPoints) + " | Rounds: " + Integer.toString(newRounds) + " | Kills: " + Integer.toString(newKills);
			}
			else if (i == 0 && highScores.size() == 5 && newHighScore > 0){ }
			else if (i == newHighScore || (highScores.size() < 5 && i+1 == highScores.size() && highScores.size() == newHighScore)) {
				if(highScores.size() < 5 && i+1 == highScores.size() && highScores.size() == newHighScore) {
					finalString += Integer.toString(highScores.size()) + ": Your Name | Points: " + Integer.toString(newPoints) + " | Rounds: " + Integer.toString(newRounds) + " | Kills: " + Integer.toString(newKills) + "\n";
					finalString += Integer.toString(highScores.size() - i - 1) + ": " + highScores.get(i).toString();
				}
				else if (highScores.size() < 5){
					finalString += Integer.toString(highScores.size() - i) + ": " + highScores.get(i).toString() + "\n";
					finalString += Integer.toString(highScores.size() - i + 1) + ": Your Name | Points: " + Integer.toString(newPoints) + " | Rounds: " + Integer.toString(newRounds) + " | Kills: " + Integer.toString(newKills);
				}
				else {
					finalString += Integer.toString(highScores.size() - i) + ": Your Name | Points: " + Integer.toString(newPoints) + " | Rounds: " + Integer.toString(newRounds) + " | Kills: " + Integer.toString(newKills);
				}
			}
			else {
				if(i > newHighScore) {
					finalString += Integer.toString(highScores.size() - i) + ": " + highScores.get(i).toString();
				}
				else if (i < newHighScore) {
					if(highScores.size() < 5){
						finalString += Integer.toString(highScores.size() - i + 1) + ": "  + highScores.get(i).toString();
					}
					else {
						finalString += Integer.toString(highScores.size() - i + 1) + ": " +  highScores.get(i).toString();
					}

				}
				
			}
		}
		return finalString;
	}
	
	public int isHighScore(int newRounds, int newPoints, int newKills) {
		int highestSpot = 0;
		int scoreState = -1;
		for(int i = 0; i < highScores.size(); i++) {
			scoreState = highScores.get(i).compareScores(newRounds, newPoints, newKills);
			if(scoreState > 0) {
				highestSpot = i + 1;
			}
		}
		return highestSpot;
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
