package algorithms;

import dataStructure.graph;

/**
 * This interface represents a file.
 * 
 * @author Yoni
 **/

	public interface file_interface{  
		
		public void loadFile();
		public graph processFile();
		public void saveFile(String l);
		public void setLocation(String l);
		public String getFilePath();
		
		
	}