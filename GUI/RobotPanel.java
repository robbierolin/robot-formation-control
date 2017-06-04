package GUI;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Main;
import motor_schemas.Avoid_Robot;

/**
 * Panel for avoid robot schema
 *
 */
@SuppressWarnings("serial")
public class RobotPanel extends ParameterPanel
{
	JTextField gain;
	JTextField soi;
	JLabel lGain;
	JLabel lsoi;
	/**
	 * @param width
	 * @param height
	 * @param name
	 * @param model
	 */
	public RobotPanel(int width, int height, String name, Main model) {
		super(width, height, name, model);
	}

	@Override
	public void addValues(JPanel panel, Main model)
	{
		Avoid_Robot ar = (Avoid_Robot) model.getMotorSchema("robot_obstacle");
		parameters = panel;
		if (gain == null) {
			gain = new JTextField(String.format("%1.1f",  ar.getGain()), 3);
			lGain = new JLabel("Gain");
			lGain.setLabelFor(gain);
			
			soi = new JTextField(String.format("%2.0f", ar.getSphereOfInfluence().value), 3);
			lsoi = new JLabel("Sphere Of Influence");
			lsoi.setLabelFor(soi);
		}
		else {
			gain.setText(String.format("%1.1f", ar.getGain()));
			soi.setText(String.format("%2.0f", ar.getSphereOfInfluence().value));
		}
		
		panel.add(lGain);
		panel.add(gain);
		panel.add(lsoi);
		panel.add(soi);
		
	}
}
