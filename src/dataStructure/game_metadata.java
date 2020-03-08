package dataStructure;

import java.util.ArrayList;
import java.util.List;

import items.Fruit;

/**
* This class contains an object of nodes, edges, fruits
* @author Yoni
*/

public class game_metadata{
	
	public graph g;
	public List<Fruit> fruits;
	
	
	public game_metadata(graph g, List<Fruit> fruits) {
		this.g = g;
		this.fruits = new ArrayList<>(fruits);
	}
	
	public game_metadata getData() {
		return this;
	}
	


}