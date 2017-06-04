package GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import model.Main;
import GUI.GUI.bothPanel.ParameterView;

/**
 * Button Panel
 *
 */
@SuppressWarnings("serial")
public class ButtonPanel extends ParameterPanel
{
    boolean initiated = false;
	/**
	 * @param width
	 * @param height
	 * @param name
	 * @param model
	 * @param parent - container panel
	 */
	public ButtonPanel(int width, int height, String name, Main model, ParameterView parent) {
		super(width, height, name, model, parent);
		
//		this.setBorder(BorderFactory.createLineBorder(Color.red));
	}

	@Override
	public void addValues(JPanel panel, Main model)
	{
	    if (!initiated) {
	        parameters = panel;
	        initiated = true;
	    }
		panel.setLayout(new FlowLayout());
		JButton apply = new JButton("Apply");
		apply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				apply();
				
			}
			
		});
		JButton restart  = new JButton("Restart");
		restart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				restart();
				
			}
			
		});
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				reset();
				
			}
			
		});
		panel.add(apply);
		panel.add(restart);
		panel.add(reset);
		
	}
	
	private void reset() {
		Main.reset();
		parent.notifyO();
		
	}
	
	private void restart() {
		Main.restart(parent.getGoal(), parent.getFormation(), 
				parent.getMaintenanceScheme(), parent.getStObsGain(), parent.getStaticSOI(), 
				5, parent.getRoObsGain(), parent.getRobotSOI(), 5, parent.getMFGain(), parent.getDesiredSpacing(), 
				parent.getControlledZoneRadius(), parent.getDeadZoneRadius(), parent.getGoalGain(), parent.getNoiseGain(), 6);
		parent.notifyO();
	}
	
	private void apply() {
		Main.apply(parent.getGoal(), parent.getFormation(), 
				parent.getMaintenanceScheme(), parent.getStObsGain(), parent.getStaticSOI(), 
				5, parent.getRoObsGain(), parent.getRobotSOI(), 5, parent.getMFGain(), parent.getDesiredSpacing(), 
				parent.getControlledZoneRadius(), parent.getDeadZoneRadius(), parent.getGoalGain(), parent.getNoiseGain(), 6);
		parent.notifyO();
	}
	

//	public void update(){
//		
//	}
}
