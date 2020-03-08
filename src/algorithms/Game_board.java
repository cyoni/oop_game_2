package algorithms;

import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import dataStructure.game_metadata;
import dataStructure.graph;
import gui.Graph_draw;
import gui.MyGameGUI;
import items.Fruit;
import oop_dataStructure.OOP_DGraph;

public class Game_board{
	 int scenario_num;
	 MyGameGUI myGameGui;
	 game_metadata game_mt; // contains graph, list of fruits, robots
	 
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
			int rs = ttt.getInt("robots");
			System.out.println(info);
			System.out.println(graph_json); // json text
			
			ReadJSON r = new ReadJSON(myGameGui.f);
			

			game_mt = r.ReadJson_graphAndFruits(graph_json, game.getFruits());

			System.out.println(game_mt.fruits.size());
			Graph_draw gd = new Graph_draw(myGameGui);
			gd.draw(game_mt.g);
			 
			
			/// לצייר את הפירות על הגרף. לתת ליוזר למקם את הרובוטים
			
			drawFruits();
			
			
			// the list of fruits should be considered in your solution
			Iterator<String> f_iter = game.getFruits().iterator();
			while(f_iter.hasNext()) {System.out.println(f_iter.next() + "!!");}	
			
			int src_node = 0;  // arbitrary node, you should start at one of the fruits
			for(int a = 0;a<rs;a++) {
				game.addRobot(src_node+a);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
		game.startGame();
		// should be a Thread!!!
		while(game.isRunning()) {
		//	moveRobots(game, gg);
		}
		String results = game.toString();
		System.out.println("Game Over: "+results);
		
	}

	/**
	 * This function draws the fruits on the screen.
	 * Apples will be printed on edges that its edge increases e.g   2---->6
	 * Strawberries will be printed on edges that its edge decreases e.g  5---->2*/
	
	private void drawFruits() {

		if (game_mt.fruits.isEmpty()) return;
		
		for (int i=0; i < game_mt.fruits.size(); i++) {
			Fruit current_fruit = game_mt.fruits.get(i);
			
			System.out.println(current_fruit.getPos().x());
			
			myGameGui.picture(current_fruit.getPos().x(), current_fruit.getPos().y() , "apple.png", 40,100 );
			
			
		}
		
	}
	
	
}