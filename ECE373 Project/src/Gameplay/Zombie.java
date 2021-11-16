package Gameplay;

import Levels.FieldPoint;
import java.lang.Math;

public class Zombie extends Entity {
	
	//Constructors
	public Zombie(int diffLevel) {
		super();
		this.health = this.health * diffLevel;
		this.points = 100;
		this.speed = diffLevel;
	}
	
	//Void
	public void removeDebris(int health) {
		// TODO
	}
	
	public void findTarget(FieldPoint target) {
		double xDif = Math.ceil( target.getX()-2) - Math.ceil( location.getX() );
		double yDif = Math.ceil( target.getY()+1) - Math.ceil( location.getY() );
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

		System.out.println("State: " + angle);
		location.setAngleView(angle);
	}

}
