package items;


import java.util.List;

import algorithms.line;
import dataStructure.edge_data;
import dataStructure.graph;
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
	private graph g;
	private edge_data edge_of_fruit;
	
	public Fruit(double value, int type, Point3D p, graph g) {
		this.value = value;
		this.type = type;
		this.pos = p;
		this.g = g;
		findEdge();
		}
	
	private void findEdge() {
		List<edge_data> edges = g.getEdges();
		
		for (edge_data current_edge : edges) {
			Point3D p1 = g.getNode(current_edge.getSrc()).getLocation();
			Point3D p2 = g.getNode(current_edge.getDest()).getLocation();
			Point3D point_to_check = this.pos;
			
			//System.out.println(line.distanceBetweenLineAnd2Points(p1, p2, point_to_check)[0] + " ("+ p1.x() + "," + p1.y() + "  " + p2.x() +"," + p2.y());
			if (line.distanceBetweenLineAnd2Points(p1, p2, point_to_check)[0] < 2) {
				edge_of_fruit = current_edge;
				break;
				}
		}
		
		if (edge_of_fruit == null)
			throw new IllegalArgumentException("Could not find an edge to this fruit " + this.pos.x() +"," + this.pos.y());

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
	
	public edge_data getEdge() {
		return edge_of_fruit;
	}
	

	
}
