package GUI;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Main;
import motor_schemas.Avoid_Static_Obstacle;

/**
 * Panel for avoid static obstacle schema
 *
 */
@SuppressWarnings("serial")
public class ObsPanel extends ParameterPanel
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
	public ObsPanel(int width, int height, String name, Main model) {
		super(width, height, name, model);
	}

	@Override
	public void addValues(JPanel panel, Main model)
	{
		parameters = panel;
		Avoid_Static_Obstacle aso = (Avoid_Static_Obstacle) model.getMotorSchema("static_obstacle");
		
		if (gain == null) {
			gain = new JTextField(String.format("%1.1f", aso.getGain()), 3);
			lGain = new JLabel("Gain");
			lGain.setLabelFor(gain);
			
			soi = new JTextField(String.format("%2.0f", aso.getSphereOfInfluence().value), 3);
			lsoi = new JLabel("Sphere Of Influence");
			lsoi.setLabelFor(soi);
		}
		else {
			gain.setText(String.format("%1.1f",  aso.getGain()));
			soi.setText(String.format("%2.0f", aso.getSphereOfInfluence().value));
		}
		
		panel.add(lGain);
		panel.add(gain);
		panel.add(lsoi);
		panel.add(soi);
		
	}
	

}
