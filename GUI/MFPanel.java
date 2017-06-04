package GUI;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Formation;
import model.Main;
import motor_schemas.Maintain_Formation;
import formation_maintenance.Formation_Maintenance_Scheme;
import formation_maintenance.Leader_Reference;
import formation_maintenance.Neighbour_Reference;

/**
 * Panel for maintain formation schema
 *
 */
@SuppressWarnings("serial")
public class MFPanel extends ParameterPanel
{
	JComboBox<String> formationList;
	JLabel form;
	JComboBox<String> schemeList;
	JLabel scheme;
	JLabel killSpace;
	JTextField gain;
	JTextField desiredSpacing;
	JTextField controlledZoneRadius;
	JTextField deadZoneRadius;
	JLabel lGain;
	JLabel ldesiredSpacing;
	JLabel lcontrolledZoneRadius;
	JLabel ldeadZoneRadius;
	/**
	 * @param width
	 * @param height
	 * @param name
	 * @param model
	 */
	public MFPanel(int width, int height, String name, Main model) {
		super(width, height, name, model);
	}
	
	public void addValues(JPanel panel, Main model) {
		parameters = panel;
		
		Maintain_Formation mf = (Maintain_Formation)model.getMotorSchema("formation");

		if (formationList == null) {
			formationList = new JComboBox<String>(formations);
			form = new JLabel("Formation: ");
			form.setLabelFor(formationList);
			schemeList = new JComboBox<String>(maintenance_schemes);
			scheme = new JLabel("Formation Maintenance Schemes: ");
			scheme.setLabelFor(schemeList);
			killSpace = new JLabel("");
			killSpace.setPreferredSize(new Dimension(170,0));
			
			gain = new JTextField(String.format("%1.1f", mf.getGain()), 3);
			lGain = new JLabel("Gain");
			lGain.setLabelFor(gain);
			desiredSpacing = new JTextField(String.format("%2.0f", mf.getDesiredSpacing().value), 3);
			ldesiredSpacing = new JLabel("Desired Spacing");
			ldesiredSpacing.setLabelFor(desiredSpacing);
			controlledZoneRadius = new JTextField(String.format("%2.0f", mf.getControlledZoneRadius().value), 3);
			lcontrolledZoneRadius = new JLabel("Controlled Zone Radius");
			lcontrolledZoneRadius.setLabelFor(controlledZoneRadius);
			deadZoneRadius = new JTextField(String.format("%2.0f", mf.getDeadZoneRadius().value), 3);
			ldeadZoneRadius = new JLabel("Dead Zone Radius");
			ldeadZoneRadius.setLabelFor(deadZoneRadius);
		}
		else
		{
		    gain.setText("");
		    gain.removeAll();
		    double g = mf.getGain();
			gain.setText(String.format("%1.1f", g));
			desiredSpacing.setText(String.format("%2.0f", mf.getDesiredSpacing().value));
			controlledZoneRadius.setText(String.format("%2.0f", mf.getControlledZoneRadius().value));
			deadZoneRadius.setText(String.format("%2.0f", mf.getDeadZoneRadius().value));
		}
		Formation f = model.getFormation();
		if (f == Formation.Column) {
			formationList.setSelectedItem("Column");
		}
		else if (f == Formation.Line) {
			formationList.setSelectedItem("Line");
		}
		else if (f == Formation.Diamond) {
			formationList.setSelectedItem("Diamond");
		}
		else  {
			formationList.setSelectedItem("Wedge");
		}
	

		Formation_Maintenance_Scheme fms = model.getFMS();
		if (fms instanceof Leader_Reference) {
			schemeList.setSelectedItem("Leader Reference");
		}
		else if (fms instanceof Neighbour_Reference) {
			schemeList.setSelectedItem("Neighbour Reference");
		}
		else {
			schemeList.setSelectedItem("Unit-Centre Reference");
		}
		
		
		panel.add(form);
		panel.add(formationList);
		panel.add(scheme);
		panel.add(schemeList);
		panel.add(killSpace);		
		panel.add(lGain);
		panel.add(gain);
		panel.add(ldesiredSpacing);
		panel.add(desiredSpacing);
		panel.add(lcontrolledZoneRadius);
		panel.add(controlledZoneRadius);
		panel.add(ldeadZoneRadius);
		panel.add(deadZoneRadius);
	}
	
}
