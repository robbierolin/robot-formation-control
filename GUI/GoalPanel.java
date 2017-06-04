package GUI;

import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Main;
import motor_schemas.Move_To_Goal;

/**
 * Panel for move to goal schema
 *
 */
@SuppressWarnings("serial")
public class GoalPanel extends ParameterPanel
{
	
	JTextField x;
	JTextField y;
	JTextField gain;
	JLabel lGain;
	JLabel ly;
	JLabel lx;
	JLabel goal;
	/**
	 * @param width
	 * @param height
	 * @param name
	 * @param model
	 */
	public GoalPanel(int width, int height, String name, Main model) {
		super(width, height, name, model);
//		this.setBorder(BorderFactory.createLineBorder(Color.blue));
	}

	@Override
	public void addValues(JPanel panel, Main model)
	{
		parameters = panel;
		Move_To_Goal g = (Move_To_Goal)model.getMotorSchema("goal");
		if (x == null) {
			gain = new JTextField(String.format("%1.1f", g.getGain()), 3);
			Point pGoal = model.getGoal();
			int xPos = pGoal.x + 400;
			x = new JTextField(""+xPos, 3);
			y = new JTextField(""+pGoal.y, 3);
			lGain = new JLabel("Gain");
			lGain.setLabelFor(gain);
			ly = new JLabel("y: ");
			ly.setLabelFor(y);
			lx = new JLabel("x: ");
			lx.setLabelFor(x);	
			goal = new JLabel("Goal");
		}
		else {
			Point pGoal = model.getGoal();
			int xPos = pGoal.x + 400;
			gain.setText("");
			gain.setText(String.format("%1.1f", g.getGain()));
			x.setText("" + xPos);
			y.setText("" + pGoal.y);
		}
		
			
		
	
		panel.add(lGain);
		panel.add(gain);
		panel.add(goal);
		panel.add(lx);
		panel.add(x);
		panel.add(ly);
		panel.add(y);
	}
	

}
