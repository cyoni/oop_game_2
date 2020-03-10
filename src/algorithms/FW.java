package algorithms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;

public class FW implements Serializable{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -731345878701854509L;
	private int infinity = Integer.MAX_VALUE;
	private double mat[][];
	private graph g;
    private  ArrayList<node_data>[][] paths;

	
	public FW(graph g) {
		this.g = g;
		initMat();
	}       
	

	@SuppressWarnings("unchecked")
	private void initMat() {
		mat = new double[g.nodeSize()][g.nodeSize()];
		paths = new ArrayList[g.nodeSize()][g.nodeSize()];
		for (double[] row : mat)  Arrays.fill(row, infinity);
		
		Collection<node_data> nodes = g.getV();	
		// Initialize the mat with adjacent vertices 
		for (node_data current_node : nodes) {
			int i = current_node.getKey();
			Collection<edge_data> edges = g.getE(i);
			mat[i][i] = current_node.getWeight();
			
			if (edges == null) continue; // if there is no edges in some vertex (possible when the graph is not connected)
			
			for (edge_data current_edge : edges) {
				int j = current_edge.getDest();
				mat[i][j] = 2*current_edge.getWeight();
			}
		}
		
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				paths[i][j] = new ArrayList<>();
				if (mat[i][j] != infinity) { paths[i][j].add(g.getNode(i)); paths[i][j].add(g.getNode(j));}
			}
		}
		
		// convert weights of vertices to edges
		convertVertexToEdge();
		// update the mat with FW
		startFW();
		// correct the mat with the weights of the vertices:
		correctMatrix();
		printMat();
	}
	
	private void correctMatrix() {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				
				if (mat[i][j] != infinity && i != j) mat[i][j] = (mat[i][i] + mat[j][j] + mat[i][j]) / 2;
				}
			}		
	}



	private void convertVertexToEdge() {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j] != infinity && i!=j)
					mat[i][j] = mat[i][j] + mat[i][i] + mat[j][j];
			}	
		}		
	}



	private void printMat() {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				System.out.print(mat[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}


	private void startFW(){
		int n = mat.length;
		for (int k = 0; k<n; k++){
			for (int i = 0; i<n; i++){
				for (int j = 0; j<n; j++){
					if (i!=j && mat[i][k] != infinity && mat[k][j] != infinity){
						if (mat[i][j] > mat[i][k] + mat[k][j]){
							mat[i][j] = mat[i][k] + mat[k][j];
							paths[i][j].clear();
							paths[i][j].addAll(paths[i][k]);
							paths[i][j].addAll(paths[k][j]);
						}
					}
				}
			}
		}
	}

	public double[][] getMat() {
		return mat;
	}



	public List<node_data> getShortestPath(int src, int dest) {
		ArrayList<node_data> shortestPath = paths[src][dest];
		return shortestPath; 
	}



	// remove duplicates
	private List<node_data> shortestPath_without_duplicates(List<node_data> shortestPath) {
		List<node_data> shortestPath_without_duplicates = new ArrayList<>();
		int visited[] = new int[g.nodeSize()];
		Arrays.fill(visited, -1);
    	for (int i=0; i< shortestPath.size(); i++) {
    		int key = shortestPath.get(i).getKey();
    		if (visited[key] == 1) continue;
    		visited[key] = 1;
    		shortestPath_without_duplicates.add(shortestPath.get(i));
    	}	
		return shortestPath_without_duplicates;
	}



	public List<node_data> getShortestPathWithTarget(List<Integer> targets) {
		List<node_data> list = new ArrayList<>();
		if (targets.isEmpty()) {System.out.println("targets list is empty"); return null;}
		node_data arr[] = new node_data[targets.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = g.getNode(targets.get(i));
		}
		node_data leader_node = arr[0];  // get the first node in the list
		for (int i=1; i< arr.length; i++) {
		List<node_data> shorestPathList = getShortestPath(leader_node.getKey(), arr[i].getKey());
		list.addAll(shorestPathList);
			for (int j = 1; j < shorestPathList.size() ; j++) {
			mat[shorestPathList.get(j).getKey()][shorestPathList.get(j-1).getKey()] = 0;
			mat[shorestPathList.get(j-1).getKey()][shorestPathList.get(j).getKey()] = 0;
			}
			convertVertexToEdge();
			// update the mat with FW
			startFW();
			// correct the mat with the weights of the vertices:
			correctMatrix();
		}

		return shortestPath_without_duplicates(list);
	}
}
