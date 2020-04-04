package threads;

import java.awt.Color;
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
public class Refresh_thread extends Thread{
	private Point3D measures;
	private game_metadata game_mt;
	private List<Fruit> my_fruits; // to check for new fruits
	
	public Refresh_thread(game_metadata game_mt, Point3D measures) {
	this.game_mt = game_mt;
	this.measures = measures;
	my_fruits = new ArrayList<>();
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
		
		List<String> log = game_mt.service.move();
		if(log != null) {
			checkForNewFruits();
			// refresh graph:
			Graph_draw gd = new Graph_draw();
			gd.draw(game_mt.getGraph());
			// refresh fruits:
			refreshFruits();
			refreshGameTime();
			// refresh robots:
			refreshRobots(log);
			StdDraw.show();
		
		}
	}

	private void refreshGameTime() {
		long seconds = game_mt.service.timeToEnd();
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.text(1100, 750, "Time: " + seconds/1000);
	}

	private void checkForNewFruits() {
		 game_mt.fruits = game_mt.getFruits(); // refresh list
		for (int i = 0; i < game_mt.fruits.size(); i++) { // check for new fruits and then add them to available fruit list:

			boolean found = false;
			Fruit current_fruit = game_mt.fruits.get(i);
			for (int j = 0; j < my_fruits.size(); j++) {
				if (current_fruit.getPos().x() == my_fruits.get(j).getPos().x() && 
						current_fruit.getPos().y() == my_fruits.get(j).getPos().y()) found = true;
			}
			if (!found)  // not found = new fruit from server
			{
				System.out.println("NEW FRUIT! " + current_fruit.getPos().x() + "," + current_fruit.getPos().y() + "; At " + 
						current_fruit.getEdge().getSrc() + "," + current_fruit.getEdge().getDest());
				my_fruits.add(current_fruit);
				game_mt.available_fruits.add(current_fruit);
			}
		}
		
		for (int i = 0; i < my_fruits.size(); i++) { // remove old fruits
			boolean found = false;
			Fruit current_fruit = my_fruits.get(i);
			for (int j = 0; j < game_mt.fruits.size(); j++) {
				if (current_fruit.getPos().x() == game_mt.fruits.get(j).getPos().x() && 
						current_fruit.getPos().y() == game_mt.fruits.get(j).getPos().y()) found = true;
			}
			if (!found)  // not found = new fruit from server
			{
				System.out.println("REMOVED FRUIT " + current_fruit.getPos().x() + "," + current_fruit.getPos().y() + "; At " + 
						current_fruit.getEdge().getSrc() + "," + current_fruit.getEdge().getDest());
				my_fruits.remove(current_fruit);
				game_mt.available_fruits.remove(current_fruit); // just in case it still exists there
			}
		}
		
		
	}

	private synchronized void refreshRobots(List<String> log) {
		boolean flag = true;
		List<Robot> list_robots = new ArrayList<>();
		long t = game_mt.service.timeToEnd();
		for(int i=0;i<log.size() && flag;i++) { // iterates over the robots
			String robot_json = log.get(i);
			//System.out.println(robot_json);
			
			ReadJSON rj = new ReadJSON(measures);
			Robot robot = rj.readRobot(robot_json);
		//	System.out.println(robot_json);
			list_robots.add(robot);
					
			
			StdDraw.setPenColor(StdDraw.ORANGE);
			StdDraw.setPenRadius(0.04);
			StdDraw.point(robot.getPos().x(), robot.getPos().y());
		}	
		game_mt.updateRobots(list_robots);

	}

	private void refreshFruits() {

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