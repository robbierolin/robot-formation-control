package motor_schemas;

import java.util.List;

import model.Main;

/**
 * Abstract class for motor schema
 *
 */
public abstract class Motor_Schema
{
	protected double gain;
	protected List<Parameter> params;
	/**
	 * @param model - model of current system
	 * @param id - robot to get vector for
	 * @return vector indicating where this motor schema will direct the robot
	 */
	public abstract Motion_Vector getVector(Main model, int id);
//	public Motor_Schema(double pGain, List<Parameter> pParams){
//		gain = pGain;
//		params = pParams;
//	}
	/**
	 * @return the gain of the schema
	 */
	public double getGain() {
		return gain;
	}
	
	public String toString(){
		return this.getClass().toGenericString();
	}
}
