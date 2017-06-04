package GUI;

import java.awt.Point;

import model.Formation;
import model.Main;

/**
 * @author robbierolin
 * Launching class for project
 *
 */
public class Launch
{
	
	@SuppressWarnings("javadoc")
    public static void main(String[] args) {
	   
	    Formation f = Formation.Line;
	    Point goal = new Point(0, 650) ;
	    Main m = new Main(goal, f, "leader", 
	            1.5, 50, 5, 2.0, 20, 5, 1.0, 50, 25, 0, 0.8, 0.1, 6);
	    new GUI(m);
	}
}
