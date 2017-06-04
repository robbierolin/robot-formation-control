package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import model.Formation;
import model.Main;
import model.Obstacle;

/**
 * the GUI
 *
 */
public class GUI {
	
    /**
     * Height of window
     */
    public static int TOTAL_HEIGHT = 700;
	/**
	 * Width of window
	 */
	public static int TOTAL_WIDTH = 1200;
	/**
	 * Width of simulation
	 */
	public static int SIMULATION_WIDTH = 800;
	/**
	 * @param model - information to be shown through the GUI
	 */
	public GUI(Main model) {
		 JFrame frame = new JFrame("Formation Simulations");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(TOTAL_WIDTH, TOTAL_HEIGHT);
//	        frame.setLayout(new BoxLayout(frame, BoxLayout.LINE_AXIS));
	        frame.setLocationRelativeTo(null);
	        frame.add(new bothPanel(model));
	        //frame.add(new Canvas());
	        frame.setVisible(true);
	}


@SuppressWarnings("serial")
class bothPanel extends JPanel {
	Canvas c;
	ParameterView pv;
	public bothPanel(Main model) {
		this.setSize(TOTAL_WIDTH, TOTAL_HEIGHT);
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		pv = new ParameterView(model);
		c = new Canvas(model);
		this.add(c);
		
		this.add(pv);
	}
	class Canvas extends JComponent {
		boolean start = true;
	    Main model;
	    public Canvas(Main pModel) {
	    	model = pModel;
	    	this.setSize(SIMULATION_WIDTH,TOTAL_HEIGHT);
	    	this.setMaximumSize(new Dimension(SIMULATION_WIDTH,TOTAL_HEIGHT));
	    	this.setMinimumSize(new Dimension(SIMULATION_WIDTH,TOTAL_HEIGHT));
	    	this.setPreferredSize(new Dimension(SIMULATION_WIDTH,TOTAL_HEIGHT));
	        Thread animationThread = new Thread(new Runnable() {
	            public void run() {
	                while (true) {
	                    model = Main.getModel();
	                    repaint();
	                    try {Thread.sleep(10);} catch (Exception ex) {}
	                }
	            }
	        });

	        animationThread.start();
	   
	    }

	    public void paintComponent(Graphics g) {
	        Graphics2D gg = (Graphics2D) g;
	        placeGoal(g, model.getGoal());
	        placeObstacles(g, model.getObstacles().values());
	      
	        int w = getWidth();
	        int h = getHeight();

	        gg.setColor(Color.BLUE);

	        List<Point> robotPoints = model.getRobotPoints();
	        for (Point p : robotPoints) {
	        	gg.fillRect(p.x+w/2, h-p.y, 10, 10);
	        }

	    }

	    public void placeGoal(Graphics g, Point goal) {
	    	g.setColor(Color.RED);
	    	int h = getHeight();
	    	int w = getWidth();
	    	g.fillRect(goal.x + w/2, h - goal.y, 10, 10);
	    }
	    public void placeObstacles(Graphics g, Collection<Obstacle> obstacles) {
	    	g.setColor(Color.black);
	    	int h = getHeight();
	    	int w = getWidth();
	    	for (Obstacle o : obstacles) {
	    		int x = o.getX();
	    		int y = o.getY();
	    		int width = o.getWidth();
	    		int height = o.getHeight();
	    		g.drawRect(w/2+ x, h-y-height, width, height);
	    	}

	    	
	    }
	    
	    
	}
	class ParameterView extends JComponent {
		
		List<ParameterPanel> panels;
		MFPanel mf;
		GoalPanel g;
		RobotPanel rp;
		ObsPanel op;
		NoisePanel  np;
		ButtonPanel bp;
		public ParameterView(Main model) {
			this.setSize(TOTAL_WIDTH - SIMULATION_WIDTH,TOTAL_HEIGHT);
			this.setMaximumSize(new Dimension(TOTAL_WIDTH - SIMULATION_WIDTH,TOTAL_HEIGHT));
			this.setMinimumSize(new Dimension(TOTAL_WIDTH - SIMULATION_WIDTH,TOTAL_HEIGHT));
			this.setPreferredSize(new Dimension(TOTAL_WIDTH - SIMULATION_WIDTH,TOTAL_HEIGHT));
			this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.darkGray, Color.black));
			this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			
			panels = new ArrayList<ParameterPanel>();
			mf = new MFPanel(TOTAL_WIDTH - SIMULATION_WIDTH, TOTAL_HEIGHT / 6 + 100, "Maintain_Formation", model);
			g = new GoalPanel(TOTAL_WIDTH - SIMULATION_WIDTH, TOTAL_HEIGHT / 6 - 20, "Move_To_Goal", model);
			rp = new RobotPanel(TOTAL_WIDTH - SIMULATION_WIDTH, TOTAL_HEIGHT / 6 - 20, "Avoid_Robot", model);
			op = new ObsPanel(TOTAL_WIDTH - SIMULATION_WIDTH, TOTAL_HEIGHT / 6 - 20, "Avoid_Static_Obstacle", model);
			np = new NoisePanel(TOTAL_WIDTH - SIMULATION_WIDTH, TOTAL_HEIGHT / 6 - 20, "Noise", model);  
			bp = new ButtonPanel(TOTAL_WIDTH - SIMULATION_WIDTH, TOTAL_HEIGHT / 6 - 20, "button", model, this);
			this.add(mf);
			this.add(g);
			this.add(rp);
			this.add(op);
			this.add(np);
			this.add(bp);
			panels.add(mf);
			panels.add(g);
			panels.add(rp);
			panels.add(op);
			panels.add(np);
//			panels.add(bp);
		}
		
		public void notifyO() {
			for (ParameterPanel p : panels) {
				p.update();
			}
		}
		
		public double getMFGain() {
			return new Double(mf.gain.getText().trim());
		}
		public double getGoalGain() {
			return new Double(g.gain.getText().trim());
		}
		public double getNoiseGain() {
			return new Double(np.gain.getText().trim());
		}
		public double getStObsGain() {
			return new Double(op.gain.getText().trim());
		}
		public double getRoObsGain() {
			return new Double(rp.gain.getText().trim());
		}
		public int getDesiredSpacing(){
			return new Integer(mf.desiredSpacing.getText().trim());
		}
		public int getControlledZoneRadius() {
			return new Integer(mf.controlledZoneRadius.getText().trim());
		}
		public int getDeadZoneRadius() {
			return new Integer(mf.deadZoneRadius.getText().trim());
		}
		
		public Point getGoal(){
			return new Point(new Integer(g.x.getText().trim())-400, new Integer(g.y.getText().trim()));
		}
		public int getStaticSOI() {
			return new Integer(op.soi.getText().trim());
		}
		public int getRobotSOI() {
			return new Integer(rp.soi.getText().trim());
		}
		public Formation getFormation(){
			String f =(String)mf.formationList.getSelectedItem();
			if (f.equalsIgnoreCase("Line")) {
				return Formation.Line;
			}
			else if (f.equalsIgnoreCase("Column")) {
				return Formation.Column;
			}
			else if (f.equalsIgnoreCase("Wedge")) {
				return Formation.Wedge;
			}
			else {
				return Formation.Diamond;
			}
		}
		
		public String getMaintenanceScheme() {
			String s = (String)mf.schemeList.getSelectedItem();
			if (s.equalsIgnoreCase("leader reference")) {
				return "leader";
			}
			else if (s.equalsIgnoreCase("unit-centre reference")){
				return "unit";
			}
			else {
				return "neighbour";
			}
		}
		
	}
	}
}





