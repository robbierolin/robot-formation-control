package motor_schemas;

import java.util.List;

/**
 * Avoid static obstacle motor schema
 *
 */
public class Avoid_Static_Obstacle extends Avoid_Obstacle
{

	/**
	 * @param pGain - gain
	 * @param pParams - list of parameters
	 */
	public Avoid_Static_Obstacle(double pGain, List<Parameter> pParams)
	{
		super(pGain, pParams);
	}
	
}
