package dataStructure;

import java.util.ArrayList;
import java.util.List;

import Server.game_service;
import algorithms.ReadJSON;
import gui.MyGameGUI;
import items.Fruit;
import items.Robot;

/**
* This class contains an object of nodes, edges, fruits
* @author Yoni
*/

public class game_metadata{
	
	public game_service service;
	private ReadJSON readJ;
	private List<Robot> robots;
	private graph game_graph;
	
	public game_metadata(MyGameGUI gameGui, game_service game) {
		this.service = game;
		readJ = new ReadJSON(gameGui.f);
		robots = readJ.readRobots(service.getRobots());
		this.game_graph = readJ.readGraph(service.getGraph());
	}
	
	public List<Robot> getRobots() {return robots;};
	public List<Fruit> getFruits() {return readJ.readFruits(service.getFruits());}
	public graph getGraph() {return game_graph;}

	public void updateRobots(List<Robot> list_robots) {

		robots.clear();
		robots.addAll(list_robots);
		
		
		
		/*for (Robot robot : list_robots) {*/
/*			robot.setDest(robot.getDest());
			getRobot().setPos(robot.getPos());
			getRobot().setSpeed(robot.getSpeed());
			getRobot().setSrc(robot.getSrc());
			getRobot().setValue(robot.getValue());*/
			
		/*}*/
		
	}
	

}