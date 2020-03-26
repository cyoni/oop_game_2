package gui;

public class startGame {

	
	public static void main(String[] args) {
		//start_mouse_listener();
		Game_board gb = new Game_board(23);
		gb.initiate();
	    gb.start();
	}
}
