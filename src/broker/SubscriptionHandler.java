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
				
				char in;
				int charNo = 0;
				char[] temp = new char[DATALength];
				// waiting for data
				while((in = (char) inputStream.readByte()) == '_'){}
				do{
					// if trying to write data longer than 5 chars at a time
					// or if data starting with ',' or '.' breaks and runs try clause again
					if(charNo >= DATALength){
						System.err.println("wrong data format received");
						break; // goes outside whole try catch statement and closes client connection
					}
					temp[charNo] = in;
					charNo++;
				}while((in = (char) inputStream.readByte()) != '_');

				// writing temperature measurement to file 
				if(writeToProperty(String.valueOf(index), temp)){
					index++;	
				}
				
			} catch (IOException e) {
				System.out.println("Failed to accept connection from TCP client - Subscription handler");
				e.printStackTrace();
			}
			
		}
		

	}

}
