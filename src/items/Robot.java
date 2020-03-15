package items;

import utils.Point3D;

/**
 * This class represents a robot
 * @author Yoni
 *
 */

public class Robot extends Robot_metadata{


	public Robot(int id, double value, int src, int dest, int speed, Point3D pos) {
		super(id, value, src, dest, speed, pos);
	}
	

	public Robot getRobot() {return this;}
	
	
}