package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import motor_schemas.Avoid_Robot;
import motor_schemas.Avoid_Static_Obstacle;
import motor_schemas.Maintain_Formation;
import motor_schemas.Motion_Vector;
import motor_schemas.Motor_Schema;
import motor_schemas.Move_To_Goal;
import motor_schemas.Noise;
import motor_schemas.Parameter;
import formation_maintenance.Formation_Maintenance_Scheme;
import formation_maintenance.Leader_Reference;
import formation_maintenance.Neighbour_Reference;
import formation_maintenance.Unit_Centre_Reference;

/**
 * Model of the system.
 *
 */
public class Main
{
	
	HashMap<Integer, Robot> robots;
	HashMap<Integer, Obstacle> obstacles;
	List<Point> map;
	Point nextWayPoint;
	Formation_Maintenance_Scheme fms;
	Formation f;
	static List<Motor_Schema> motorSchemas;
	static Main model;
	static long time;
	boolean finished;
	
	/**
	 * Constructor for new model
	 */
	@SuppressWarnings("javadoc")
    public Main(Point goal, Formation f, String formationScheme, 
			double gain1, int soi_static, int minRange_static, 
			double gain2, int soi_robot, int minRange_robot,
			double gain3, int desiredSpacing, int controlledZoneRadius, int deadZoneRadius, 
			double gain4, 
			double gain5, int persistence) {
		model = this;
		nextWayPoint = goal;
		this.f = f;
		obstacles = generateObstacles();
		setupRobots();
		initializeMaintenanceScheme(formationScheme, desiredSpacing);
		initializeMotorSchema(gain1, soi_static, minRange_static, gain2, soi_robot, 
				minRange_robot, gain3, desiredSpacing, controlledZoneRadius, deadZoneRadius, gain4, gain5, persistence);
		time = System.currentTimeMillis();
		finished = false;
		Thread modelThread = new Thread(new Runnable() {
			public void run()
			{
				while (!finished) {
                    move();
                    try {Thread.sleep(100);} catch (Exception ex) {}
                }
			}
		});
		modelThread.start();
	}
	
	/**
	 * Constructor for a model with known robots and obstacles	
	 */
	@SuppressWarnings("javadoc")
    public Main(Point goal, Formation f, String formationScheme, 
			double gain1, int soi_static, int minRange_static, 
			double gain2, int soi_robot, int minRange_robot,
			double gain3, int desiredSpacing, int controlledZoneRadius, int deadZoneRadius, 
			double gain4, 
			double gain5, int persistence, HashMap<Integer, Robot> robots, HashMap<Integer, Obstacle> obstacles) {
		model = this;
		nextWayPoint = goal;
		this.f = f;
		this.robots = robots;
		finished = false;
	    this.obstacles = obstacles;
		initializeMaintenanceScheme(formationScheme, desiredSpacing);
		initializeMotorSchema(gain1, soi_static, minRange_static, gain2, soi_robot, 
				minRange_robot, gain3, desiredSpacing, controlledZoneRadius, deadZoneRadius, gain4, gain5, persistence);
		time = System.currentTimeMillis();

		Thread modelThread = new Thread(new Runnable() {
			public void run()
			{
				while (!finished) {
                    move();
                    try {Thread.sleep(100);} catch (Exception ex) {}
                }
			}
		});
		modelThread.start();
	}
	/**
	 * Constructor for model with known obstacles
	 */
	@SuppressWarnings("javadoc")
    public Main(Point goal, Formation f, String formationScheme, 
			double gain1, int soi_static, int minRange_static, 
			double gain2, int soi_robot, int minRange_robot,
			double gain3, int desiredSpacing, int controlledZoneRadius, int deadZoneRadius, 
			double gain4, 
			double gain5, int persistence, HashMap<Integer, Obstacle> obstacles) {
		model = this;
		nextWayPoint = goal;
		this.f = f;
		setupRobots();
		this.obstacles = obstacles;
		initializeMaintenanceScheme(formationScheme, desiredSpacing);
		initializeMotorSchema(gain1, soi_static, minRange_static, gain2, soi_robot, 
				minRange_robot, gain3, desiredSpacing, controlledZoneRadius, deadZoneRadius, gain4, gain5, persistence);
		finished = false;
		time = System.currentTimeMillis();
		Thread modelThread = new Thread(new Runnable() {
			public void run()
			{
				while (!finished) {
                    move();
                    try {Thread.sleep(100);} catch (Exception ex) {}
                }
			}
		});
		modelThread.start();
	}
	
