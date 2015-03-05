package sensor;

import java.util.LinkedList;

public class ClientController {
	volatile LinkedList<String> queue = new LinkedList<>();
	private Thread clientThread;
	private Thread datagenThread;
	private String host;
	private int port;
	
	public ClientController(String server, int port) {
		this.host = server;
		this.port = port;
	}



	public void start(){
		//One thread for sending data
		clientThread = new Thread(new Client(this,host,port));
		clientThread.start();
		//And one to generate new data - to ensure 3000 ms interval
		datagenThread = new Thread(new DataGenerator(this));
		datagenThread.start();

	}
	
	
}
