package dataStructure;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class DGraph implements graph, Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8995919428111032917L;
	
	private Map<Integer, node_data> g;
	private Map<Integer, List<edge_data>> e;

	public DGraph() {
		g = new HashMap<>();
		e = new HashMap<>();
	}
	
	@Override
	public node_data getNode(int key) {
		return g.get(key); 
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		return e.get(src).get(dest);
	}

	@Override
	public void addNode(node_data n) {
		g.put(n.getKey(), n);
	}

	@Override
	public void connect(int src, int dest, double w) {

		edge_metadata edge_new = new edge_metadata(src, dest, w);
		
		List<edge_data> list = e.get(src);
		if (list == null) list = new ArrayList<>();
		list.add(edge_new);
		e.put(src, list);

		
/*		list = e.get(dest);
		if (list == null) list = new ArrayList<>();
		edge_new = new edge_metadata(dest, src, w);
		list.add(edge_new);
		e.put(dest, list);*/
				
	}

	@Override
	public Collection<node_data> getV() {
		return (Collection<node_data>) g.values() ;
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		return e.get(node_id);
	}

	@Override
	public node_data removeNode(int key) {
		List<edge_data> old_node_edges = e.get(key);
		g.remove(key); // remove the node
		e.remove(key); //  remove its edges	
		for (edge_data edge : old_node_edges) { // remove the edges that connect with him from his neighbors 
			int dest = edge.getDest();
			List<edge_data> l = e.get(dest); 
			for (int i=0; i< l.size(); i++) {
				edge_data edge_to_check = l.get(i);
				if (edge_to_check.getDest() == key)   {e.get(dest).remove(edge_to_check); i--;} 
			}
		}
		return null;
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		e.remove(src);
		e.remove(dest);
		return null;
	}

	@Override
	public int nodeSize() {
		return g.size();
	}

	@Override
	public int edgeSize() {
		return e.size()/2;
	}

	@Override
	public int getMC() {
		// TODO Auto-generated method stub
		return -100;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<edge_data>[] getArrayOfVertciesWithEdges() {
		List<edge_data>[] array_graph = new ArrayList[this.g.size()];
    	for (int i = 0; i < array_graph.length; i++) array_graph[i] = new ArrayList<>();
		
    	
    	for (int i = 0; i < array_graph.length; i++) {
    		array_graph[i].addAll(this.getE(i));
		}
		return array_graph;
	}

}
