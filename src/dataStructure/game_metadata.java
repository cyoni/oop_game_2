package dataStructure;

import java.util.ArrayList;
import java.util.List;

import Server.game_service;
import algorithms.ReadJSON;
import gui.Todelete;
import items.Fruit;
import items.Robot;
import utils.Point3D;

/**
* This class contains an object of nodes, edges, fruits
* @author Yoni
*/

public class game_metadata{
	
	public game_service service;
	private ReadJSON readJ;
	private List<Robot> robots;
	private graph game_graph;
	
	public game_metadata(Point3D measures, game_service game) {
		this.service = game;
		readJ = new ReadJSON(measures);
		robots = readJ.readRobots(service.getRobots());
		this.game_graph = readJ.readGraph(service.getGraph());
	}
	
	public List<Robot> getRobots() {return robots;};
	public List<Fruit> getFruits() {return readJ.readFruits(service.getFruits(), game_graph);}
	public graph getGraph() {return game_graph;}

	public void updateRobots(List<Robot> list_robots) {
		robots.clear();
		robots.addAll(list_robots);
	}

}