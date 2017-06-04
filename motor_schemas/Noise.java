package motor_schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Main;

/**
 * Noise motor schema
 *
 */
public class Noise extends Motor_Schema
{

	private int count;
	private Motion_Vector cur;
	/**
	 * @param pGain - gain
	 * @param persistence - how long each noise vector will act for
	 */
	public Noise(double pGain, Parameter persistence) {
		List<Parameter> l = new ArrayList<Parameter>(1);
		l.add(persistence);
		params = l;
		this.gain = pGain;
		count = 0;
	}
	@Override
	public Motion_Vector getVector(Main model, int id)
	{
		Random r = new Random();
		if (count == 0) {
			cur = new Motion_Vector(r.nextDouble(), r.nextDouble());
			cur.setMagnitude(this.gain);
		}
		
		count = (int) ((count + 1) % getPersistence().value); 
		return cur;
	}
	
	/**
	 * @return persistence of the noise
	 */
	public Parameter getPersistence() {
		return params.get(0);
	}

}
