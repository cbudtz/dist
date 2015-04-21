package broker;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SubscriptionHandler implements Runnable {
	private static final int TIME_OUT = 500;
	private ServerSocket subSocket;
	private EventBroker broker;
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
		while (true){
			try  {
				//Waiting for connections
				Socket clientSocket = subSocket.accept();
				clientSocket.setSoTimeout(TIME_OUT);//To avoid stupid subscribers from locking up system ;)
				//TODO could be improved with a seperate process so multiple subscribers could subscribe simultaneously
				System.out.println("Subscriber connected");
				DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
				String data = inputStream.readUTF();
				parse(data);
				clientSocket.close();

			} catch (IOException e) {
				System.out.println("Failed to accept connection from TCP client - Subscription handler");
				e.printStackTrace();
				
			} finally{
				
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

