package items;

import utils.Point3D;

/**
 * This class represents a robot
 * @author Yoni
 *
 */

public class Robot{
	
	private int speed;
	private Point3D pos;
	
	public Robot(Point3D pos) {
		this.pos = pos;
	}
	
	public Point3D getPos() {return pos;}
	public int getSpeed() {return speed;}
	
}