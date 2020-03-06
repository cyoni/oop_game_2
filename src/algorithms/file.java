package algorithms;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * 
	* This class loads/process/saves files
	*@author Yoni
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
	public void processFile() {
	if (getFilePath().isEmpty()) return;
		
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
				}
			arr = obj.getJSONArray("Nodes"); // get data of Nodes
			for (int i = 0; i < arr.length(); i++) // get data of Edges
			{
				String pos = arr.getJSONObject(i).getString(("pos"));
				int id = arr.getJSONObject(i).getInt(("id"));	

			}
		}
		
		 catch (JSONException e) {
				e.printStackTrace();
			}
	}

	@Override
	public void saveFile() {

			
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