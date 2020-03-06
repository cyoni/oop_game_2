package algorithms;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
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
				setLocation("selectedFile.getAbsolutePath()");
			}
			
		}
		

		@Override
		public void processFile() {

			
		}

		@Override
		public void saveFile() {

			
		}

		@Override
		public void setLocation(String l) {
		this.file_location = l;
		}

		
		
		
		
	}