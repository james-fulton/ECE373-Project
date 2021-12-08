package Core;

public class HighScore {
	private String playerName;
	private int points;
	private int rounds;
	private int kills;
	
	public HighScore() {
		playerName = "player";
		points = 0;
		rounds = 0;
		kills = 0;
	}
	
	public HighScore(String name, int score) {
		this.playerName = name;
		this.points = score;
		rounds = 0;
		kills = 0;
	}
	
	public HighScore(String name, int rounds, int points, int kills) {
		this.playerName = name;
		this.points = points;
		this.rounds = rounds;
		this.kills = kills;
	}
	
	public void setName(String name) {
		this.playerName = name;
	}
	
	public void setScore(int score) {
		this.points = score;
	}
	
	public String getName() {
		return this.playerName;
	}
	
	public int getScore() {
		return this.points;
	}
	
	public int getRounds() {
		return rounds;
	}
	
	public int getKills() {
		return kills;
	}
	
	public int compareScores(int newRounds, int newPoints, int newKills) {
		if(newRounds > rounds) {
			if(newPoints > points) {
				if(newKills > kills) {
					return 6;
				}
				return 5;
			}
			return 4;
		}
		else if(newRounds == rounds) {
			if(newPoints > points) {
				if(newKills > kills) {
					return 3;
				}
				return 2;
			}
			else if(newPoints == points) {
				if(newKills > kills) {
					return 1;
				}
				else if(newKills == kills) {
					return 0;
				}
			}
		}
		return -1;
	}
	
	public String toString() {
		String output = playerName + " | Points: " + Integer.toString(points) + " | Rounds: " + Integer.toString(rounds) + " | Kills: " + Integer.toString(kills);
		return output;
	}
}

