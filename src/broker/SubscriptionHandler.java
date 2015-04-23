package broker;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class SubscriptionHandler implements Runnable {
	private ServerSocket subSocket;
	private EventBroker broker;
	private PingHandler pingHandler = new PingHandler(this);
	final int DATALength = 100;

	public SubscriptionHandler(int subPort, EventBroker broker) {
		super();
		try {
			this.subSocket = new ServerSocket(subPort);
		} catch (IOException e) {
			System.out.println("Socket Creation failed in Subscription Handler\n Another broker might be started on same IP");
			e.printStackTrace();
		}
		this.broker = broker;
	}

	@Override
	public void run() {
		//Start pingHandler - to keep a look out for dead subscribers...
		new Thread(pingHandler).start();
		while (true){
			try {
				//Waiting for connections
				Socket clientSocket = subSocket.accept();
				System.out.println("Subscriber connected");
				DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
				String data = inputStream.readUTF();
				parse(data);


			} catch (IOException e) {
				System.out.println("Failed to accept connection from TCP client - Subscription handler");
				e.printStackTrace();
			}

		}


	}

	private void parse(String data) {
		//Message is parsed and data extracted - hopefully it conforms to specified format
		String[] message = data.split(";");
		String messageType = message[0];
		String ip = message[1];
		String topic = message[2];
		//Check message type
		if (messageType.contains("sub")){
			broker.addSubscription(ip, topic);
			pingHandler.pingReceived(message[1]);
		} else if (messageType.contains("unroll")){
			broker.removeSubScription(ip, topic);
		} else if (messageType.contains("renew")){
			pingHandler.pingReceived(ip);
			broker.getGui().addToSubLog("Subscriber renewed lease:" + ip);		}

	}

	public LinkedList<String> getSubscribers() {
		return broker.getAllSubscribers();
		
	}

	public void remove(String ip) {
		broker.removeSubScriber(ip);
		
	}

}

