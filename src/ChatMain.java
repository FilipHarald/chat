import GUI.ChatGUI;

import javax.swing.JOptionPane;

/**
 * Program entry point. A simple chat application that lets you chat with
 * all others with the same application. In this version there is only one
 * global channel and users can not create or join other channels. 
 * 
 * @author Thomas Ejnefj�ll
 * @version 0.1
 */
public class ChatMain {
	/**
	 * Program entry point
	 * 
	 * @param args a name to use in the chat, if no name is provided via main 
	 * 			   the program will prompt the user for one
	 */
	public static void main(String[] args) {
		String userName = "";

		if (args.length > 0) {
			userName = args[0];
		}

		while(userName == null || userName.length() < 1) {
			userName = JOptionPane.showInputDialog(null, "Enter your name", "Name", JOptionPane.QUESTION_MESSAGE);
		}
		new ChatGUI(userName).setVisible(true);
	}
}