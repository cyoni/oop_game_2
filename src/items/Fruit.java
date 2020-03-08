package items;


import utils.Point3D;
/**
 * This class represents a fruit
 *
 *@author Yoni
 */


public class Fruit {
	private double value;
	private int type;
	private Point3D pos;
	private double z;
	
	public Fruit(double value, int type, Point3D p) {
		this.value = value;
		this.type = type;
		this.pos = p;
		}
	
	public Point3D getPos() {
		return pos;
	}
	
	
	
}
