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
		return Math.sqrt( Math.pow(x-other.getX(), 2) + Math.pow(y-other.getY(), 2));
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
	
	
}
