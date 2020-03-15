package gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import algorithms.ReadJSON;
import algorithms.converter;
import algorithms.line;
import algorithms.split_string;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.game_metadata;
import dataStructure.graph;
import dataStructure.node_data;
import items.Fruit;
import items.Robot;
import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;
import threads.GameThread;
import utils.Point3D;
import static javax.swing.JOptionPane.showMessageDialog;

public class Game_board{
	 int scenario_num;
	static MyGameGUI myGameGui;
	 game_metadata game_mt; // contains converted graph, list of fruits, robots
	 private game_service game;
	 OOP_DGraph game_graph;
	 private List<GameThread> g_threads;
	 
	public Game_board(MyGameGUI myGameGui , int stage) {Game_board.myGameGui = myGameGui; this.scenario_num = stage;  g_threads = new ArrayList<>();}
	
	
	public void start_game() {
		int scenario_num = 23;
		game = Game_Server.getServer(scenario_num); // you have [0,23] games
		String graph_json = game.getGraph();
		game_graph = new OOP_DGraph();
		game_graph.init(graph_json);
		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("robots");
			System.out.println(info);
			System.out.println(graph_json);
			// the list of fruits should be considered in your solution
			Iterator<String> f_iter = game.getFruits().iterator();
			System.out.println("fruits " + f_iter.toString());
			//while(f_iter.hasNext()) {System.out.println(f_iter.next());}	
				int src_node = 0;  // arbitrary node, you should start at one of the fruits
			for(int a = 0;a<rs;a++) {
				game.addRobot(src_node+a);
				
			}
						
			game_mt = ReadJSON.ReadJson_graph(myGameGui.f, graph_json, game.getFruits()
					, game.getRobots());
			
			drawRobots();
			drawObjects();
			
		}
		catch (JSONException e) {e.printStackTrace();}
		game.startGame();


		manualVersion();
		
		
	}
	
	
	public synchronized void drawObjects() {
		myGameGui.picture(600, 500, "background.png", 1200, 1200);
		drawGraph();
		drawFruits();
	}


	private synchronized void drawGraph() {
		Graph_draw gd = new Graph_draw(myGameGui);
		gd.draw(game_mt.getGraph());
	}


	private void drawRobots() {

		List<String> robots = game.getRobots();
		
		for (int i = 0; i < robots.size(); i++) {
		Robot r = ReadJSON.readRobot(robots.get(i));
		
		myGameGui.picture(r.getPos().x(), r.getPos().y() , "robot.png", 30,60);
		}
		
	}