	private void end() {
	    finished = true;
	}
	
	
	private void initializeMaintenanceScheme(String formationScheme, int desiredSpacing){
		if (formationScheme.equalsIgnoreCase("leader"))
		{
			this.fms = new Leader_Reference(f, desiredSpacing);
		}
		else if (formationScheme.equalsIgnoreCase("unit"))
		{
			this.fms = new Unit_Centre_Reference(f, desiredSpacing);
		}
		else
		{
			this.fms = new Neighbour_Reference(f, desiredSpacing);
		}		
	}
	private void setupRobots(){
		robots = new HashMap<Integer, Robot>();
		for (int i = 1; i <= 4; i++) {
			Robot r = new Robot(i, this);
			robots.put(i, r);
		}
	}
	
	private void initializeMotorSchema(double gain1, int soi_static, int minRange_static, 
			double gain2, int soi_robot, int minRange_robot,
			double gain3, int desiredSpacing, int controlledZoneRadius, int deadZoneRadius, 
			double gain4, 
			double gain5, int persistence) {
		motorSchemas = new ArrayList<Motor_Schema>();
		Parameter soi = new Parameter(soi_static, "metres");
		Parameter minRange = new Parameter(minRange_static, "metres");
		List<Parameter> st_obs_params = new ArrayList<Parameter>();
		st_obs_params.add(soi);
		st_obs_params.add(minRange);
		motorSchemas.add(new Avoid_Static_Obstacle(gain1, st_obs_params));
		
		List<Parameter> ro_obs_params = new ArrayList<Parameter>();
		soi = new Parameter(soi_robot, "metres");
		minRange = new Parameter(minRange_robot, "metres");
		ro_obs_params.add(soi);
		ro_obs_params.add(minRange);
		motorSchemas.add(new Avoid_Robot(gain2, ro_obs_params));
		
		List<Parameter> form_params = new ArrayList<Parameter>();
		Parameter DesiredSpacing = new Parameter(desiredSpacing, "metres");
		Parameter ControlledZoneRadius = new Parameter(controlledZoneRadius, "metres");
		Parameter DeadZoneRadius = new Parameter(deadZoneRadius, "metres");
		form_params.add(DesiredSpacing);
		form_params.add(ControlledZoneRadius);
		form_params.add(DeadZoneRadius);
		motorSchemas.add(new Maintain_Formation(gain3, form_params, fms));
		
		motorSchemas.add(new Move_To_Goal(gain4, nextWayPoint));
		
		Parameter Persistence = new Parameter(persistence, "time steps");
		motorSchemas.add(new Noise(gain5, Persistence));
	}
	
	
	private HashMap<Integer, Motion_Vector> getMovements() {
		HashMap<Integer, Motion_Vector> ret = new HashMap<Integer, Motion_Vector>();
		for (Robot r : robots.values()) {
			ret.put(r.getID(), r.getResultingBehaviour(motorSchemas, robots));
		}
		return ret;
	}
	
	private void move(){
		HashMap<Integer, Motion_Vector> moves = getMovements();
		for (int i : moves.keySet()) {
			Robot r = robots.get(i);
			r.move(moves.get(i));
		}
	}

	/**
	 * @return List of the current points of the robots
	 */
	public List<Point> getRobotPoints() {
		List<Point> ret = new ArrayList<Point>();
		for (Robot r : robots.values()) {
			ret.add(r.getPOS());
		}
		return ret;
	}
	
