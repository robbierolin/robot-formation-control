package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Main;
import GUI.GUI.bothPanel.ParameterView;

/**
 * Abstract class for Panel for a schema
 *
 */
@SuppressWarnings("serial")
public abstract class ParameterPanel extends JPanel
{
	JPanel parameters;
	ParameterView parent;
	String[] formations = {"Line", "Column", "Diamond", "Wedge"};
	String[] maintenance_schemes = {"Leader Reference", "Unit-Centre Reference", "Neighbour Reference"};
	protected ParameterPanel(int width, int height, String name, Main model, ParameterView parent) {
		this.parent = parent;
		this.setMaximumSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		if (!name.equals("button")) {
			JPanel title = new JPanel();
			title.add(new JLabel(name));
			this.add(title);
			
		}
		JPanel values = new JPanel();
		values.setLayout(new FlowLayout(FlowLayout.LEFT));
		values.setMaximumSize(new Dimension(width, height-10));
		values.setMinimumSize(new Dimension(width, height-10));
		values.setPreferredSize(new Dimension(width, height-10));
		addValues(values, model);
		this.add(values);
	}
	protected ParameterPanel(int width, int height, String name, Main model) {
		this.setMaximumSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		if (!name.equals("button")) {
			JPanel title = new JPanel();
			JLabel nameLabel = new JLabel(name);
			Font font = new Font(nameLabel.getFont().getName(), Font.BOLD,12);
            nameLabel.setFont(font);
			title.add(nameLabel);
			this.add(title);
			
		}
		JPanel values = new JPanel();
		values.setLayout(new FlowLayout(FlowLayout.LEFT));
		values.setMaximumSize(new Dimension(width, height-10));
		values.setMinimumSize(new Dimension(width, height-10));
		values.setPreferredSize(new Dimension(width, height-10));
		addValues(values, model);
		this.add(values);
	}
	/**
	 * @param panel - panel to add the panel-specific information like parameters
	 * @param model - source of the information
	 */
	public abstract void addValues(JPanel panel, Main model);
	
	/**
	 * Updates the panel with the current information
	 */
	public void update(){
		Main model = Main.getModel();
		addValues(parameters, model);
		this.updateUI();
		this.repaint();
	}
}
