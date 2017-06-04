package motor_schemas;

/**
 * Class to represent parameters to a motor schema
 *
 */
public class Parameter
{
	/**
	 * value of parameter
	 */
	public double value;
	/**
	 * unit of parameter
	 */
	public String unit;
	/**
	 * @param pV - value
	 * @param pUnit - unit
	 */
	public Parameter(double pV, String pUnit){
		value = pV;
		unit = pUnit;
	}
}
