package Gameplay;

import Levels.FieldPoint;
import java.lang.Math;

public class Zombie extends Entity {
	//Variables
	private boolean playGrowl;
	private boolean growlTimer;
	private float growlLength;

	//Constructors
	public Zombie(double diffLevel, double speed) {
		super();
		this.health = (int)(50 * diffLevel);
		this.points = 50;
		this.speed = speed;
	}
	
	
	//Setters & Getters
	public boolean getPlayGrowl() {
		return playGrowl;
	}

	public void setPlayGrowl(boolean playGrowl) {
		this.playGrowl = playGrowl;
	}

	public boolean getGrowlTimer() {
		return growlTimer;
	}

	public void setGrowlTimer(boolean growlTimer) {
		this.growlTimer = growlTimer;
	}

	public float getGrowlLength() {
		return growlLength;
	}

	public void setGrowlLength(float growlLength) {
		this.growlLength = growlLength;
	}
	
	
	//Void
	public void removeDebris(int health) {
		// TODO
	}
	
	public void findTarget(FieldPoint target) {
		double xDif = Math.ceil( target.getX()) - Math.ceil( location.getX() ); //-2
		double yDif = Math.ceil( target.getY()) - Math.ceil( location.getY() ); //+1
		//double xDif = target.getX() - location.getX() ;
		//double yDif = target.getY() - location.getY() ;
		boolean state = false;
		double angle = 0;
		
		
		
		if( Math.sqrt(xDif*xDif + yDif*yDif) < 12) {
			state = true;
		}
		
		if(yDif == 0) {
			if(xDif < 0) {
				angle = 180;
				if(state) { angle = 90; }
			}
			else {
				angle = 0;
				if(state) { angle = 270; }
			}
		}
		if(xDif == 0) {
			if(yDif < 0) {
				angle = 270;
				if(state) { angle = 180; }
			}
			else{
				angle = 90;
				if(state) { angle = 0; }
			}
		}
		
		
		if(yDif > 0 && xDif > 0) {
			angle = Math.toDegrees(Math.atan(yDif/xDif));
			if(state) {
				angle = 270 + angle;
			}
		}
	    if(yDif > 0 && xDif < 0) {
	    	angle = 90 + Math.toDegrees(Math.atan(xDif/yDif * -1));
	    	if(state) {
	    		angle = angle - 90;
	    	}
	    }
	    if(yDif < 0 && xDif < 0) {
	    	angle = 180 + Math.toDegrees(Math.atan(yDif/xDif));
	    	if(state) {
	    		angle = angle - 90;
	    	}
	    }
	    if(yDif < 0 && xDif > 0) {
	    	angle = 270 + Math.toDegrees(Math.atan(xDif/yDif * -1));
	    	if(state) {
	    		angle = angle - 90;
	    	}
	    }

		//System.out.println("State: " + angle);
		location.setAngleView(angle);
	}

}
