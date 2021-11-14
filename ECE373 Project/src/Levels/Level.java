package Levels;

import Core.Game;

public abstract class Level {
private String name;
protected Game game;

public Level(Game game) {
	this.game = game;
}

public Level(Game game, String name) {
	this.game = game;
	this.name = name;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public Game getGame() {
	return game;
}

public abstract void tick(float deltaTime);

public abstract void exitGame();
  //FIXME implement
}
