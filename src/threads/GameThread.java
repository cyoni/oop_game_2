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
import gui.MyGameGUI;
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
public class GameThread extends Thread{
	
	private MyGameGUI gameGui;
	private game_metadata game_mt;
	private Game_board game_board;
	
	public GameThread(game_metadata game_mt, MyGameGUI gameGui) {
	this.game_mt = game_mt;
	this.gameGui = gameGui;
	}
		
	public void run() {
					
			try {
				while (game_mt.service.isRunning()) {
					sleep(10);
					
					gameGui.enableDoubleBuffering();					
					refresh();
				}

		/*		System.out.println("Thread ID " + getId() + " started!");
			while (game.isRunning()) {
				sleep(10);
				gameGui.enableDoubleBuffering();

				boolean flag = true;
				List<String> log = game.move();
				if(log != null) {
					long t = game.timeToEnd();
					for(int i=0;i<log.size() && flag;i++) {
						String robot_json = log.get(i);
						System.out.println(robot_json);
						Robot game_robot = ReadJSON.readRobot(robot_json);
						
						if (game_robot.getID() == getRobot().getID()) {
							flag = false;
									
							//gameGui.clear();

							game_board.drawObjects();
							updateRobot(game_robot);


							
							//gameGui.picture(game_robot.getPos().x(), game_robot.getPos().y() , "robot.png", 30,60);
							
							gameGui.setPenColor(gameGui.ORANGE);
							gameGui.setPenRadius(0.04);
							gameGui.point(getRobot().getPos().x(), getRobot().getPos().y());
							gameGui.pause(50);
							
							gameGui.show();
							if (getRobot().getDest() == -1) {} // go to sleep until the user chooses an edge.
								else { //the specific robot is on a node.	
	
									System.out.println("Turn to node: "+ getRobot().getDest() +"  time to end:"+(t/1000) + " dest: " + game_robot.getDest());
								
								}
							}
						}
					}
				}*/
			}
			
			catch (Exception e) {
					e.printStackTrace();
				}

	}

	
	private void refresh() {
		// refresh background picture
		gameGui.picture(600, 500, "background.png", 1200, 1200);

		
		
		boolean flag = true;
		List<String> log = game_mt.service.move();
		if(log != null) {
			
		
			

			
			// refresh graph:
			
			Graph_draw gd = new Graph_draw(gameGui);
			gd.draw(game_mt.getGraph());
			
			// refresh fruits:
			
			drawFruits();
			
			// refresh robots:
			
			List<Robot> list_robots = new ArrayList<>();
			long t = game_mt.service.timeToEnd();
			for(int i=0;i<log.size() && flag;i++) { // iterates over the robots
				String robot_json = log.get(i);
				//System.out.println(robot_json);
				
				ReadJSON rj = new ReadJSON(gameGui.f);
				Robot robot = rj.readRobot(robot_json);
				list_robots.add(robot);
				
				
				game_mt.updateRobots(list_robots);
				
				
				gameGui.setPenColor(gameGui.ORANGE);
				gameGui.setPenRadius(0.04);
				gameGui.point(robot.getPos().x(), robot.getPos().y());
			}
		
			

			
			
		//	game_board.drawObjects();
			/*
			updateRobot(game_robot);


			
			//gameGui.picture(game_robot.getPos().x(), game_robot.getPos().y() , "robot.png", 30,60);
			
			gameGui.setPenColor(gameGui.ORANGE);
			gameGui.setPenRadius(0.04);
			gameGui.point(getRobot().getPos().x(), getRobot().getPos().y());
			gameGui.pause(50);*/

			gameGui.show();
		
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
						gameGui.picture(current_fruit.getPos().x(), current_fruit.getPos().y() , "banana.png", 30,60, 60);
					}
					else {
						gameGui.picture(current_fruit.getPos().x(), current_fruit.getPos().y() , "apple.png", 30,60);

						}
			}

			
			
		/*	for (int i=0; i < tmp_fruit_list.size(); i++) {
				Fruit current_fruit = tmp_fruit_list.get(i);
				
				flag = true;
						
				for (int j = 0; j < array_of_graph.length && flag; j++) {
					for (int k = 0; k < array_of_graph[j].size() && flag; k++) {
						int dest = array_of_graph[j].get(k).getDest();
						Point3D pos = game_mt.getGraph().getNode(dest).getLocation();

						if (line.isIn(game_mt.getGraph().getNode(j).getLocation(), pos, current_fruit.getPos())) {
							tmp_fruit_list.remove(current_fruit); i--;
							flag = false;

							if (game_mt.getGraph().getNode(j).getLocation().y() > game_mt.getGraph().getNode(dest).getLocation().y()) {
							gameGui.picture(current_fruit.getPos().x(), current_fruit.getPos().y() , "apple.png", 30,60);
						//	System.out.println(current_fruit.getPos().x() + "," + current_fruit.getPos().y());
							}
							else {
								gameGui.picture(current_fruit.getPos().x(), current_fruit.getPos().y() , "banana.png", 30,60, 60);						}
						}
					}
				}

			}*/
	}
		
	/*		System.out.println("--");
			System.out.println(game_mt.g.getNode(12).getLocation().x() + "," + game_mt.g.getNode(12).getLocation().y() + " = 12->13");
			System.out.println(game_mt.g.getNode(13).getLocation().x() + "," + game_mt.g.getNode(13).getLocation().y() + " = 13->13");
			System.out.println(tmp_fruit_list.get(0).getPos().x() + "," + tmp_fruit_list.get(0).getPos().y() + " fruit");
			for (int i = 0; i < tmp_fruit_list.size(); i++) {
				
				System.out.println(tmp_fruit_list.get(i).getPos().x() + " - " + tmp_fruit_list.get(i).getPos().y() + " i=" + i);	
			}
			
			line.isIn(game_mt.g.getNode(12).getLocation(), game_mt.g.getNode(13).getLocation(), tmp_fruit_list.get(0).getPos());
			
			*/
			
			

		
		


/*	*//**
	 * update the robot that the server sends
	 * @param robot
	 *//*
	private void updateRobot(Robot robot) {
		getRobot().setDest(robot.getDest());
		getRobot().setPos(robot.getPos());
		getRobot().setSpeed(robot.getSpeed());
		getRobot().setSrc(robot.getSrc());
		getRobot().setValue(robot.getValue());
	}*/



/*	public Robot getRobot() {
		return null;
	}*/
	

/*	*//**
	 * This method gets an edge when the user clicks on the screen.
	 * The method looks if the robot has a connection to the adjacent node. 
	 * If so, the thread will wake up.
	 * @param edge
	 *//*
	public void edge_broadcast(edge_data edge) {
		if (getRobot().getDest() == -1) {			
			int target = -1;			
			if (game_mt.getGraph().getEdge(getRobot().getSrc(), edge.getSrc()) != null  && edge.getDest() == getRobot().getSrc()|| 
				game_mt.getGraph().getEdge(getRobot().getSrc(), edge.getDest()) != null && edge.getSrc() == getRobot().getSrc()) {
				if (edge.getSrc() == getRobot().getSrc()) target = edge.getDest();
				else target = edge.getSrc();
				getRobot().setDest(target); 
				game.chooseNextEdge(getRobot().getID(), target);
				this.notify();
			}
		}
	}*/
	
	
}