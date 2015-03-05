import java.util.LinkedList;

import sensor.ClientController;


public class SensorMain {
	static String server = "127.0.0.1"; //args[0];
	static int port = 9999; //Integer.parseInt(args[1]);
	static volatile LinkedList<String> queue = new LinkedList<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub Start a number of Sensors
		ClientController ctrl = new ClientController(server,port);
		ctrl.start();

	}

}
