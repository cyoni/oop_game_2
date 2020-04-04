package items;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import dataStructure.node_data;
import utils.Point3D;

/**
 * This class represents a robot
 * @author Yoni
 *
 */

public class Robot {

	private Point3D pos;
	private double value;
	private int src, dest , speed, id;
	Queue<node_data> path_to_target;
	private PriorityQueue<Fruit> my_targets;
	private boolean busy;
	
	
	public Robot(int id, double value, int src, int dest, int speed, Point3D pos) {
		this.id = id;
		this.pos = pos;
		this.value = value;
		this.src = src;
		this.dest = dest;
		this.path_to_target = new LinkedList<node_data>();
		this.busy = false;
	}
	
	public Robot() {
		this.path_to_target = new LinkedList<node_data>();
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

	public void setPath_to_target(Queue<node_data> l) {
		
		path_to_target.addAll(l);
		System.out.println("New path has been added. The first item is: " + path_to_target.peek().getKey() + ". The path is:");

		for (node_data item : path_to_target) {
			System.out.print(item.getKey() + "->");
		}
			System.out.println();
			
		path_to_target.poll(); // remove head
	}
	
	public Queue<node_data> getPath_to_target() {return path_to_target;}


	public boolean isBusy() {
		return busy;
	}
	
	public void setIsBusy(boolean b) {
		this.busy = b;
	}
	

}