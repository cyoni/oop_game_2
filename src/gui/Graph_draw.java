package gui;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import algorithms.Graph_Algo;
import algorithms.math;
import dataStructure.DGraph;
import dataStructure.Node_metadata;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
import utils.StdDraw;

	/**
	* This class draws graphs.
	* @author Yoni
	*/

public class Graph_draw {
	
	public void draw(graph myGraph)  {	
		math m = new math();
    	Graph_Algo a = new Graph_Algo();
    	a.init(myGraph);
		StdDraw.setCanvasSize(500, 500);
		StdDraw.setScale(0, 100);
		StdDraw.setFont(new Font("Arial", 10, 15));
		Collection<node_data> my_nodes = myGraph.getV();
		for (node_data i : my_nodes) {
			node_data currnet_node = i;
						
			double x = currnet_node.getLocation().x();
			double y = currnet_node.getLocation().y();
			
			// draw the first point of the edge
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setPenRadius(0.02);
			StdDraw.point(x,y);
			
			// write the weight of the point
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.text(x, y+2, currnet_node.getKey() +" (" + currnet_node.getWeight() + ")");
			
			// get edges of the specific point
			 Collection<edge_data> edges_for_this_node = myGraph.getE(i.getKey());
			 
				for ( edge_data j : edges_for_this_node) {
					int dest = j.getDest();
					// FIXME - improve the algorithm
					node_data node_dest = myGraph.getNode(dest);
					double x2 = node_dest.getLocation().x();
					double y2 = node_dest.getLocation().y();
					double loc[] = m.getPointOnLine(x, y, x2, y2, 90); // draw a small circle to the edge you're going to
					StdDraw.setPenColor(StdDraw.YELLOW);
					StdDraw.setPenRadius(0.03);
					StdDraw.point(loc[0], loc[1]);
					
					// get weights
					double node_weight_dest = myGraph.getNode(j.getDest()).getWeight();
					double edge_weight = j.getWeight();
					
					// draw the 2nd point of the edge
					StdDraw.setPenColor(StdDraw.RED);
					StdDraw.setPenRadius(0.02);
					StdDraw.point(x2,y2);
					
					// draw the edge
					StdDraw.setPenRadius(0.005);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.line(x, y, x2, y2);
					
					// write the weights
					StdDraw.setPenColor(StdDraw.BLUE);
					StdDraw.setPenColor(StdDraw.PINK);
					StdDraw.text((x+x2)/2, (y+y2)/2 +1, edge_weight+"");
					StdDraw.setPenColor(StdDraw.BLUE);
					StdDraw.text(x2, y2+2, j.getDest() +" ("+ node_weight_dest +")");

				}
		}
	}

}
