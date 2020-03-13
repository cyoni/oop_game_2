package dataStructure;

import java.util.ArrayList;
import java.util.List;

import items.Fruit;
import items.Robot;

/**
* This class contains an object of nodes, edges, fruits
* @author Yoni
*/

public class game_metadata{
	
	private graph g;
	private List<Fruit> fruits;
	private List<Robot> robots;

	
	public game_metadata(graph g, List<Fruit> fruits, List<Robot> robots) {
		this.g = g;
		this.robots = new ArrayList<>();
		this.fruits = new ArrayList<>(fruits);
		this.robots = new ArrayList<Robot>(robots);
	}
	
	public void addRobot(Robot r) {
		robots.add(r);		
	}
	
	public List<Robot> getRobots() {return robots;}
	
	
	public Robot getRobot(int n) { if (robots.size() > n) return robots.get(n); else return null;}

	
	
	public List<Fruit> getFruits() {
		return fruits;
	}
	
	public graph getGraph() {return g;}
	
	


}