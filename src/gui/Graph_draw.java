package gui;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import algorithms.Graph_Algo;
import algorithms.converter;
import algorithms.line;
import dataStructure.DGraph;
import dataStructure.Node_metadata;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

	/**
	* This class draws graphs.
	* @author Yoni
	*/

public class Graph_draw {
	MyGameGUI g;
	
	public Graph_draw(MyGameGUI g) {this.g = g;}
	

	public void draw(graph myGraph)  {
    
    	//a.init(myGraph);
    	
    	@SuppressWarnings("unchecked")
		List<Integer>[] tmp_graph = new ArrayList[myGraph.nodeSize()];
    	for (int i = 0; i < tmp_graph.length; i++) {
			tmp_graph[i] = new ArrayList<>();
		}
    	
    	
	
		
		Collection<node_data> my_nodes = myGraph.getV();
		for (node_data i : my_nodes) {
			node_data currnet_node = i;
						
			double x = currnet_node.getLocation().x();
			double y = currnet_node.getLocation().y();
			
			// draw the first point of the edge
			g.setPenColor(g.BLUE);
			g.setPenRadius(0.02);
			g.point(x,y);
			
			// write the weight of the point
			g.setPenColor(g.BLUE);
			g.text(x, y+30, currnet_node.getKey()+""); 
			
			// get edges of the specific point
			 Collection<edge_data> edges_for_this_node = myGraph.getE(i.getKey());
			 
				for ( edge_data j : edges_for_this_node) {
					int dest = j.getDest();
				
					node_data node_dest = myGraph.getNode(dest);

					double loc[] = line.getPointOnLine(i.getLocation(), node_dest.getLocation(), 90); // draw a small dot to the edge you're going to

					g.setPenColor(g.YELLOW);
					g.setPenRadius(0.015);
					g.point(loc[0], loc[1]);
					
					if (tmp_graph[i.getKey()].contains(dest)) 	continue;
					
					// get weights
					//double node_weight_dest = myGraph.getNode(j.getDest()).getWeight();
					double edge_weight = j.getWeight();
					
					// draw the 2nd point of the edge
					//setPenColor(BLUE);
					//setPenRadius(0.02);
					//point(x2,y2);
					
					// draw the edge
					g.setPenRadius(0.001);
					g.setPenColor(g.BLACK);
					g.line(x, y, node_dest.getLocation().x(), node_dest.getLocation().y());
					
					// write the weights
					//setPenColor(BLUE);
					g.setPenColor(g.GRAY);
					
					g.text((x+node_dest.getLocation().x())/2, (y+node_dest.getLocation().y())/2 +1, String.format("%.1f", edge_weight));
					g.setPenColor(g.BLUE);

					tmp_graph[i.getKey()].add(dest);
					tmp_graph[dest].add(i.getKey());
				
				}	
		}
	}

}
