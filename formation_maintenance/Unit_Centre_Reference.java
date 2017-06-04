package formation_maintenance;

import java.awt.Point;
import java.util.ArrayList;

import model.Formation;

/**
 * Unit Center Reference formation maintenance scheme
 * Each robot computes the centre of all robots and uses that point to determine where it should be
 *
 */
public class Unit_Centre_Reference extends Formation_Maintenance_Scheme
{
	/**
	 * @param f - formation
	 * @param pDS -desired spacing
	 */
	public Unit_Centre_Reference(Formation f, double pDS) {
		super(f, pDS);
	}
	/**
	 * @param id - id of robot
	 * @param allPoints - the points of all the robots
	 * @return the point where the robot should be
	 */
	public Point getDesiredPosition(int id, ArrayList<Point> allPoints){
		Point centre = computeCentre(allPoints);
		Point p = (Point) centre.clone();
		if (f == Formation.Line) {
			if (id == 1) {
				p.x = (int) (p.x + 0.5*desiredSpacing);
			}
			else if (id == 2) {
				p.x = (int) (p.x + 1.5*desiredSpacing);
			}
			else if (id == 3) {
				p.x = (int) (p.x - 0.5*desiredSpacing);
			}
			else if (id == 4) {
				p.x = (int) (p.x - 1.5*desiredSpacing);
			}
		}
		else if (f == Formation.Diamond) {
			if (id == 1) {
				p.y = (int) (p.y + desiredSpacing);
			}
			else if (id == 2) {
				p.x = (int) (p.x + desiredSpacing);
			}
			else if (id == 3) {
				p.x = (int) (p.x - desiredSpacing);
			}
			else if (id == 4) {
				p.y = (int) (p.y - desiredSpacing);
			}
		}
		else if (f == Formation.Column) {
			if (id == 1) {
				p.y = (int) (p.y + 1.5*desiredSpacing);
			}
			else if (id == 2) {
				p.y = (int) (p.y - 0.5*desiredSpacing);
			}
			else if (id == 3) {
				p.y = (int) (p.y + 0.5*desiredSpacing);
			}
			else if (id == 4) {
				p.y = (int) (p.y - 1.5*desiredSpacing);
			}
		}
		else if (f == Formation.Wedge) {
			if (id == 1) {
				p.x = (int) (p.x + 0.5*desiredSpacing);
				p.y = (int) (p.y + 0.5*desiredSpacing);
			}
			else if (id == 2) {
				p.x = (int) (p.x + 1.5*desiredSpacing);
				p.y = (int) (p.y - 0.5*desiredSpacing);
			}
			else if (id == 3) {
				p.x = (int) (p.x - 0.5 * desiredSpacing);
				p.y = (int) (p.y + 0.5*desiredSpacing);
			}
			else if (id == 4) {
				p.x = (int) (p.x - 1.5*desiredSpacing);
				p.y = (int) (p.y - 0.5*desiredSpacing);
			}
		}
		return p;
	}
	
	private Point computeCentre(ArrayList<Point> points) {
		double avgX = 0;
		double avgY = 0;
		for (Point p : points) {
			avgX += p.getX();
			avgY += p.getY();
		}
		int newX = (int) (avgX / points.size());
		int newY = (int) (avgY / points.size());
		return new Point(newX, newY);
	}
    @Override
    public String toString() {
        return "Unit-Centre Reference";
    }
}
