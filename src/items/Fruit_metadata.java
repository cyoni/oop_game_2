package items;

import utils.Point3D;

public class Fruit_metadata {
	
	private double value;
	private int type;
	private Point3D pos;
	
	public Fruit_metadata(double value, int type, Point3D p) {
		this.value = value;
		this.type = type;
		this.pos = p;
		}
	
	public Point3D getPos() {
		return pos;
	}
	
	public int getType() {
		return type;
	}
	
	public double getValue() {
		return value;
	}
	
}
