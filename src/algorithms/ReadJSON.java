package algorithms;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.edge_metadata;
import dataStructure.game_metadata;
import dataStructure.graph;
import dataStructure.node_data;
import gui.Todelete;
import gui.Node_metadata;
import items.Fruit;
import items.Robot;
import utils.Point3D;

public class ReadJSON {
	Point3D f;
	 
	 public ReadJSON(Point3D measures) {
		 this.f = measures;

	 }
	 
	public  Robot readRobot(String robot) {
		Robot r = null;
		try {
		JSONObject obj = new JSONObject(robot.toString()).getJSONObject("Robot");
		
		int id = obj.getInt(("id"));	
		double value = obj.getDouble("value");
		int src = obj.getInt(("src"));
		int dest = obj.getInt(("dest"));
		int speed = obj.getInt(("speed"));
		String pos = obj.getString(("pos"));

			r = new Robot(id, value, src, dest, speed, split_string.get_pos_frm_str_and_convert(f, pos));
		}
		catch(Exception c) {}
		
			return r;
	}
	

	public  graph readGraph(String str_graph) { // reads nodes and edges


		List<edge_data> Edges = new ArrayList<>();
		List<node_data> Nodes = new ArrayList<>();
		try {
				JSONObject obj = new JSONObject(str_graph);
				JSONArray arr = obj.getJSONArray("Edges");
				for (int i = 0; i < arr.length(); i++) // get data of Edges
					{
						int src = arr.getJSONObject(i).getInt(("src"));
						double w = arr.getJSONObject(i).getDouble(("w"));
						int dest = arr.getJSONObject(i).getInt(("dest"));
						Edges.add(new edge_metadata(src, dest, w));
					}
				arr = obj.getJSONArray("Nodes"); // get data of Nodes
				for (int i = 0; i < arr.length(); i++) // get data of Edges
				{
					String pos = arr.getJSONObject(i).getString(("pos"));
					int id = arr.getJSONObject(i).getInt(("id"));	
					String point[] = pos.split(",");
					double x = Double.parseDouble(point[0]);
					double y = Double.parseDouble(point[1]);
					double z = Double.parseDouble(point[2]);
					Nodes.add(new Node_metadata(id, new converter(f).coordsToPixel(y, x)));
					}
				}
			 catch (JSONException e) {
					e.printStackTrace();
				}
		
				graph g = new DGraph();
				
				for (int i = 0; i < Nodes.size(); i++) {
					g.addNode(Nodes.get(i));
				}
				for (int i = 0; i < Edges.size(); i++) {
					g.connect(Edges.get(i).getSrc(), Edges.get(i).getDest(), Edges.get(i).getWeight());
				}
		
		
		return g;
	}

	public List<Fruit> readFruits(List<String> str_fruits, graph g) {
			List<Fruit> fruits = new ArrayList<>();
			JSONObject obj; 
			if (!str_fruits.isEmpty()) {
			try {
				for (String item : str_fruits) {
				obj = new JSONObject(item.toString());
				
				double value = obj.getJSONObject("Fruit").getDouble("value");
				int type = obj.getJSONObject("Fruit").getInt(("type"));	
				String pos = obj.getJSONObject("Fruit").getString(("pos"));
	
				String point[] = pos.split(",");
				double x = Double.parseDouble(point[0]);
				double y = Double.parseDouble(point[1]);
				//double z = Double.parseDouble(point[2]);
								
				Fruit _fruit = new Fruit(value, type, new converter(f).coordsToPixel(y, x), g);
				fruits.add(_fruit);
				
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			return fruits;		
	}

	public  List<Robot> readRobots(List<String> robots) {
		List<Robot> list_robots = new ArrayList<>();
		
		for (String robot : robots) {
			list_robots.add(readRobot(robot));
		}
		return list_robots;
	}

}