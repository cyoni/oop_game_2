package threads;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONObject;

import Server.game_service;
import algorithms.ReadJSON;
import algorithms.line;
import algorithms.split_string;
import dataStructure.edge_data;
import dataStructure.game_metadata;
import dataStructure.graph;
import dataStructure.node_data;
import gui.Game_board;
import gui.Graph_draw;
import gui.Todelete;
import items.Fruit;
import items.Robot;
import oop_dataStructure.OOP_DGraph;
import utils.Point3D;
import utils.StdDraw;


/**
 * The job of this thread is only to refresh the screen.
 * @author Yoni
 *
 */
public class Refresh_screen_thread extends Thread{
	

	private Point3D measures;
	private game_metadata game_mt;
	
	public Refresh_screen_thread(game_metadata game_mt, Point3D measures) {
	this.game_mt = game_mt;
	this.measures = measures;
	refresh();
	}
		
	public void run() {
	
			try {
				while (game_mt.service.isRunning()) {
					sleep(10);
					StdDraw.enableDoubleBuffering();					
					refresh();
				}
			}
			
			catch (Exception e) {
					e.printStackTrace();
				}

	}

	private void refresh() {
		// refresh background picture
		StdDraw.clear();
		//gameGui.picture(600, 500, "background.png", 1200, 1200);
		
		
		
		boolean flag = true;
		List<String> log = game_mt.service.move();
		if(log != null) {
			
		
			

			
			// refresh graph:
			
			Graph_draw gd = new Graph_draw();
			gd.draw(game_mt.getGraph());
			
			// refresh fruits:
			
			drawFruits();
			
			// refresh robots:
			
			List<Robot> list_robots = new ArrayList<>();
			long t = game_mt.service.timeToEnd();
			for(int i=0;i<log.size() && flag;i++) { // iterates over the robots
				String robot_json = log.get(i);
				//System.out.println(robot_json);
				
				ReadJSON rj = new ReadJSON(measures);
				Robot robot = rj.readRobot(robot_json);
				list_robots.add(robot);
				
				
				game_mt.updateRobots(list_robots);
				
				
				StdDraw.setPenColor(StdDraw.ORANGE);
				StdDraw.setPenRadius(0.04);
				StdDraw.point(robot.getPos().x(), robot.getPos().y());
			}
		
			StdDraw.show();
		
		}
	}

	private void drawFruits() {

		/**
		 * This function draws the fruits on the screen.
		 * Apples will be printed on edges that its edge increases e.g   2---->6
		 * Strawberries will be printed on edges that its edge decreases e.g  5---->2*/
	
			if (game_mt.getFruits().isEmpty()) return;

			graph Graph = game_mt.getGraph();
			List<Fruit> fruits = game_mt.getFruits();
			for (Fruit current_fruit : fruits) {
			
					if(current_fruit.getType() == -1) {
						StdDraw.picture(current_fruit.getPos().x(), current_fruit.getPos().y() , "banana.png", 30,60, 60);
					}
					else {
						StdDraw.picture(current_fruit.getPos().x(), current_fruit.getPos().y() , "apple.png", 30,60);

						}
			}

	
	}

	
}