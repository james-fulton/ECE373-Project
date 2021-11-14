package Levels;

import Core.Game;

public class GameLevelTest {

public static void main(String[] args) {
	
Game game = new Game();
GameLevel level = new GameLevel(game);
//this should create a game level with difficulty of 1(easy) and generate all initial obstacles and powerups. check for all of this.

//each tick should move down the objects by 1 gravity per tick(10 pixels)
//they should be removed from the array list when the Y value is at or less than 0
//when an object disappears at the bottom, a new one should appear at the top.

level.tick(1);
level.tick(1);
level.tick(1);
level.tick(1);
level.tick(1);
level.tick(1);
level.tick(1);
level.tick(1);
level.tick(1);
level.tick(1);
level.tick(1);
level.tick(1);


}
}