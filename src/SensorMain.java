import java.util.LinkedList;

import sensor.SensorController;


public class SensorMain {
	static String server = "127.0.0.1"; //args[0];
	static int port = 13400; //Integer.parseInt(args[1]);
	static volatile LinkedList<String> queue = new LinkedList<>();
	static final String fileName = "temperature";
	
	public static void main(String[] args) {
		//Initializes 10 sensor instances
		SensorController[] sensors = new SensorController[10];
		int i = 0;
		for (SensorController s : sensors){
		s = new SensorController(server,port, i);
		new Thread(s).start();
		i++;
		}
		

	}

}
