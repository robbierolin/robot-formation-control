package model;

import java.awt.Point;
import java.util.HashSet;

/**
 * Obstacle class - wrapper for a set of points
 *
 */
public class Obstacle
{
	HashSet<Point> points;
	int height;
	int width;
	int x;
	int y;
	/**
	 * @param pPoints - points the obstacle covers
	 */
	public Obstacle(HashSet<Point> pPoints) {
		points = pPoints;
		computeHWXY(points);
	}
	
	/**
	 * @return the set of points covered by the obstacle
	 */
	public HashSet<Point> getPoints() {
		return points;
	}
	private void computeHWXY(HashSet<Point> points) {
		int lx = Integer.MAX_VALUE;
		int ly = Integer.MAX_VALUE;
		for (Point p : points) {
			if (p.x < lx) {
				lx = p.x;
			}
			if (p.y < ly) {
				ly = p.y;
			}
		}
		int w = Integer.MIN_VALUE;
		int h = Integer.MIN_VALUE;
		for (Point p : points) {
			if (p.x - lx > w) {
				w = p.x - lx;
			}
			if (p.y - ly > h) {
				
				h = p.y - ly;
			}
		}
		this.x = lx;
		this.y = ly;
		this.height = h;
		this.width = w;
	}
	
	/**
	 * @return x value of bottom-left corner
	 */
	public int getX(){
		return x;
	}
	/**
	 * @return y value of bottom left corner
	 */
	public int getY(){
		return y;
	}
	/**
	 * @return height of obstacle
	 */
	public int getHeight(){
		return height;
	}
	/**
	 * @return width of obstacle
	 */
	public int getWidth(){
		return width;
	}
}
