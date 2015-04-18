package broker;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SubscriptionHandler implements Runnable {
	private ServerSocket subSocket;
	private EventBroker broker;
	final int DATALength = 100;

	public SubscriptionHandler(int subPort, EventBroker broker) {
		super();
		try {
			this.subSocket = new ServerSocket(subPort);
		} catch (IOException e) {
			System.out.println("Socket Creation failed in Subscription Handler");
			e.printStackTrace();
		}
		this.broker = broker;
	}

	@Override
	public void run() {
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
		String[] message = data.split(";");
		if (message[0].contains("sub")){
			broker.addSubscription(message[1], message[2]);
		}

	}

}

