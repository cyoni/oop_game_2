package utils;

import javax.swing.JOptionPane;

public class User_Dialog {
	
	/**
	 * This method pops up a dialog where it asks the user to fill up something according to 'content'
	 * @param title
	 * @return
	 */
	public String getInputDialog(String content) {
		return JOptionPane.showInputDialog(content);
	}
	
	
	/*
	 * This method alerts a message to the user.
	 */
	public void showAlert(String content) {
		JOptionPane.showMessageDialog(null, content);
	}

}
