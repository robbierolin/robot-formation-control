package motor_schemas;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import model.Main;
import model.Obstacle;
import model.Robot;

/**
 * Abstract class for both obstacle avoidance schema
 *
 */
public abstract class Avoid_Obstacle extends Motor_Schema
{

	protected Avoid_Obstacle(double pGain, List<Parameter> pParams){
		this.gain = pGain;
		this.params = pParams;
	}
	@Override
	public Motion_Vector getVector(Main model, int id)
	{
		HashMap<Integer, Obstacle> static_obstacles= model.getObstacles();
		List<Point> roPoints = model.getRobotPoints();
		HashMap<Integer, Obstacle> robot_obstacles = new HashMap<Integer, Obstacle>();
		int i = 1;
		for (Point p : roPoints) {
			HashSet<Point> h = new HashSet<Point>();
			h.add(p);
			robot_obstacles.put(i, new Obstacle(h));
			i++;
		}
		
		if (this instanceof Avoid_Robot) {
			return getVectorHelp(model.getRobots().get(id), robot_obstacles);
		}
		else {
			return getVectorHelp(model.getRobots().get(id), static_obstacles);
		}
	}
	
	private Motion_Vector getVectorHelp(Robot r, HashMap<Integer, Obstacle> obstacles) {
		Point curPoint = r.getPOS();
		int maxSpeed = 100;
		//1. find closest obstacle
		int i = getClosestObstacle(obstacles, curPoint);
//		//2. get distance from robot to centre of obstacle
		Point centre = computeCentre(obstacles.get(i).getPoints());
//		double dist = Motion_Vector.getDistance(centre, curPoint);
		//instead of centre get distance to clostest obstacle
		double dist = getClosestDistance(obstacles, curPoint);
		Motion_Vector ret = new Motion_Vector(centre, curPoint);
		if (dist > getSphereOfInfluence().value) {
			return new Motion_Vector(0,0);
		}
		else if (dist <= getMinimumRange().value) {
			if (this instanceof Avoid_Robot) {
				ret.setMagnitude(10);
			}
			else {
				ret.setMagnitude(maxSpeed);
			}
			
		}
		else {
			double mag = this.gain*(getSphereOfInfluence().value - dist - 5) / (getSphereOfInfluence().value - getMinimumRange().value);
			ret.setMagnitude(mag);
		}
		return ret;
		
	}
	private int getClosestObstacle(HashMap<Integer, Obstacle> obstacles, Point curPoint)
	{
		double minDist = Integer.MAX_VALUE;
		int closestObstacleIndex = -1;
		for (Integer i : obstacles.keySet()) {
			HashSet<Point> obstacle = obstacles.get(i).getPoints();
			for (Point p : obstacle) {
				double d = Motion_Vector.getDistance(p, curPoint);
				if (d < minDist) {
					closestObstacleIndex = i;
					minDist = d;
				}
			}
		}
		return closestObstacleIndex;
	}
	private Point computeCentre(HashSet<Point> points) {
		if (points.size() == 1) {
			for (Point p : points) {
				return p;
			}
		}
		double avgX = 0;
		double avgY = 0;
		for (Point p : points) {
			avgX += p.getX();
			avgY += p.getY();
		}
		avgX = avgX/points.size();
		avgY = avgY/points.size();
		
		return new Point((int)avgX, (int)avgY);
	}
	
	private double getClosestDistance(HashMap<Integer, Obstacle> obstacles, Point cur) {
		double ret = Integer.MAX_VALUE;
		for (Obstacle o : obstacles.values()) {
			for (Point p : o.getPoints()) {
				if (Motion_Vector.getDistance(p, cur) < ret) {
					ret = Motion_Vector.getDistance(p, cur);
				}
			}
		}
		return ret;
	}
	
	
	/**
	 * @return distance past which robot is not affected by obstacle
	 */
	public Parameter getSphereOfInfluence(){
		return params.get(0);
	}
	
	/**
	 * @return minimum range a robot can get to a obstacle
	 */
	public Parameter getMinimumRange(){
		return params.get(1);
	}

}
