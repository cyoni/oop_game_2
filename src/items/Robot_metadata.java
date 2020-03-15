package items;

import utils.Point3D;

public class Robot_metadata {

	private Point3D pos;
	private double value;
	private int src, dest , speed, id;
	
	public Robot_metadata(int id, double value, int src, int dest, int speed, Point3D pos) {
		this.id = id;
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
	
	public int getID() { return id;}

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
