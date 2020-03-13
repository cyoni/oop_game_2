package items;

import utils.Point3D;

/**
 * This class represents a robot
 * @author Yoni
 *
 */

public class Robot{
	
	private Point3D pos;
	private double value;
	private int src, dest , speed;
	
	public Robot(int id, double value, int src, int dest, int speed, Point3D pos) {
		this.pos = pos;
		this.value = value;
		this.src = src;
		this.dest = dest;
	}
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getSrc() {
		return src;
	}

	public void setSrc(int src) {
		this.src = src;
	}

	public int getDest() {
		return dest;
	}

	public void setDest(int dest) {
		this.dest = dest;
	}



	public void setPos(Point3D pos) {
		this.pos = pos;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Point3D getPos() {return pos;}
	public int getSpeed() {return speed;}
	
}