/*	private Fruit readFruit(String data) {
		try {
		JSONObject line = new JSONObject(data);
		JSONObject t = line.getJSONObject("Fruit");
		

		double value = t.getDouble("value");
		int type = t.getInt(("type"));	
		String pos = t.getString(("pos"));

		String point[] = pos.split(",");
		double x = Double.parseDouble(point[0]);
		double y = Double.parseDouble(point[1]);
		//double z = Double.parseDouble(point[2]);

		return (new Fruit(value, type, new converter(myGameGui.f).coordsToPixel(y, x)));

		}
		catch(Exception c){System.out.println(c);	}

		
		
		return null;
	}
*/
	
	
	public void manualVersion() {
		
		//moveRobots(game, game_graph);

		int robots = game.getRobots().size();
		
		for (int i=0; i< robots; i++) {
			g_threads.add(new GameThread(0, game_mt, this, game_graph, game, myGameGui, game.getRobots().get(i)));
			System.out.println("Thread ID " + g_threads.get(i).getId() + " joined.");
			g_threads.get(i).start();
		}


	}

	
	public void automaticVersion() {
		
		
		
		
	}
	/** 
	 * Moves each of the robots along the edge, 
	 * in case the robot is on a node the next destination (next edge) is chosen (randomly).
	 * @param game
	 * @param gg
	 * @param log
	 */
	private static void moveRobots(game_service game, oop_graph game_graph) {
		List<String> log = game.move();
		
		if(log != null) {
			long t = game.timeToEnd();
			for(int i=0;i<log.size();i++) {
				String robot_json = log.get(i);
				try {
					JSONObject line = new JSONObject(robot_json);
					JSONObject ttt = line.getJSONObject("Robot");
					int rid = ttt.getInt("id");
					int src = ttt.getInt("src");
					int dest = ttt.getInt("dest");
					String pos = ttt.getString("pos");
					
					Point3D p = split_string.get_pos_frm_str_and_convert(myGameGui.f, pos);
					  myGameGui.picture(p.x(), p.y() , "robot.png", 30,60);
					  
					  
					if(dest==-1) { // when a robot reached a node - choose its next move.
						dest = nextNode(game_graph, src);
						game.chooseNextEdge(rid, dest);
						System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
						System.out.println(ttt);
						// to update the location of the robot in the robot list
					  
					}
				} 
				catch (JSONException e) {e.printStackTrace();}
			}
		}
	}
	
	/**
	 * a very simple random walk implementation!
	 * @param g
	 * @param src
	 * @return
	 */
	private static int nextNode(oop_graph g, int src) {
		int ans = -1; 
		Collection<oop_edge_data> ee = g.getE(src);
		
		
		Iterator<oop_edge_data> itr = ee.iterator();
		int s = ee.size();
		int r = (int)(Math.random()*s);
		int i=0;
		while(i<r) {itr.next();i++;}
		ans = itr.next().getDest();
		return ans;
	}

	
	
	public void getItems() {
			start_game();
	}

	/**
	 * this methods sets the number of robots that the user is asked to deploy on the map.
	 * @param num_of_robots
	 */
	private void setRobots(int num_of_robots) {
	
		
		for (int i = 0; i < num_of_robots; i++) {
						
			
			//game_mt.addRobot(game_mt.getGraph().getNode(0).getKey());
			
			myGameGui.picture(game_mt.getGraph().getNode(i).getLocation().x(), game_mt.getGraph().getNode(i).getLocation().y() , "robot.png", 30,60);

			game.addRobot(i);
		/*	try {
			JSONObject line = new JSONObject(game.getRobots().get(i));
			JSONObject ttt = line.getJSONObject("Robot");
			String pos = ttt.getString("pos");

			String point[] = pos.split(",");
			double x = Double.parseDouble(point[0]);
			double y = Double.parseDouble(point[1]);
			double z = Double.parseDouble(point[2]);
			
			Robot r = new Robot(new converter(myGameGui.f).coordsToPixel(y, x));
			game_mt.addRobot(r);
			
			myGameGui.picture(r.getPos().x(),r.getPos().y() , "robot.png", 30,60);

			game.addRobot(0);
			
			System.out.println("ok");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
			
		}
		
		
	}


	/**
	 * This function draws the fruits on the screen.
	 * Apples will be printed on edges that its edge increases e.g   2---->6
	 * Strawberries will be printed on edges that its edge decreases e.g  5---->2*/
	
	private void drawFruits() {


		if (game_mt.getFruits().isEmpty()) return;

		List<edge_data>[] array_of_graph = game_mt.getGraph().getArrayOfVertciesWithEdges();
		List<Fruit> tmp_fruit_list = new ArrayList<>(game_mt.getFruits());
		boolean flag = true;
		
		for (int i=0; i < tmp_fruit_list.size(); i++) {
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
						myGameGui.picture(current_fruit.getPos().x(), current_fruit.getPos().y() , "apple.png", 30,60);
						System.out.println(current_fruit.getPos().x() + "," + current_fruit.getPos().y());
						}
						else {
							myGameGui.picture(current_fruit.getPos().x(), current_fruit.getPos().y() , "banana.png", 30,60, 60);						}
					}
				}
			}

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
		
		
	}


	/**
	 * This method returns an edge when the user clicks on the screen.
	 * @param mouse_point
	 */
	public void getEdge(Point3D mouse_point) {
		double _x = mouse_point.x();
		double _y = mouse_point.y();		
		System.out.println("mouse coords is: " + _x + "," + _y );
		graph g = game_mt.getGraph();
		List<edge_data>[] x = g.getArrayOfVertciesWithEdges();
		double min_distance = Double.MAX_VALUE;
		boolean ok = false, flag = false;
		edge_data edge = null;
		double[] arr = null;
		for (int i=0; i< x.length && !flag; i++) {
			for (int j = 0; j < x[i].size() && !flag; j++) {
				node_data node_src = g.getNode(i);
				node_data node_dest = g.getNode(x[i].get(j).getDest());
				Point3D p1 = node_src.getLocation();
				Point3D p2 = node_dest.getLocation();
				Point3D p = new Point3D(_x, _y);
				double max = Math.max(p1.x(), p2.x());
				double min = Math.min(p1.x(), p2.x());
				if (!(min < p.x() && max > p.x())) continue; // the point HAS to be between an edge
				arr = line.distanceBetweenLineAnd2Points(p1, p2 , p);
				if (min_distance > arr[0] && arr[0] < 20) {
					min_distance = arr[0]; System.out.println(g.edgeSize());
					edge = g.getEdge(node_src.getKey(), node_dest.getKey());
					ok = true;
					}
				}
			}
		if (!ok) {System.out.println("Couldn't find any edges around.");}
		else {
		System.out.println("OK. new location: " + arr[1] + ","  + arr[2] + ". Edge: " + edge.getSrc() + "," + edge.getDest());
		sendEdgeToThreads(edge);
		}
	}


	private void sendEdgeToThreads(edge_data edge) {
		for (int i = 0; i < g_threads.size(); i++) {
			g_threads.get(i).edge_broadcast(edge);
		}
		
	}


