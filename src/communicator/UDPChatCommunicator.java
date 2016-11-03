package communicator;

import logger.LoggerStrategy;

import java.io.IOException;
import java.net.*;
import java.util.Objects;
import java.util.Observable;

/**
 * The communicator handles the network traffic between all chat clients.
 * Messages are sent and received via the UDP protocol which may lead to 
 * messages being lost.
 * 
 * @author Filip Harald
 */
public class UDPChatCommunicator extends Observable implements Runnable {
	private final int DATAGRAM_LENGTH = 100;
	private final int PORT = 6789;
	private final String MULTICAST_ADDRESS = "228.28.28.28";
	private LoggerStrategy _log = null;

	/**
	 * Creates a ChatCommunicator
	 *
	 * @param loggerStrategy the desired logger strategy
	 */
	public UDPChatCommunicator(LoggerStrategy loggerStrategy) {
		_log = loggerStrategy;
		/* force java to use IPv4 so we do not get 
		 * a problem when using IPv4 multicast address */		
		System.setProperty("java.net.preferIPv4Stack" , "true");
	}
	/**
	 * Sends the chat message to all clients
	 * 
	 * @param sender Name of the sender
	 * @param message Text message to send
	 * @throws IOException If there is an IO error
	 */
	public void sendChat(String sender, String message) {

		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket();
			String toSend = sender + ": " + message;
			byte[] b = toSend.getBytes();

			DatagramPacket datagram = new DatagramPacket(b, b.length,
					InetAddress.getByName(MULTICAST_ADDRESS), PORT);

			socket.send(datagram);
			socket.close();
		} catch (IOException e) {
			error(e);
		}
	}
	/**
	 * Starts to listen for messages from other clients
	 */
	public void startListen() {
		new Thread(this).start();
	}
	/**
	 * Listens for messages from other clients
	 *
	 * @throws IOException If there is an IO error
	 */
	private void listenForMessages() throws IOException {
		byte[] b = new byte[DATAGRAM_LENGTH];
		DatagramPacket datagram = new DatagramPacket(b, b.length);

		try(MulticastSocket socket = new MulticastSocket(PORT)) {
			socket.joinGroup(InetAddress.getByName(MULTICAST_ADDRESS));

			while(true) {
				socket.receive(datagram);
				String message = new String(datagram.getData());
				message = message.substring(0, datagram.getLength());
				setChanged();
				notifyObservers(message);
				log(message);
				datagram.setLength(b.length);
				throw new IOException();
			}
		}
	}
	@Override
	public void run() {
		try {
			this.listenForMessages();
		} catch (IOException e) {
			error(e);
		}
	}

	private void error(IOException e) {
		log(e);
		setChanged();
		notifyObservers(e);
	}

	private void log(Object message) {
		if (_log != null) {
			if (message instanceof String) {
				_log.log((String)message);
			} else {
				_log.log((Exception)message);
			}
		}
	}
}
