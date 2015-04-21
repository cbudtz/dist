package sensor;

import java.util.LinkedList;

public class SensorController implements Runnable {
	volatile LinkedList<String> queue = new LinkedList<>();
	private Thread clientThread;
	private Thread datagenThread;
	private String host;
	private int port;
	private int sensorID;
	
	public SensorController(String server, int port, int sensorID) {
		this.host = server;
		this.port = port;
		this.sensorID = sensorID;
	}



	public void run(){
		//One thread for sending data
		clientThread = new Thread(new Client(this,host,port, sensorID));
		clientThread.start();
		//And one to generate new data - to ensure 3000 ms interval
		datagenThread = new Thread(new DataGenerator(this));
		datagenThread.start();

	}
	
	
}