/*
	public void addRobots(Point3D mouse_point) {
		int _x = (int) mouse_point.x();
		int _y = (int) mouse_point.y();
		double min_distance = Double.MAX_VALUE;
		graph g = game_mt.getGraph();
		List<edge_data>[] x = g.getArrayOfVertciesWithEdges();
		//System.out.println(g.getE(1).size());
		
		if (add_robot == 0) {
			System.out.println("the game has started");
			start_game(); // start the game
		}
		
		if (add_robot > 0) {
			System.out.println("robot is set at: " + _x + "," + _y );
			///Robot tmp_robot = new Robot(new Point3D(_x, _y));
			boolean ok = false, flag= false;
			double[] arr = null;
			for (int i=0; i< x.length && !flag; i++) {
				for (int j = 0; j < x[i].size() && !flag; j++) {
					
				//System.out.println(g.getNode(i).getKey() + "->" + g.getNode(x[i].get(j).getDest()).getKey() + "[" + g.getNode(i).getLocation().x() + "," + g.getNode(i).getLocation().y() +  " - "+
				//		g.getNode(x[i].get(j).getDest()).getLocation().x() + "," + g.getNode(x[i].get(j).getDest()).getLocation().y()+"]");
					
					Point3D p1 = g.getNode(i).getLocation();
					Point3D p2 = g.getNode(x[i].get(j).getDest()).getLocation();
					Point3D p = new Point3D(_x, _y);
					double max = Math.max(p1.x(), p2.x());
					double min = Math.min(p1.x(), p2.x());
					if (!(min < p.x() && max > p.x())) continue; // the point Has to be between the line
					arr = line.distanceBetweenLineAnd2Points(p1, p2 , p);
					if (min_distance > arr[0] && arr[0] < 20) {
			//			min_distance = arr[0];tmp_robot = new Robot((new Point3D(arr[1],arr[2])));ok = true;
						
						}
					
				//	else { x[g.getNode(j).getKey()].remove(g.getNode(i).getKey());}
					}
				}
			
			if (!ok) {System.out.println("Please choose another point.");}
			else {
		//	game_mt.addRobot(tmp_robot);
			System.out.println("new loc: " + arr[1] + ","  + arr[2]);
		//	myGameGui.picture(tmp_robot.getPos().y(),tmp_robot.getPos().x() , "robot.png", 30,60);
			add_robot--;
			if (add_robot == 0) System.out.println("click again to start");
			}
		}
	}*/
}