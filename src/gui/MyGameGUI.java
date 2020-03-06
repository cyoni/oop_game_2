package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyGameGUI {

	public MyGameGUI(){
		
		    ImageIcon icon = new ImageIcon("background.png");
	    Image image = icon.getImage();
	    JPanel panel1 = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	        }
	        
	        @Override
	        public Dimension getPreferredSize() {
	            return new Dimension(1100, 400);
	        }
	    };
	    
	    JFrame f = new JFrame("Frame");
	    f.add((panel1),BorderLayout.CENTER);
	    f.pack();
	    f.setLocationRelativeTo(null); // open the window in the middle
	    f.setVisible(true);
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    
	    
	    
	    
	    
	}
	
	
	   public static void main(String[] args) {
		   new MyGameGUI();
	   }
	
	
	
	
}
