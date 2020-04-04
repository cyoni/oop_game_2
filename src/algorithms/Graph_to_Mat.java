package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;

public class Graph_to_Mat {

    private static  ArrayList<node_data>[][] paths;

	
	@SuppressWarnings("unchecked")
	public static double[][] convertGraphToMat(graph g) {
		double mat[][] = new double[g.nodeSize()][g.nodeSize()];
		paths = new ArrayList[g.nodeSize()][g.nodeSize()];
		for (double[] row : mat)  Arrays.fill(row, 0);
		Collection<node_data> nodes = g.getV();	
		// Initialize the mat with adjacent vertices 
		
		for (node_data current_node : nodes) {
			int i = current_node.getKey();
			Collection<edge_data> edges = g.getE(i);
/*			mat[i][i] = (int)current_node.getWeight();*/
			
			if (edges == null) continue; // if there are no edges in some vertex (possible when the graph is not connected)
			
			for (edge_data current_edge : edges) {
				int j = current_edge.getDest();
				mat[i][j] =  (current_edge.getWeight());
			}
		}
		
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				paths[i][j] = new ArrayList<>();
				if (mat[i][j] != 0) { paths[i][j].add(g.getNode(i)); paths[i][j].add(g.getNode(j));}
			}
		}

		
		return mat;
	}
	

}
