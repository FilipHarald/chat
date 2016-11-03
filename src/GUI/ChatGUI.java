package GUI;

import communicator.UDPChatCommunicator;
import logger.SystemOutLoggerStrategy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * The main GUI for a simple chat application. 
 * 
 * @author Filip Harald
 */
public class ChatGUI extends JFrame implements Observer {

	private static final long serialVersionUID = -6901406569465760897L;
	private JTextArea _chatArea, _messageArea;
	private JButton _sendButton;
	private UDPChatCommunicator _communicator;
	private String _user;

	/**
	 * Creates a ChatGUI
	 * 
	 * @param userName the name to use in the chat
	 */
	public ChatGUI(String userName) {
		this.setTitle("Simple Chat - " + userName);
		_user = userName;
        SystemOutLoggerStrategy log = new SystemOutLoggerStrategy();
		_communicator = new UDPChatCommunicator();
		_communicator.addObserver(this);
		_communicator.addObserver(log);
		this.initializeGUI();
		this.addGUIListeners();		
		_communicator.startListen();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * Initializes the GUI
	 */
	private void initializeGUI() {
		_chatArea = new JTextArea(25, 1);
		_messageArea = new JTextArea(3, 10);
		_sendButton = new JButton("Send");

		_messageArea.setLineWrap(true);
		_messageArea.setBorder(BorderFactory.createLineBorder(Color.black));
		_chatArea.setEnabled(false);
		_chatArea.setLineWrap(true);

		Container contentPane = this.getContentPane();

		contentPane.add(_chatArea, BorderLayout.NORTH);
		contentPane.add(_messageArea, BorderLayout.WEST);
		contentPane.add(_sendButton, BorderLayout.CENTER);

		this.setSize(200, 500);
		this.setResizable(false);
	}
	/**
	 * Adds GUI related listeners
	 */
	private void addGUIListeners() {
		_sendButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}});
		_messageArea.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (_messageArea.getText().length() > 0) {
						_messageArea.setText(_messageArea.getText().substring(0, _messageArea.getText().length() - 1));
						sendMessage();
					}
				}
			}});		
	}
	/**
	 * Send current message to all users
	 */
	private void sendMessage() {
		_communicator.sendChat(_user, _messageArea.getText());
		_messageArea.setText("");
		_messageArea.grabFocus();
	}

    /**
     * Receives message from all users
     *
     * @param message The received message
     */
    public void receiveMessage(String message) {
        _chatArea.append(message + "\n");
    }

	/**
	 * Informs the user that an error has occurred and exits the application
	 */
	private void error() {
		JOptionPane.showMessageDialog(this, "An error has occured and the application will close", "Error", JOptionPane.WARNING_MESSAGE);

		this.setVisible(false);
		this.dispose();
		System.exit(ERROR);
	}
    /**
     * {@inheritDoc}
     */
	@Override
	public void update(Observable o, Object arg) {
        if (arg instanceof Exception) {
			error();
		} else {
			receiveMessage((String)arg);
		}
	}
}