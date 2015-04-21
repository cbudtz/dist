import java.util.LinkedList;

import sensor.SensorController;


/**
 * @author Christian Budtz
 * Class to initialize 10 sensor processes to demonstrate functionality
 */
public class SensorMain {
	static String server = "localhost"; //Hardcoded eventbroker address
	static int port = 13400; //random port
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
