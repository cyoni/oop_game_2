package threads;

import java.util.Queue;

import algorithms.Graph_Algo;
import dataStructure.game_metadata;
import dataStructure.node_data;
import items.Fruit;
import items.Robot;

public class manageRobot extends Thread {
	
	private game_metadata game_mt;
	private Robot robot;

	public manageRobot(game_metadata game_mt, Robot robot) {
		this.game_mt = game_mt;
		this.robot = robot;
	}
	
	public void run() {
		System.out.println("Thread #" + getId() + " started");
		while (game_mt.service.isRunning()) {
			try {
				sleep(500);
				if (robot.isBusy()) {
					moveMe();
				}
				else {
					game_mt.controller.allocateFruitToRobot(robot); // should return a path and fruit 
						// use TBP (algo)
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} // end while
	}

	private void moveMe() {
			if (!robot.getPath_to_target().isEmpty() && robot.getDest() == -1) {
				int go_where = robot.getPath_to_target().poll().getKey();
				System.out.println("Popped (#"+ robot.getID() +"): " + go_where);
				System.out.println("ROBOT #" + robot.getID() + ", IS GOING TO: " + go_where);
				robot.setDest(go_where);
				game_mt.service.chooseNextEdge(robot.getID(), go_where); // request the robot to move
			}
			if (robot.getPath_to_target().size() == 0) {robot.setIsBusy(false); System.out.println("Robot " + robot.getID() + " is free.");}
	}
}
