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
import dataStructure.Node_metadata;
import dataStructure.edge_data;
import dataStructure.edge_metadata;
import dataStructure.game_metadata;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;



	/**
	* This class loads/process/saves files
	* @author Yoni
	*/


public class file implements file_interface{

		@Override
		public void loadFile() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public graph processFile() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void saveFile(String l) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setLocation(String l) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getFilePath() {
			// TODO Auto-generated method stub
			return null;
		}
	/*
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


		*/
}