	/**
	 * @return HashMap of the obstacles currently in the model
	 */
	public HashMap<Integer, Obstacle> getObstacles() {
		return getModel().obstacles;
	}
	/**
	 * @return HashMap of the robots
	 */
	public HashMap<Integer, Robot> getRobots() {
		return getModel().robots;
	}
	/**
	 * @return Goal point of the robots
	 */
	public Point getGoal(){
		return getModel().nextWayPoint;
	}
	private static HashMap<Integer, Obstacle> generateObstacles() {
		int index = 1;
		Random r = new Random();
		HashMap<Integer, Obstacle> ret = new HashMap<Integer, Obstacle>();
		while ( index < 7) {
			double gauss1 = 20*r.nextGaussian();
			double gauss2 = 20*r.nextGaussian();
			double height = 80 + gauss1;
			double width = 80 + gauss2;
			//randomly pick bottom left corner
			
			int x = r.nextInt(800- (int)width) - 400;
			int y = r.nextInt(700 -(int) height);
			HashSet<Point> obstacle = new HashSet<Point>();
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					obstacle.add(new Point(x + i, y + j));
				}
			}
			ret.put(index, new Obstacle(obstacle));
			index++;
		}
		return ret;
	}
	
	/**
	 * @return current intended formation of robots
	 */
	public Formation getFormation() {
		return getModel().f;
	}
	/**
	 * @return Formation Maintenance Scheme currently being used
	 */
	public Formation_Maintenance_Scheme getFMS() {
		return getModel().fms;
	}
		
	/**
	 * @param spec - specification of which motor schema is wanted
	 * @return - the implementation of the motor schema currently being used (ie with the parameters input)
	 */
	@SuppressWarnings("static-access")
	public Motor_Schema getMotorSchema(String spec) {
		for (Motor_Schema ms : getModel().motorSchemas) {
			if (spec.equals("noise")) {
				if (ms instanceof Noise) {
					return ms;
				}
			}
			else if (spec.equals("static_obstacle")) {
				if (ms instanceof Avoid_Static_Obstacle) {
					return ms;
				}
			}
			else if (spec.equals("robot_obstacle")) {
				if(ms instanceof Avoid_Robot) {
					return ms;
				}
			}
			else if (spec.equals("goal")) {
				if (ms instanceof Move_To_Goal) {
					return ms;
				}
			}
			else if (spec.equals("formation")) {
				if (ms instanceof Maintain_Formation) {
					return ms;
				}
			}
		}
		return null;
	}
	
	/**
	 * Resets the model with new obstacles and with default choices of formation and formation schemes
	 */
	public static void reset() {
	    model.end();
		model = new Main(new Point(0,650), Formation.Line, "leader", 
				1.5, 50, 5, 2.0, 20, 5, 1.0, 50, 25, 0, 0.8, 0.1, 6);
	}
	
	/**
	 * Restarts the model with the parameters given.
	 */
	@SuppressWarnings("javadoc")
    public static void restart(Point goal, Formation f, String formationScheme, 
			double gain1, int soi_static, int minRange_static, 
			double gain2, int soi_robot, int minRange_robot,
			double gain3, int desiredSpacing, int controlledZoneRadius, int deadZoneRadius, 
			double gain4, 
			double gain5, int persistence) {
	    model.end();
		model = new Main(goal, f, formationScheme, gain1, soi_static, minRange_static, gain2, soi_robot, minRange_robot, gain3,
				desiredSpacing, controlledZoneRadius, deadZoneRadius, gain4, gain5, persistence, model.getObstacles());
	}
	
	/**
	 * Applies the input parameters without restarting the position of the robots
	 */
	@SuppressWarnings("javadoc")
    public static void apply(Point goal, Formation f, String formationScheme, 
			double gain1, int soi_static, int minRange_static, 
			double gain2, int soi_robot, int minRange_robot,
			double gain3, int desiredSpacing, int controlledZoneRadius, int deadZoneRadius, 
			double gain4, 
			double gain5, int persistence) {
	    model.end();
		model = new Main(goal, f, formationScheme, gain1, soi_static, minRange_static, gain2, soi_robot, minRange_robot, gain3,
				desiredSpacing, controlledZoneRadius, deadZoneRadius, gain4, gain5, persistence, model.getRobots(), model.getObstacles());
	}
	/**
	 * @return the current version of the model
	 */
	public static Main getModel() {
		return model;
	}
}
