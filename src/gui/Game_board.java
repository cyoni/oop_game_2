package gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import algorithms.ReadJSON;
import algorithms.line;
import dataStructure.edge_data;
import dataStructure.game_metadata;
import dataStructure.graph;
import items.Fruit;
import items.Robot;
import oop_dataStructure.OOP_DGraph;
import utils.Point3D;
import static javax.swing.JOptionPane.showMessageDialog;

public class Game_board{
	 int scenario_num;
	 MyGameGUI myGameGui;
	 game_metadata game_mt; // contains graph, list of fruits, robots
	 private int add_robot;
	 
	public Game_board(MyGameGUI myGameGui , int stage) {this.myGameGui = myGameGui; this.scenario_num = stage;}
	
	
	public void start_game() {
		
		
	}
	public void getItems() {
		
		game_service game = Game_Server.getServer(scenario_num); // you have [0,23] games
		String graph_json = game.getGraph();
		OOP_DGraph gg = new OOP_DGraph();
		gg.init(graph_json);
		
		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int num_of_robots = ttt.getInt("robots");
			
			System.out.println(info);
			System.out.println(graph_json); // json text
			
			ReadJSON r = new ReadJSON(myGameGui.f);
			

			game_mt = r.ReadJson_graph(graph_json, game.getFruits());

			System.out.println(game_mt.getFruits().size());
			Graph_draw gd = new Graph_draw(myGameGui);
			gd.draw(game_mt.getGraph());
			 
			
			
			drawFruits(); // draw the fruits on the screen
		
			
			
			showMessageDialog(null, "Please deploy on the map " +  num_of_robots + " robot\\s");

			// deploy robots on the map:
			
			setRobots(num_of_robots);
			

			start_game(); // start the game
			
			// the list of fruits should be considered in your solution
			Iterator<String> f_iter = game.getFruits().iterator();
			while(f_iter.hasNext()) {System.out.println(f_iter.next() + "!!");}	
			
			int src_node = 0;  // arbitrary node, you should start at one of the fruits
			for(int a = 0;a<num_of_robots;a++) {
				game.addRobot(src_node+a);
			}
		}
		
		
		
		catch (JSONException e) {e.printStackTrace();}
		
		
		
/*		game.startGame();
		// should be a Thread!!!
		while(game.isRunning()) {
		//	moveRobots(game, gg);
		}
		String results = game.toString();
		System.out.println("Game Over: "+results);*/
		
	}

	/**
	 * this methods sets the number of robots that the user is asked to deploy on the map.
	 * @param num_of_robots
	 */
	private void setRobots(int num_of_robots) {
		this.add_robot = num_of_robots;
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


	public void addRobots(Point3D mouse_point) {
		int _x = (int) mouse_point.x();
		int _y = (int) mouse_point.y();
	
		graph g = game_mt.getGraph();
		List<edge_data>[] x = g.getArrayOfVertciesWithEdges();
		//System.out.println(g.getE(1).size());
		if (add_robot > 0) {
			//System.out.println("robot is set at: " + _x + "," + _y );
			Robot tmp_robot = new Robot(new Point3D(_x, _y));
			boolean ok = false, flag= false;
			double[] arr = null;
			for (int i=0; i< x.length && !flag; i++) {
				for (int j = 0; j < x[i].size() && !flag; j++) {
					
			//	System.out.println(g.getNode(i).getKey() + "->" + g.getNode(x[i].get(j).getDest()).getKey() + "[" + g.getNode(i).getLocation().x() + "," + g.getNode(i).getLocation().y() +  " - "+
					//	g.getNode(x[i].get(j).getDest()).getLocation().x() + "," + g.getNode(x[i].get(j).getDest()).getLocation().y()+"]");
				
					arr = line.distanceBetweenLineAndPoint(g.getNode(i).getLocation(),
					g.getNode(x[i].get(j).getDest()).getLocation(), new Point3D(_y, _x));
					System.out.println(arr[0]);
					if (arr[0] < 40) {System.out.println("OK!");  ok = true; tmp_robot = new Robot((new Point3D(arr[1],arr[2]))); flag=true;}	
				//	else { x[g.getNode(j).getKey()].remove(g.getNode(i).getKey());}
					}
				}
			
			if (!ok) {System.out.println("Please choose another point.");}
			else {
			game_mt.addRobot(tmp_robot);
			//System.out.println("new loc: " + arr[1] + ","  + arr[2]);
			myGameGui.picture(arr[2], arr[1] , "robot.png", 30,60);
			add_robot--;
			}
		}
	}
}