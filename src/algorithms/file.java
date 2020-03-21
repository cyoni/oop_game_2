package algorithms;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
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
import gui.Node_metadata;
import utils.Point3D;



	/**
	* This class loads/process/saves files
	* @author Yoni
	*/

public class file implements file_interface{
	private JFrame f;
	private String file_location;

	public file(JFrame f) {
		this.f = f;
	}
	
	public file(String l) {
		setLocation(l);
	}
		
	@Override
	public void loadFile() {
	JFileChooser fileChooser = new JFileChooser();
	fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
	int result = fileChooser.showOpenDialog(f);
		if (result == JFileChooser.APPROVE_OPTION) { // user selects a file
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			setLocation(selectedFile.getAbsolutePath());
			} 
		}
		

	@Override
	public graph processFile() {
	if (getFilePath().isEmpty()) return null;
		
	//game_metadata gmt = new game_metadata();
	List<edge_data> Edges = new ArrayList<>();
	List<node_data> Nodes = new ArrayList<>();
	
	String data = "";
	try {
		data = new String(Files.readAllBytes(Paths.get(getFilePath())));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	System.out.println(data);
	
		
	try {
			JSONObject obj = new JSONObject(data);
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
	
	//	return new game_metadata(Edges, Nodes, null);
			return g;
	}

	@Override
	public void saveFile(String f) {

	       String filename = f + ".ser";
	  	 
	        // save the object to file
	        FileOutputStream fos = null;
	        ObjectOutputStream out = null;
	        try {
	            fos = new FileOutputStream(filename);
	            out = new ObjectOutputStream(fos);
	            out.writeObject(null ); // TODO

	            out.close();
	        } catch (Exception ex) {
	        	System.out.println("error.");
	            ex.printStackTrace();
	        }
	        System.out.println("done");
	}

		@Override
	public void setLocation(String l) {
	this.file_location = l;
	}
		
	@Override
	public String getFilePath() {
	return file_location;
	}


		
}