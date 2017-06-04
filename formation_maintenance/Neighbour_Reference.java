package formation_maintenance;

import java.awt.Point;

import model.Formation;

/**
 * Neighbour Reference Formation Maintenance Scheme
 * Each robot uses the position of a neighbour to determine its correct position
 *
 */
public class Neighbour_Reference extends Formation_Maintenance_Scheme
{
	/**
	 * @param pF - formation
	 * @param pDS - desired spacing
	 */
	public Neighbour_Reference(Formation pF, double pDS) {
		super(pF, pDS);
	}
	/**
	 * @param id - id of robot
	 * @param neighbourPos - position of neighbour
	 * @return - desired position of robot
	 */
	public Point getDesiredPosition(int id, Point neighbourPos) {
		
		Point p = (Point) neighbourPos.clone();
		if (f == Formation.Column) {
			if (id == 1) {
				p.y = (int) (neighbourPos.y + desiredSpacing);
			}	
			else if (id == 2 || id == 3 || id == 4) {
				p.y = (int) (neighbourPos.y - desiredSpacing);
			}	
		}
		else if (f == Formation.Diamond) {
			if (id == 1) {
				p.x = (int) (neighbourPos.x - desiredSpacing);
				p.y = (int) (neighbourPos.y + desiredSpacing);
			}	
			else if (id == 2) {
				p.x = (int) (neighbourPos.x + desiredSpacing);
				p.y = (int) (neighbourPos.y + desiredSpacing);
			}
			else if (id == 3) {
				p.x = (int) (neighbourPos.x + desiredSpacing);
				p.y = (int) (neighbourPos.y - desiredSpacing);
			}
			else if (id == 4) {
				p.x = (int) (neighbourPos.x - desiredSpacing);
				p.y = (int) (neighbourPos.y - desiredSpacing);
			}
		}
		else if (f == Formation.Line) {
			if (id == 1 || id == 3 || id == 4) {
				p.x = (int) (neighbourPos.x - desiredSpacing);
			}	
			else if (id == 2) {
				p.x = (int) (neighbourPos.x + desiredSpacing);
			}
			
		}
		else if (f == Formation.Wedge) {
			if (id == 1) {
				p.x = (int) (neighbourPos.x + desiredSpacing);
			}	
			else if (id == 2) {
				p.x = (int) (neighbourPos.x + desiredSpacing);
				p.y = (int) (neighbourPos.y - desiredSpacing);
			}
			else if (id == 3) {
				p.x = (int) (neighbourPos.x - desiredSpacing);
			}
			else if (id == 4) {
				p.x = (int) (neighbourPos.x - desiredSpacing);
				p.y = (int) (neighbourPos.y - desiredSpacing);
			}
		}
		return p;
	}
    @Override
    public String toString() {
        return "Neighbour Reference";
    }
}
