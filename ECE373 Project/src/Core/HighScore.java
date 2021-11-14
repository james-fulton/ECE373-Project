package Core;

public class HighScore {
	private String playerName;
	private int score;
	
	public HighScore() {
		playerName = "player";
		score = 0;
	}
	
	public HighScore(String name, int score) {
		this.playerName = name;
		this.score = score;
	}
	
	public void setName(String name) {
		this.playerName = name;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public String getName() {
		return this.playerName;
	}
	
	public int getScore() {
		return this.score;
	}
}

