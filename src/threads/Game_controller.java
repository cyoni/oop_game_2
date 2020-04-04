package threads;

import java.util.Queue;

import dataStructure.game_metadata;
import dataStructure.node_data;
import items.Fruit;
import items.Robot;

public class Game_controller {

	private game_metadata game_mt;
	
	public Game_controller(game_metadata game_mt) {
		this.game_mt = game_mt;
	}
	
	
	public synchronized void allocateFruitToRobot(Robot robot) {
		Double min_distance = Double.MAX_VALUE;
		Fruit fruit_of_min_distance = null;
		Fruit closest_fruit = null;
		if (robot.getDest() != -1 || game_mt.available_fruits.size() == 0) return; // This robot should wait until his dest will be updated from server

		
		for (int j = 0; j < game_mt.available_fruits.size() ; j++) { // find the closest fruit and assign it to the available robot

		 Fruit current_fruit = game_mt.available_fruits.get(j);
			

			double distance_to_fruit = -1;
			
			if (game_mt.graph_algo.isEdgeOpposite(current_fruit, robot) && current_fruit.getType() == -1) { // banana
					distance_to_fruit = game_mt.graph_algo.shortestPathDist(robot.getSrc(), 
						game_mt.getGraph().getNode(current_fruit.getEdge().getSrc()).getKey());
			}
			
			else if (!game_mt.graph_algo.isEdgeOpposite( current_fruit, robot) && current_fruit.getType() == 1) { // Opposite & apple
				distance_to_fruit = game_mt.graph_algo.shortestPathDist(robot.getSrc(), 
						game_mt.getGraph().getNode(current_fruit.getEdge().getDest()).getKey());
			}
			
			else if (!game_mt.graph_algo.isEdgeOpposite(current_fruit, robot) && current_fruit.getType() == -1) { // Opposite & banana
				distance_to_fruit = game_mt.graph_algo.shortestPathDist(robot.getSrc(), 
						game_mt.getGraph().getNode(current_fruit.getEdge().getDest()).getKey()) + (current_fruit.getEdge().getWeight());
			}
			
			else if (game_mt.graph_algo.isEdgeOpposite(current_fruit, robot) && current_fruit.getType() == 1) { // apple
				distance_to_fruit = game_mt.graph_algo.shortestPathDist(robot.getSrc(), 
						game_mt.getGraph().getNode(current_fruit.getEdge().getSrc()).getKey()) + (current_fruit.getEdge().getWeight());
			}
			
			
			
			System.out.println("j="+ j +" Fruit " + current_fruit.getEdge().getSrc() +"," + current_fruit.getEdge().getDest() + " distance: " + distance_to_fruit);
			if (min_distance > distance_to_fruit) {
				min_distance = distance_to_fruit;
				fruit_of_min_distance = current_fruit;
				closest_fruit = current_fruit;
				}
		} // END j LOOP
	
		Queue<node_data> l = null;

		
		/// the following should be in ManageRobot
		
		System.out.println("TYPE IS " +  closest_fruit.getType() + " IS OPPOSITE? " + game_mt.graph_algo.isEdgeOpposite(fruit_of_min_distance, robot) + " edge: " 
				+ closest_fruit.getEdge().getSrc() + ", dest: " + closest_fruit.getEdge().getDest());
		
		if (game_mt.graph_algo.isEdgeOpposite(fruit_of_min_distance, robot) && closest_fruit.getType() == -1) {
			l = game_mt.graph_algo.shortestPath(robot.getSrc(), fruit_of_min_distance.getEdge().getDest());
			l.add(game_mt.getGraph().getNode(fruit_of_min_distance.getEdge().getSrc()));
		
		} else if (game_mt.graph_algo.isEdgeOpposite(fruit_of_min_distance, robot) && closest_fruit.getType() == 1) {
			l = game_mt. graph_algo.shortestPath(robot.getSrc(), fruit_of_min_distance.getEdge().getDest());
			l.add(game_mt.getGraph().getNode(fruit_of_min_distance.getEdge().getSrc()));
			l.add(game_mt.getGraph().getNode(fruit_of_min_distance.getEdge().getDest()));
			
		} else if (!game_mt.graph_algo.isEdgeOpposite(fruit_of_min_distance, robot) && closest_fruit.getType() == -1) { // banana
			l = game_mt.graph_algo.shortestPath(robot.getSrc(), fruit_of_min_distance.getEdge().getSrc());
			l.add(game_mt.getGraph().getNode(fruit_of_min_distance.getEdge().getDest()));
			l.add(game_mt.getGraph().getNode(fruit_of_min_distance.getEdge().getSrc()));	
			
		} else if (!game_mt.graph_algo.isEdgeOpposite(fruit_of_min_distance, robot) && closest_fruit.getType() == 1) { // apple
			l = game_mt.graph_algo.shortestPath(robot.getSrc(), fruit_of_min_distance.getEdge().getSrc());
			l.add(game_mt.getGraph().getNode(fruit_of_min_distance.getEdge().getDest()));
			
		}
		
		game_mt.available_fruits.remove(closest_fruit); // make the fruit now available to others robots
		robot.setPath_to_target(l); // assign the new path to the robot.
		robot.setTarget(closest_fruit);
		robot.setIsBusy(true); // now I am busy

		
		// print new path of the current robot:
		System.out.println("NEW PATH TO ROBOT #" + robot.getID() + ": ");
		for (node_data item : robot.getPath_to_target()) {
			System.out.print(item.getKey() + "->");
		}
			System.out.println();
		
	}
}
