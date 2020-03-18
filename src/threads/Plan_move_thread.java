package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import algorithms.Graph_Algo;
import dataStructure.game_metadata;
import dataStructure.node_data;
import gui.Game_board;
import gui.MyGameGUI;
import items.Fruit;
import items.Robot;

public class Plan_move_thread extends Thread {
	
	private Graph_Algo graph_algo;
	private MyGameGUI gameGui;
	private game_metadata game_mt;
	List<Fruit> available_fruits = new ArrayList<>();
	//List<Fruit> targeted_fruits = new ArrayList<>();
	List<Fruit> fruits = new ArrayList<>();
	List<Robot> robots = new ArrayList<>(); // robots from server
	List<Robot> robots_to_allocate_fruits = new ArrayList<>(); // available robots
	Robot robots_with_target[]; // busy robots

	List<Fruit> list_fruit = new ArrayList<>(); // to know when new fruits come

	
	
	public Plan_move_thread(game_metadata game_mt, MyGameGUI gameGui) {
	this.game_mt = game_mt;
	this.gameGui = gameGui;
	this.graph_algo = new Graph_Algo();
	this.graph_algo.init(game_mt.getGraph());

	fruits.addAll(game_mt.getFruits());
	available_fruits.addAll(fruits); // initialize list
	list_fruit.addAll(fruits);
	
	robots_to_allocate_fruits = new ArrayList<>();
	robots_with_target = new Robot[game_mt.getRobots().size()];
	for (int i=0; i<robots_with_target.length; i++) robots_with_target[i] = new Robot();

	}

	
	public void run() {
		
		while(game_mt.service.isRunning()) {
			try {
				sleep(500);
					robots.clear();
					robots.addAll(game_mt.getRobots()); // refresh robot list
					fruits.clear();
					fruits.addAll(game_mt.getFruits()); // refresh fruit list
					
					// check for new fruits:
					
					
					boolean changed = false;
					for (int i = 0; i < fruits.size(); i++) {
						Fruit current_fruit = fruits.get(i);
						boolean exists = false;
						for (int j = 0; j < list_fruit.size(); j++) {
							if (current_fruit.getPos().x() == list_fruit.get(j).getPos().x() &&
									current_fruit.getPos().y() == list_fruit.get(j).getPos().y()) {
								exists = true;
							}
						}

						if (!exists) {
							list_fruit.add(current_fruit);
							available_fruits.add(current_fruit); // new fruit!
							System.out.println("A new fruit has been added: " + current_fruit.getPos().x() + "," + current_fruit.getPos().y()+"."
									+  ", available fruits: " + available_fruits.size());
							changed = true;
							
						}
					}
					
					if (changed) { // remove redundant fruits from the list (old fruits)
						for (int i = 0; i < list_fruit.size(); i++) {
							boolean found = false;
							for (int j = 0; j < fruits.size(); j++) {
								if (list_fruit.get(i).getPos().x() == fruits.get(i).getPos().x() &&
										list_fruit.get(i).getPos().y() == fruits.get(i).getPos().y()) {found = true;}
							}
							if (!found) {list_fruit.remove(i--);System.out.println("fruit #" + (i+1) +" has been removed"); }
						}
					}

					for (int i = 0; i < robots.size(); i++) {
						if (robots_with_target[i].getPath_to_target().size() == 0 && robots.get(i).getDest() == -1) { // find available robots 
							robots_to_allocate_fruits.add(robots.get(i));
							System.out.println("Robot #"+ i +" has just become available and has received a new target. (" + robots.get(i).getSrc() + "," + robots.get(i).getDest()+")");
						} 
					}
					
					// allocate a fruit to those robots:
					
					for (int i = 0; i < robots_to_allocate_fruits.size(); i++) {
						Double min_distance = Double.MAX_VALUE;
						Robot current_robot = null;
						Fruit fruit_of_min_distance = null;
						int index_of_fruit = -1;
						
						System.out.println("available_fruits: "+ available_fruits.size());
						boolean isOpposite = false;
						Fruit current_fruit = null;
						
						for (int j = 0; j < available_fruits.size() ; j++) {
							current_robot = robots_to_allocate_fruits.get(i);
							current_fruit = available_fruits.get(i);
							available_fruits.get(j);
							// to check if an edge is opposite:
							
							 isOpposite = false;
							if (graph_algo.shortestPathDist(current_robot.getSrc(), 
									game_mt.getGraph().getNode(current_fruit.getEdge().getDest()).getKey()) > 
							graph_algo.shortestPathDist(current_robot.getSrc(), 
									game_mt.getGraph().getNode(current_fruit.getEdge().getSrc()).getKey())) {isOpposite = true;}
							

							
							double distance_to_fruit = -1;
							
							
							if (isOpposite && current_fruit.getType() == -1 || !isOpposite && current_fruit.getType() == 1) { 
							distance_to_fruit = graph_algo.shortestPathDist(current_robot.getSrc(), 
									game_mt.getGraph().getNode(current_fruit.getEdge().getDest()).getKey()) + current_fruit.getEdge().getWeight();
							}
							else if (isOpposite && current_fruit.getType() == 1 || !isOpposite && current_fruit.getType() == -1) { 
								distance_to_fruit = graph_algo.shortestPathDist(current_robot.getSrc(), 
										game_mt.getGraph().getNode(current_fruit.getEdge().getDest()).getKey()) + 2*current_fruit.getEdge().getWeight();
								}
							
							
							if (min_distance > distance_to_fruit) {
								min_distance = distance_to_fruit;
								fruit_of_min_distance = current_fruit;
								index_of_fruit = j;
								}
						
						}
						System.out.println("Robot " + i + " going to " + robots_to_allocate_fruits.get(i).getSrc()+ "," + fruit_of_min_distance.getEdge().getDest()+
								". Target: ");
						

						Queue<node_data> l = null;
						
						if (isOpposite && current_fruit.getType() == -1 || !isOpposite && current_fruit.getType() == 1) { 
							l = graph_algo.shortestPath(robots_to_allocate_fruits.get(i).getSrc(), fruit_of_min_distance.getEdge().getDest());
							System.out.println("#### " + robots_to_allocate_fruits.get(i).getSrc() + "," + fruit_of_min_distance.getEdge().getDest());
						}
						else {
							System.out.println("#### " + robots_to_allocate_fruits.get(i).getSrc() + "," + fruit_of_min_distance.getEdge().getSrc());
							l = graph_algo.shortestPath(robots_to_allocate_fruits.get(i).getSrc(), fruit_of_min_distance.getEdge().getSrc());
						}
						
						
						System.out.println("current robot: " + current_robot.getSrc());
						Robot tmp = new Robot(current_robot.getID(), current_robot.getValue(),
								current_robot.getSrc(), -1 , current_robot.getSpeed(), current_robot.getPos());
						tmp.setPath_to_target(l);
						
						robots_with_target[current_robot.getID()] = tmp;
						
						robots_to_allocate_fruits.remove(i);
						//targeted_fruits.add(available_fruits.get(index_of_fruit));
						available_fruits.remove(index_of_fruit);
					}
					
					
					/// notify server of path
					
					System.out.println("Robot's position: "+ robots.get(0).getSrc() + "," + robots.get(0).getDest());
					// כשהוא יאכל את הפרי נוסיףך אותאת הרובוט לרשימה של הרובוטים הפנויים
					for (int i = 0; i < robots_with_target.length; i++) {
						if (robots_with_target[i].getPath_to_target().size() > 0) {
						//	System.out.println(robots.get(i).getDest() + "<===>" +  robots_with_target[i].getDest() + "@#@#");
							if (robots.get(i).getDest() == -1 || robots_with_target[i].getDest() == -1) { // robot stands still
								int node_to_pop = robots_with_target[i].getPath_to_target().poll().getKey();
								game_mt.service.chooseNextEdge(robots_with_target[i].getID(), node_to_pop);
								
								robots_with_target[i].setSrc(robots.get(i).getSrc());
								robots_with_target[i].setDest(node_to_pop);
								System.out.println("popped: " + node_to_pop);
							//	robots.get(i).getPath_to_target();
							}
						}
					}
					
					
					//
					
					
					// print paths:
					
					for (Robot current_robot : robots_with_target) {
						
						for (node_data item : current_robot.getPath_to_target()) {
							System.out.print(item.getKey() + "->");
						}
							System.out.println();

						
					}
				
				

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	
}
