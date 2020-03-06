package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import algorithms.file;

public class MyGameGUI implements ActionListener {
	
	file _file;
	JFrame f;
	
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
	    
	    f = new JFrame("Game");
		_file = new file(f);
	    // set menu:
	    JMenuBar mb; 
	    
        // create a menu bar 
        mb = new JMenuBar(); 
  
        // create a menu 
        JMenu x = new JMenu("Menu"); 
        JMenu x2 = new JMenu("Game"); 


        // create menu items 
        JMenuItem m1 = new JMenuItem("Load map..."); 
        JMenuItem m2 = new JMenuItem("Exit"); 

        JMenuItem m3 = new JMenuItem("Start");

  
        // add ActionListener to menuItems 
        m1.addActionListener(this); 
        m2.addActionListener(this); 

        // add menu items to menu 
        x.add(m1); 
        x.add(m2); 
        
        x2.add(m3);
   
        // add menu to menu bar 
        mb.add(x); 
        mb.add(x2);
  
        // add menu bar to frame 
        f.setJMenuBar(mb); 
	    
	    f.add((panel1),BorderLayout.CENTER);
	    f.pack();
	    f.setLocationRelativeTo(null); // open the window in the middle
	    f.setVisible(true);
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    
	    // FOR DEVELOPMENT PURPOSES ONLY
	    
	    _file.setLocation("C:\\Users\\Yoni\\git\\oop_game_2\\data\\A0");
	    _file.processFile();
	    
	}
	
	
	   public static void main(String[] args) {
		   new MyGameGUI();
	   }


	@Override
	public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand(); 
        
        if (s.equals("Load map...")) {_file.loadFile(); _file.processFile();}
        
        System.out.println(s + " selected");
	}
	
	
	
	
}
