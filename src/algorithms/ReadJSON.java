package algorithms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dataStructure.DGraph;
import dataStructure.Node_metadata;
import dataStructure.edge_data;
import dataStructure.edge_metadata;
import dataStructure.game_metadata;
import dataStructure.graph;
import dataStructure.node_data;
import items.Fruit;

public class ReadJSON {
	
	private JFrame f;
	
	public ReadJSON(JFrame f) {this.f = f;}
	
	public game_metadata ReadJson_graphAndFruits(String jsonString, List<String> list_fruits) {

		
		if (jsonString.isEmpty()) return null;
		
		//game_metadata gmt = new game_metadata();
		List<edge_data> Edges = new ArrayList<>();
		List<node_data> Nodes = new ArrayList<>();
		List<Fruit> fruits = new ArrayList<>();
		
		try {
				JSONObject obj = new JSONObject(jsonString);
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
					

					Nodes.add(new Node_metadata(id, new converter(f).coordsToPixel(y, x), z));
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
				
				// get fruits
		JSONObject obj;
		if (!list_fruits.isEmpty()) {
		try {
			for (String item : list_fruits) {
			obj = new JSONObject(item.toString());
			
			double value = obj.getJSONObject("Fruit").getDouble("value");
			int type = obj.getJSONObject("Fruit").getInt(("type"));	
			String pos = obj.getJSONObject("Fruit").getString(("pos"));

			String point[] = pos.split(",");
			double x = Double.parseDouble(point[0]);
			double y = Double.parseDouble(point[1]);
			//double z = Double.parseDouble(point[2]);

			fruits.add(new Fruit(value, type, new converter(f).coordsToPixel(y, x)));
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		}
				/*
				// get fruits:
				
				if (jsonString.contains("Fruit")) {
				arr = obj.getJSONArray("Fruit"); // get data of Nodes
				for (int i = 0; i < arr.length(); i++) // get data of Edges
				{
					double value = arr.getJSONObject(i).getDouble(("value"));
					int type = arr.getJSONObject(i).getInt(("type"));	
					String pos = arr.getJSONObject(i).getString(("pos"));

					
					String point[] = pos.split(",");
					double x = Double.parseDouble(point[0]);
					double y = Double.parseDouble(point[1]);
					//double z = Double.parseDouble(point[2]);
	
					fruits.add(new Fruit(value, type, new converter(f).coordsToPixel(y, x)));
						}
					}
				 	*/
					
			return new game_metadata(g, fruits);
	}

}