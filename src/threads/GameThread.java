package threads;

import java.util.List;

import org.json.JSONObject;

import Server.game_service;
import algorithms.ReadJSON;
import algorithms.split_string;
import dataStructure.edge_data;
import dataStructure.game_metadata;
import gui.Game_board;
import gui.MyGameGUI;
import items.Robot;
import oop_dataStructure.OOP_DGraph;
import utils.Point3D;
import utils.StdDraw;

public class GameThread extends Thread{
	
	Robot robot;
	private int version; // 0 for manual version, 1 for automatic version.
	private game_service game;
	private MyGameGUI gameGui;
	private game_metadata game_mt;
	private OOP_DGraph game_graph; 
	private Game_board game_board;
	
	public GameThread(int version, game_metadata game_mt, Game_board game_board, OOP_DGraph game_graph, game_service game, MyGameGUI gameGui, String robot) {
	 this.robot = ReadJSON.readRobot(robot); 
	 this.version = version;		
	 this.game = game;
	 this.gameGui = gameGui;
	 this.game_mt = game_mt;
	 this.game_graph = game_graph;
	 this.game_board = game_board;
	}
		
	public synchronized void run() {
					
			try {
				System.out.println("Thread ID " + getId() + " started!");
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
				}
			}
			
			catch (InterruptedException e) {
					e.printStackTrace();
				}

	}

	
	/**
	 * update the robot that the server sends
	 * @param robot
	 */
	private void updateRobot(Robot robot) {
		getRobot().setDest(robot.getDest());
		getRobot().setPos(robot.getPos());
		getRobot().setSpeed(robot.getSpeed());
		getRobot().setSrc(robot.getSrc());
		getRobot().setValue(robot.getValue());
	}



	public Robot getRobot() {
		return robot;
	}
	

	/**
	 * This method gets an edge when the user clicks on the screen.
	 * The method looks if the robot has a connection to the adjacent node. 
	 * If so, the thread will wake up.
	 * @param edge
	 */
	public synchronized void edge_broadcast(edge_data edge) {
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
	}
	
	
}