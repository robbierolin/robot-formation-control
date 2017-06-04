package formation_maintenance;

import java.awt.Point;

import model.Formation;

/**
 * Leader Reference Formation Maintenance Scheme
 * Each robot uses the position of the leader to find its position
 *
 */
public class Leader_Reference extends Formation_Maintenance_Scheme
{
	/**
	 * @param pF - formation
	 * @param pDS - Desired Spacing
	 */
	public Leader_Reference(Formation pF, double pDS){
		super(pF, pDS);
	}
	/**
	 * @param id - id of robot to get position for
	 * @param leaderPos - position of leader
	 * @return - desired position for the robot
	 */
	public Point getDesiredPosition(int id, Point leaderPos) {
		Point p = (Point) leaderPos.clone();
		if (id == 1) {
			return leaderPos;
		}
		else if (f == Formation.Column) {
			if (id == 2) {
				p.y = (int) (leaderPos.y - 2* desiredSpacing);
			}
			else if (id == 3) {
				p.y = (int) (leaderPos.y - desiredSpacing);
			}
			else if (id == 4) {
				p.y = (int) (leaderPos.y - 3*desiredSpacing);
				
			}
		}
		else if (f == Formation.Diamond) {
			if (id == 2) {
				p.x = (int) (leaderPos.x + desiredSpacing);
				p.y = (int) (leaderPos.y - desiredSpacing);
			}
			else if (id == 3) {
				p.x = (int) (leaderPos.x - desiredSpacing);
				p.y = (int) (leaderPos.y - desiredSpacing);
			}
			else if (id == 4) {
				p.y = (int) (leaderPos.y - 2*desiredSpacing);
			}
		}
		else if (f == Formation.Line) {
			if (id == 2) {
				p.x = (int) (leaderPos.x + desiredSpacing);
			}
			else if (id == 3) {
				p.x = (int) (leaderPos.x - desiredSpacing);
			}
			else if (id == 4) {
				p.x = (int) (leaderPos.x - 2*desiredSpacing);
			}
		}
		else if (f == Formation.Wedge) {
			if (id == 2) {
				p.x = (int) (leaderPos.x + desiredSpacing);
				p.y = (int) (leaderPos.y - desiredSpacing);
			}
			else if (id == 3) {
				p.x = (int) (leaderPos.x - desiredSpacing);
			}
			else if (id == 4) {
				p.x = (int) (leaderPos.x - 2*desiredSpacing);
				p.y = (int) (leaderPos.y - desiredSpacing);
			}
		}
		return p;
	}
    @Override
    public String toString() {
        return "Leader Reference";
    }
}
