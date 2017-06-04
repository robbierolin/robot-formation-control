package motor_schemas;

import java.util.List;

/**
 * Avoid Robot Motor Schema
 *
 */
public class Avoid_Robot extends Avoid_Obstacle
{

	/**
	 * @param pGain - gain
	 * @param pParams - list of parameters
	 */
	public Avoid_Robot(double pGain, List<Parameter> pParams)
	{
		super(pGain, pParams);
	}
	
}
