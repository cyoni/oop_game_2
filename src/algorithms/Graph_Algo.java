package algorithms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import gui.Node_metadata;
import utils.Point3D;
/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms , Serializable{

	private file _file;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6772723883559432038L;
	
	
	private static graph g;
	private FW fw;
	
	@Override
	public void init(graph g) {
		Graph_Algo.g = g;
		fw = new FW(g);
	}

	@Override
	public void init(String file_name) {
		_file.setLocation(file_name);
		_file.processFile();
	}

	@Override
	public void save(String file_name) {
		_file.saveFile(file_name);
	}

	@Override
	public boolean isConnected() {
		Collection<node_data> nodes = g.getV();
		boolean[] visited = new boolean[g.nodeSize()]; 
		int parent[] = new int[g.nodeSize()];
		LinkedList<node_data> queue = new LinkedList<>();
		node_data[] nodesArray = nodes.toArray(size -> new node_data[size]); 	//	node_data[] nodesArray = nodes.toArray(node_data[]::new);
		node_data current = nodesArray[0];
		
		// Mark the current node as visited and enqueue it 
			visited[current.getKey()]=true; 
			queue.add(nodesArray[current.getKey()]); 
			parent[current.getKey()] = -1;
			while (queue.size() != 0) 
			{ 
				// Dequeue a vertex from queue and print it 
				current = queue.poll(); 
				
				// Get all adjacent vertices of the dequeued vertex s 
				// If a adjacent has not been visited, then mark it 
				// visited and enqueue it 
				 for (edge_data neighbor : g.getE(current.getKey())) { 
					 int id_of_neighbor = neighbor.getDest(); // source(current) ------> (id_of_neighbor)
						if (visited[id_of_neighbor]==false) 
						{ 
							//dis[n] = dis[s] + 1;
							parent[id_of_neighbor] = id_of_neighbor;
							visited[id_of_neighbor] = true; 
							queue.add(g.getNode(id_of_neighbor)); 
						} 
				 }
			
			} 
			for (boolean what : visited) if (!what) return false;
		return true;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		double mat[][] = fw.getMat();
		
		return mat[src][dest];
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		return fw.getShortestPath(src, dest);
	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {
		return fw.getShortestPathWithTarget(targets);
	}

	@Override
	public graph copy() {
		try {
        //Serialization of object
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(g);
        
        //De-serialization of object
        ByteArrayInputStream bis = new   ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bis);
        return (graph) in.readObject();

		}
		catch (Exception e) {
			System.out.println("exception. " + e);
		}
		return null;
	}
}
