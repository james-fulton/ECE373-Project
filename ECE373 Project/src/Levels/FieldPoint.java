package Levels;

public class FieldPoint {
	//Variables
	private double x;
	private double y;
	private double angleView;
	
	//Constructors
	public FieldPoint(double x, double y, double angleView) {
		this.x = x;
		this.y = y;
		this.angleView = angleView;
	}
	
	public FieldPoint() {
		x = 0.0;
		y = 0.0;
		angleView = 90.0;
	}
	
	//Getter & Setter
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setLocation(FieldPoint old) {
		this.x = old.getX();
		this.y = old.getY();
		this.angleView = old.getAngleView();
	}
	
	public double getDistance(FieldPoint other) {
		return Math.sqrt( Math.pow(x-other.getX(), 2) + Math.pow(y-other.getY(), 2) );
	}
	
	public double getDistance(double xOther, double yOther) {
		return Math.sqrt( Math.pow(x-xOther, 2) + Math.pow(y-yOther, 2));
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getAngleView() {
		return angleView;
	}
	public void setAngleView(double angleView) {
		this.angleView = angleView;
	}
	
	public double findImageDistance(FieldPoint target, double xMax, double yMax) {
		//variables
		double finalDistance = 0;
		double targetAngle = findAngle(target, false, false);
		double mathAngle;
		
		//find distance of bullet image at targetAngle
		//(xMax & yMax image for bullet are at 270 degrees)
		mathAngle = targetAngle;
		if(targetAngle == 270 || targetAngle == 90) { finalDistance = yMax; }
		else if(targetAngle == 0 || targetAngle == 180) { finalDistance = xMax; }
		else if(targetAngle > 315 && targetAngle < 360) {
			mathAngle = 360.0 - targetAngle; 
			finalDistance = xMax / Math.cos(Math.toRadians(mathAngle));
		}
		else if(targetAngle >= 225) {
			if(targetAngle > 270) { mathAngle = targetAngle - 270; }
			else { mathAngle = 270 - targetAngle; }
			finalDistance = yMax / Math.cos(Math.toRadians(mathAngle));
		}
		else if(targetAngle >= 135) {
			if(targetAngle > 180) { mathAngle = targetAngle - 180; }
			else { mathAngle = 180 - targetAngle; }
			finalDistance = xMax / Math.cos(Math.toRadians(mathAngle));
		}
		else if(targetAngle >= 45) {
			if(targetAngle > 90) { mathAngle = targetAngle - 90; }
			else { mathAngle = 90 - targetAngle; }
			finalDistance = yMax / Math.cos(Math.toRadians(mathAngle));
		}
		else if(targetAngle >= 0) {
			finalDistance = xMax / Math.cos(Math.toRadians(mathAngle));
		}
		
		return finalDistance;
	}
	
	public double findAngle(FieldPoint target, boolean updateAngle, boolean zombie) {
		double xDif = Math.ceil( target.getX()) - Math.ceil( x ); //-2
		double yDif = Math.ceil( target.getY()) - Math.ceil( y ); //+1
		//double xDif = target.getX() - location.getX() ;
		//double yDif = target.getY() - location.getY() ;
		boolean state = false;
		double angle = 0;
		
		
		
		if( Math.sqrt(xDif*xDif + yDif*yDif) < 12 && zombie) {
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
		//location.setAngleView(angle);
	    if(updateAngle) {
	    	angleView = angle;
	    }
	    return angle;
	}
	
}
