package model;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;

import motor_schemas.Motion_Vector;
import motor_schemas.Motor_Schema;

/**
 * Robot Class
 *
 */
public class Robot
{
	private int id;
	private Point currentPoint;
	Main model;
	
	/**
	 * @param motorSchemas - implementations of motor schemas in model
	 * @param robots - hashmap of all robots
	 * @return - vector indicating where the robot should move
	 */
	public Motion_Vector getResultingBehaviour(List<Motor_Schema> motorSchemas, HashMap<Integer, Robot> robots) {
		double totX = 0;
		double totY = 0;
		for (Motor_Schema ms : motorSchemas) {
			Motion_Vector mv = ms.getVector(model, id);
			totX += mv.getX()*ms.getGain()*10;
			totY += mv.getY()*ms.getGain()*10;
			
		}
		Motion_Vector ret = new Motion_Vector(totX, totY);
		
		return ret;
	}
	
	

	/**
	 * @param pId - id of the robot
	 * @param pModel - reference to the model
	 */
	public Robot(int pId, Main pModel){
		id = pId;
		currentPoint = new Point(0,0);
		model = pModel;
	}
	
	/**
	 * @return - id of the robot
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * @return - current point of the robot
	 */
	public Point getPOS(){
		return currentPoint;
	}
	/**
	 * @param vector - direction and magnitude to move the robot
	 */
	public void move(Motion_Vector vector) {
		currentPoint.x = currentPoint.x + (int)vector.getX();
		currentPoint.y = currentPoint.y + (int)vector.getY();
	}
	
}
