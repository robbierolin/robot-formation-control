package formation_maintenance;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import model.Formation;
import model.Robot;

/**
 * Abstract class for a formation maintenance scheme that will tell a robot where it should be
 *
 */
public abstract class Formation_Maintenance_Scheme
{
	protected Formation f;
	protected double desiredSpacing;
	/**
	 * @param pF - formation
	 * @param pDesiredSpacing - the desired spacing
	 */
	public Formation_Maintenance_Scheme(Formation pF, double pDesiredSpacing) {
		f = pF;
		desiredSpacing = pDesiredSpacing;
	}
	/**
	 * @param robots - hashmap of robots
	 * @param id - id of robot 
	 * @return - desired point in formation for that robot
	 */
	public Point getDesiredPoint(HashMap<Integer, Robot> robots, int id) {
		if (this instanceof Leader_Reference) {
			return ((Leader_Reference) this).getDesiredPosition(id, robots.get(1).getPOS());
		}
		else if (this instanceof Neighbour_Reference) {
			int n = getNeighbour(f, id);
			return ((Neighbour_Reference) this).getDesiredPosition(id, robots.get(n).getPOS());
		}
		else {
			return ((Unit_Centre_Reference) this).getDesiredPosition(id, getRobotPoints(robots.values()));
		}
	}
	
	private static int getNeighbour(Formation f, int id) {
		int n = 0;
		if (f == Formation.Column) {
			if (id == 1) {
				n=3;
			}
			else if (id == 2) {
				n=3;
			}
			else if (id == 3) {
				n=1;
			}
			else {
				n=2;
			}
		}
		else if (f == Formation.Line) {
			if (id == 1) {
				n=2;
			}
			else if (id == 2) {
				n=1;
			}
			else if (id == 3) {
				n=1;
			}
			else {
				n=3;
			}
		}
		else if (f == Formation.Diamond) {
			if (id == 1) {
				n=2;
			}
			else if (id == 2) {
				n=3;
			}
			else if (id == 3) {
				n=4;
			}
			else {
				n=1;
			}
		}
		else if (f == Formation.Wedge) {
			if (id == 1) {
				n=3;
			}
			else if (id == 2) {
				n=1;
			}
			else if (id == 3) {
				n=1;
			}
			else {
				n=3;
			}
		}
		return n;
	}
	private static ArrayList<Point> getRobotPoints(Collection<Robot> robots) {
		ArrayList<Point> ret = new ArrayList<Point>();
		for (Robot r : robots) {
			ret.add(r.getPOS());
		}
		return ret;
	}
	public abstract String toString();
}
