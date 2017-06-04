package motor_schemas;

import java.awt.Point;

/**
 * Class representing a 2-dimensional vector
 *
 */
public class Motion_Vector
{
	private double x;
	private double y;
	/**
	 * @param x - x value
	 * @param y - y value
	 */
	public Motion_Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * @param from - starting point
	 * @param to - ending point
	 */
	public Motion_Vector(Point from, Point to) {
		this.x = to.x - from.x;
		this.y = to.y - from.y;
	}
	/**
	 * @return x value
	 */
	public double getX(){
		return x;
	}
	/**
	 * @return y value
	 */
	public double getY(){
		return y;
	}
	
	/**
	 * sets magnitude of the vector to 1
	 */
	public void normalize() {
		if (x==0 && y ==0) {
			return;
		}
		double a = Math.sqrt(x*x + y*y);
		x = x/a;
		y = y/a;
	}
	/**
	 * @param mag - what the magnitude of the vector will be
	 */
	public void setMagnitude(double mag) {
		normalize();
		x = x*mag;
		y = y*mag;
	}
	/**
	 * @param a - point 1
	 * @param b - point 2
	 * @return distance between two points
	 */
	public static double getDistance(Point a, Point b) {
		double xDif = a.getX() - b.getX();
		double yDif = a.getY() - b.getY();
		return Math.sqrt(xDif*xDif + yDif*yDif);
	}
	
	public String toString(){
		return "(" + x + "," + y + ")";
	}
	
}
