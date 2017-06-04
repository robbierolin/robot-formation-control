package GUI;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Main;
import motor_schemas.Noise;

/**
 * Panel for Noise schema
 *
 */
@SuppressWarnings("serial")
public class NoisePanel extends ParameterPanel
{
	JTextField gain;
	JLabel lGain;
	/**
	 * @param width
	 * @param height
	 * @param name
	 * @param model
	 */
	public NoisePanel(int width, int height, String name, Main model) {
		super(width, height, name, model);
	}

	@Override
	public void addValues(JPanel panel, Main model)
	{
		parameters = panel;
		Noise n = (Noise) model.getMotorSchema("noise");
		if (gain == null) {
			gain = new JTextField(String.format("%1.1f", n.getGain()), 3);
			lGain = new JLabel("Gain");
			lGain.setLabelFor(gain);
		}
		else {
			gain.setText(String.format("%1.1f", n.getGain()));
		}
		
	
		panel.add(lGain);
		panel.add(gain);
		
	}
	
}
