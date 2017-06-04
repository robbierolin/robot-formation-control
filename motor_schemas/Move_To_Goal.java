package motor_schemas;

import java.awt.Point;
import java.util.ArrayList;

import model.Main;

/**
 * Move to goal motor schema
 *
 */
public class Move_To_Goal extends Motor_Schema
{
	Point goal;
	/**
	 * @param pGain - gain
	 * @param pGoal - goal point
	 */
	public Move_To_Goal(double pGain, Point pGoal) {
		this.gain = pGain;
		params = new ArrayList<Parameter>();
		goal = pGoal;
	}
	@Override
	public Motion_Vector getVector(Main model, int id)
	{
		Point curPoint = model.getRobots().get(id).getPOS();
		Motion_Vector toGoal = new Motion_Vector(curPoint, goal);
		toGoal.setMagnitude(this.gain);
		return toGoal;
	}

}
