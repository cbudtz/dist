import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import rmiServer.Calculator;
import sensorServer.TempListener;
import common.ICalculator;


/**
 * @author Christian Budtz
 * Class for instantiating RMI registry, RMI server and subscriber
 */
public class TempServerMain {

	public static void main(String[] args) {
		//  Start an RMI server and SensorServer in different Threads.
		//Start RMI server
		try {
			Calculator calc = new Calculator();
			ICalculator stub = (ICalculator) UnicastRemoteObject.exportObject(calc, 0);
			Registry registry = LocateRegistry.createRegistry(ICalculator.port);
			registry.bind(ICalculator.calcName, stub);
			System.out.println("RMIServer is running...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("RMIServer started");
		
		//Start sensor server
		TempListener sensorServer = new TempListener();
		Thread sensorServerThread = new Thread(sensorServer);
		sensorServerThread.start();
		System.out.println("Sensor Server started");
		
	}

}
