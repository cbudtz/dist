package testUDPsensor;

import java.net.SocketException;
import java.util.LinkedList;

public class UDPSensorController implements Runnable {
	volatile LinkedList<String> queue = new LinkedList<>();//Protected list of data to send to server
	volatile LinkedList<Subscriber> subscribers = new LinkedList<>();
	private Thread listenerThread;//thread for connection to server
	private Thread publisherThread;
	private Thread datagenThread;//Thread to simulate data
	private int publishPort;////Common port for communication
	private int listenPort;
	public final String ACK = "ack";
	public final String SUB = "sub"; 
	
	public UDPSensorController(int publishPort, int listenPort) {
		this.publishPort = publishPort;
		this.listenPort = listenPort;
	}



	public void run(){
		//One thread for sending data
		try {
			listenerThread = new Thread(new UDPSensorListener(this,listenPort));
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listenerThread.start();
		publisherThread = new Thread(new UDPPublisher(this,publishPort));
		publisherThread.start();
		//And one to generate new data - to ensure 3000 ms interval
		datagenThread = new Thread(new UDPDataGenerator(this));
		datagenThread.start();

	}
	
}
