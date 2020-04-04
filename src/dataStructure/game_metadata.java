package dataStructure;


import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import Server.game_service;
import algorithms.Graph_Algo;
import algorithms.ReadJSON;
import items.Fruit;
import items.Robot;
import threads.Game_controller;
import utils.Point3D;

/**
* This class contains an object of nodes, edges, fruits
* @author Yoni
*/

public class game_metadata{
	
	public game_service service;
	private ReadJSON readJ;
	private List<Robot> robots; // robots & their recent location
	private graph game_graph;
	public List<Fruit> fruits; // all fruits
	public List<Fruit> available_fruits;
	public PriorityQueue<Fruit> available__fruits;
	public Game_controller controller;
	public Graph_Algo graph_algo;
	
	public game_metadata(Point3D measures, game_service game) {

		this.service = game;
		this.fruits = new ArrayList<>();
		this.available_fruits = new ArrayList<>();
		

		
		
			readJ = new ReadJSON(measures);
			controller = new Game_controller(this);
		this.game_graph = readJ.readGraph(service.getGraph());
		this.graph_algo = new Graph_Algo(getGraph());
	
		
		robots = readJ.readRobots(service.getRobots());
		fruits = getFruits();
	}
	
	public List<Robot> getRobots(){ return robots;};
	public List<Fruit> getFruits(){ return readJ.readFruits(service.getFruits(), game_graph);}
	public graph getGraph(){ return game_graph;}

	public void updateRobots(List<Robot> list_robots) {
		for (int i=0; i<list_robots.size(); i++) {
			int index = list_robots.get(i).getID();
			boolean flag = true;
				for (int j = 0; j < robots.size() && flag; j++) {
					if (robots.get(j).getID() == index) {
				//		System.out.println("ROBOT " +index + " CHANGED POS: " + list_robots.get(i).getSrc() + "," + list_robots.get(i).getDest() );
						robots.get(j).setPos(list_robots.get(i).getPos());
						robots.get(j).setDest(list_robots.get(i).getDest());
						robots.get(j).setSrc(list_robots.get(i).getSrc());
						flag = false;
					}
				}
			}
		}

}