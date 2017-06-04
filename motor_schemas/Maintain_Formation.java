package motor_schemas;

import java.awt.Point;
import java.util.List;
import model.Main;
import formation_maintenance.Formation_Maintenance_Scheme;

/**
 * Maintain formation motor schema
 *
 */
public class Maintain_Formation extends Motor_Schema
{
	Formation_Maintenance_Scheme fms;
	/**
	 * @param pGain - gain
	 * @param pParams - list of parameters
	 * @param pFms - formation maintenance scheme
	 */
	public Maintain_Formation(double pGain, List<Parameter> pParams, Formation_Maintenance_Scheme pFms){
		this.gain = pGain;
		this.params = pParams;
		fms = pFms;
	}
	@Override
	public Motion_Vector getVector(Main model, int id)
	{
		Point curPoint = model.getRobots().get(id).getPOS();
		Point desiredPoint = fms.getDesiredPoint(model.getRobots(), id);
		double dist = Motion_Vector.getDistance(desiredPoint, curPoint);
		if (dist <= getDeadZoneRadius().value) {
			return new Motion_Vector(0,0);
		}
		Motion_Vector ret = new Motion_Vector(curPoint, desiredPoint);
		if (dist >= getControlledZoneRadius().value) {
			ret.setMagnitude(this.gain);
		}
		else {
			double mag = this.gain*(dist - getDeadZoneRadius().value) / (getControlledZoneRadius().value - getDeadZoneRadius().value);
			ret.setMagnitude(mag);
		}
		
		return ret;
	}
	
	/**
	 * @return desired spacing of robots
	 */
	public Parameter getDesiredSpacing(){
		return params.get(0);
	}
	/**
	 * @return controlled zone radius
	 */
	public Parameter getControlledZoneRadius(){
		return params.get(1);
	}
	/**
	 * @return dead zone radius
	 */
	public Parameter getDeadZoneRadius(){
		return params.get(2);
	}
}
