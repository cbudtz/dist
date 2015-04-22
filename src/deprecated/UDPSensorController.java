package deprecated;

import java.util.LinkedList;

public class UDPSensorController implements Runnable {
	volatile LinkedList<String> queue = new LinkedList<>();//Protected list of date to send to server
	private Thread clientThread;//thread for connection to server	
	private Thread datagenThread;//Thread to simulate data
	private int port;////Common port for communication
	
	public UDPSensorController(String server, int port) {
		this.port = port;
	}



	public void run(){
		//One thread for sending data
		clientThread = new Thread(new UDPClient(this,port));
		clientThread.start();
		//And one to generate new data - to ensure 3000 ms interval
		datagenThread = new Thread(new UDPDataGenerator(this));
		datagenThread.start();

	}
	
	
}
