package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import algorithms.Graph_Algo;
import algorithms.converter;
import dataStructure.game_metadata;
import dataStructure.node_data;
import gui.Game_board;
import gui.Todelete;
import items.Fruit;
import items.Robot;
import sun.util.calendar.CalendarUtils;

public class Plan_move_thread extends Thread {
	
	private Graph_Algo graph_algo;
	private game_metadata game_mt;
	private List<Fruit> available_fruits = new ArrayList<>();
	private List<Fruit> fruits = new ArrayList<>();
	private List<Robot> robots = new ArrayList<>(); // robots from server
	private List<Robot> available_robots = new ArrayList<>(); // available robots
	private List<Robot> busy_robots;
	private List<Fruit> my_fruits; // to know when new fruits come

	
	
	public Plan_move_thread(game_metadata game_mt) {
	this.game_mt = game_mt;

	this.graph_algo = new Graph_Algo();
	this.graph_algo.init(game_mt.getGraph());

	available_robots = new ArrayList<>();
	available_robots.addAll(game_mt.getRobots());
	busy_robots = new ArrayList<>();
	my_fruits = new ArrayList<>();
	
	}

	
	public void run() {
		
		while(game_mt.service.isRunning()) {
			try {
				sleep(500);
			}
			 catch (InterruptedException e) {
				e.printStackTrace();
			}
				
					robots.clear();
					robots.addAll(game_mt.getRobots()); // refresh robot list
					fruits.clear();
					fruits.addAll(game_mt.getFruits()); // refresh fruit list
					
					
					////////////////////////////////////////////// UPDATE ROBOTS SRC & DEST from Server ////////////////////////////////
					
					for (int i=0; i<robots.size(); i++) {
						for (int j = 0; j < busy_robots.size(); j++) {
								if (busy_robots.get(j).getID() == robots.get(i).getID()) {
									busy_robots.get(robots.get(j).getID()).setSrc(robots.get(i).getSrc());
									busy_robots.get(robots.get(j).getID()).setDest(robots.get(i).getDest());
								}
							}
						
						for (int j = 0; j < available_robots.size(); j++) {
							if (available_robots.get(j).getID() == robots.get(i).getID()) {
								available_robots.get(robots.get(j).getID()).setSrc(robots.get(i).getSrc());
								available_robots.get(robots.get(j).getID()).setDest(robots.get(i).getDest());
								}
							}
						}
					
					
					////////////////////////////////////////////// UPDATE FRUIT LISTS //////////////////////////////////////////////////
					for (int i = 0; i < fruits.size(); i++) { // check for new fruits and then add then to available fruit list:
						boolean found = false;
						Fruit current_fruit = fruits.get(i);
						for (int j = 0; j < my_fruits.size(); j++) {
							if (current_fruit.getPos().x() == my_fruits.get(j).getPos().x() && 
									current_fruit.getPos().y() == my_fruits.get(j).getPos().y()) found = true;
						}
						if (!found)  // not found = new fruit from server
						{
							System.out.println("NEW FRUIT! " + current_fruit.getPos().x() + "," + current_fruit.getPos().y() + "; At " + 
									current_fruit.getEdge().getSrc() + "," + current_fruit.getEdge().getDest());
							my_fruits.add(current_fruit);
							available_fruits.add(current_fruit);
						}
					}
					
					for (int i = 0; i < my_fruits.size(); i++) { // remove old fruits
						boolean found = false;
						Fruit current_fruit = my_fruits.get(i);
						for (int j = 0; j < fruits.size(); j++) {
							if (current_fruit.getPos().x() == fruits.get(j).getPos().x() && 
									current_fruit.getPos().y() == fruits.get(j).getPos().y()) found = true;
						}
						if (!found)  // not found = new fruit from server
						{
							System.out.println("REMOVED FRUIT " + current_fruit.getPos().x() + "," + current_fruit.getPos().y() + "; At " + 
									current_fruit.getEdge().getSrc() + "," + current_fruit.getEdge().getDest());
							my_fruits.remove(current_fruit);
							available_fruits.remove(current_fruit); // just in case it still exists there
						}
					}

					//////////////////////////////////// ALLOCATE FRUITS TO AVAILABLE ROBOTS ////////////////////////////////////////////////
					
					for (int i = 0; i < available_robots.size(); i++) {
						
						Double min_distance = Double.MAX_VALUE;
						Robot current_robot = null;
						Fruit fruit_of_min_distance = null;
						Fruit closest_fruit = null;
						if (available_robots.get(i).getDest() != -1) continue; // This robot should wait until his dest will be updated from server
						
						for (int j = 0; j < available_fruits.size() ; j++) { // find the closest fruit and assign it to the available robot
						 current_robot = available_robots.get(i);
						 Fruit current_fruit = available_fruits.get(j);
							
		
							double distance_to_fruit = -1;
							
							///////////// CONSIDER CONDITION 
														
							if (isEdgeOpposite(current_fruit, current_robot) && current_fruit.getType() == -1) { // banana
									distance_to_fruit = graph_algo.shortestPathDist(current_robot.getSrc(), 
										game_mt.getGraph().getNode(current_fruit.getEdge().getSrc()).getKey());
							}
							
							else if (!isEdgeOpposite(current_fruit, current_robot) && current_fruit.getType() == 1) { // Opposite & apple
								distance_to_fruit = graph_algo.shortestPathDist(current_robot.getSrc(), 
										game_mt.getGraph().getNode(current_fruit.getEdge().getDest()).getKey());
							}
							
							else if (!isEdgeOpposite(current_fruit, current_robot) && current_fruit.getType() == -1) { // Opposite & banana
								distance_to_fruit = graph_algo.shortestPathDist(current_robot.getSrc(), 
										game_mt.getGraph().getNode(current_fruit.getEdge().getDest()).getKey()) + (current_fruit.getEdge().getWeight());
							}
							
							else if (isEdgeOpposite(current_fruit, current_robot) && current_fruit.getType() == 1) { // apple
								distance_to_fruit = graph_algo.shortestPathDist(current_robot.getSrc(), 
										game_mt.getGraph().getNode(current_fruit.getEdge().getSrc()).getKey()) + (current_fruit.getEdge().getWeight());
							}
							
							
							
							System.out.println("j="+ j +" Fruit " + current_fruit.getEdge().getSrc() +"," + current_fruit.getEdge().getDest() + " distance: " + distance_to_fruit);
							if (min_distance > distance_to_fruit) {
								min_distance = distance_to_fruit;
								fruit_of_min_distance = current_fruit;
								closest_fruit = current_fruit;
								}
						} // END j LOOP
						
				//		System.out.println("Robot " + i + " is going from " + available_robots.get(i).getSrc() + 
				//				". Target: " + closest_fruit.getEdge().getSrc() + "," + closest_fruit.getEdge().getDest() + " (" + closest_fruit.getPos().x() + "," + closest_fruit.getPos().y()+")");
						
						Queue<node_data> l = null;
						System.out.println("TYPE IS " +  closest_fruit.getType() + " IS OPPOSITE? " + isEdgeOpposite(fruit_of_min_distance, available_robots.get(i)) + " edge: " 
								+ closest_fruit.getEdge().getSrc() + ", dest: " + closest_fruit.getEdge().getDest());
						
						if (isEdgeOpposite(fruit_of_min_distance, available_robots.get(i)) && closest_fruit.getType() == -1) {
							l = graph_algo.shortestPath(available_robots.get(i).getSrc(), fruit_of_min_distance.getEdge().getDest());
							l.add(game_mt.getGraph().getNode(fruit_of_min_distance.getEdge().getSrc()));
						
						} else if (isEdgeOpposite(fruit_of_min_distance, available_robots.get(i)) && closest_fruit.getType() == 1) {
							l = graph_algo.shortestPath(available_robots.get(i).getSrc(), fruit_of_min_distance.getEdge().getDest());
							l.add(game_mt.getGraph().getNode(fruit_of_min_distance.getEdge().getSrc()));
							l.add(game_mt.getGraph().getNode(fruit_of_min_distance.getEdge().getDest()));
							
						} else if (!isEdgeOpposite(fruit_of_min_distance, available_robots.get(i)) && closest_fruit.getType() == -1) { // banana
							l = graph_algo.shortestPath(available_robots.get(i).getSrc(), fruit_of_min_distance.getEdge().getSrc());
							l.add(game_mt.getGraph().getNode(fruit_of_min_distance.getEdge().getDest()));
							l.add(game_mt.getGraph().getNode(fruit_of_min_distance.getEdge().getSrc()));	
							
						} else if (!isEdgeOpposite(fruit_of_min_distance, available_robots.get(i)) && closest_fruit.getType() == 1) { // apple
							l = graph_algo.shortestPath(available_robots.get(i).getSrc(), fruit_of_min_distance.getEdge().getSrc());
							l.add(game_mt.getGraph().getNode(fruit_of_min_distance.getEdge().getDest()));
							
						}
						
						available_robots.remove(current_robot); // make the robot not available.
						available_fruits.remove(closest_fruit); // make the fruit now available to others robots
						
						current_robot.setPath_to_target(l); // assign the new path to the robot.
						// print new path of the current robot:
						System.out.println("NEW PATH TO ROBOT (" + current_robot.getID() + "): ");
						for (node_data item : current_robot.getPath_to_target()) {
							System.out.print(item.getKey() + "->");
						}
							System.out.println();
						
					
						busy_robots.add(current_robot);

					}

					
					//////////////////////////////////////////// ROBOTS NEXT MOVE ////////////////////////////////////
					System.out.println("fruits graph:");
					for (int j = 0; j < game_mt.getFruits().size(); j++) {
						System.out.print("(" +game_mt.getFruits().get(j).getPos().x() + "," + game_mt.getFruits().get(j).getPos().y() +") ");
					}
					System.out.println();
					
					for (int i = 0; i < busy_robots.size() ; i++) {

						Robot current_robot = busy_robots.get(i);
							
						if (!current_robot.getPath_to_target().isEmpty() && current_robot.getDest() == -1) {
							int go_where = current_robot.getPath_to_target().poll().getKey();
							System.out.println("popped " + go_where);
							game_mt.service.chooseNextEdge(current_robot.getID(), go_where); // request a move
							//System.out.println("ROBOT: " + current_robot.getSrc() + "," + current_robot.getSrc());

						}
						if (current_robot.getPath_to_target().isEmpty()) { // if the robot reached its destination then remove it from busy_robots and add it to available robots
							busy_robots.remove(current_robot); 
							available_robots.add(current_robot);
							}
					}
	
					//////////////////////////////////////////// END ROBOTS NEXT MOVE ////////////////////////////////////

		} // end while
	} // end method


	/**
	 * This method checks whether an edge which the robot is going towards to is opposite.
	 * @param current_fruit
	 * @param robot
	 * @return
	 */
	private boolean isEdgeOpposite(Fruit current_fruit, Robot robot) {
		boolean	closest_fruit_isEdgeOpposite = false;
		if (graph_algo.shortestPathDist(robot.getSrc(), 
				game_mt.getGraph().getNode(current_fruit.getEdge().getDest()).getKey()) < 
					graph_algo.shortestPathDist(robot.getSrc(), 
						game_mt.getGraph().getNode(current_fruit.getEdge().getSrc()).getKey())) {
						closest_fruit_isEdgeOpposite = true;
					}
		return closest_fruit_isEdgeOpposite;
	}
	
	
	
}
