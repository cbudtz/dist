package tempServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import common.ICalculator;

public class TempServerController implements Runnable{
	TempListener sensorServer;
	Calculator calc;

	@Override
	public void run() {
		// Start an RMI server and SensorServer in different Threads.
		//Start RMI server.
		try {
			calc = new Calculator();
			ICalculator stub = (ICalculator) UnicastRemoteObject.exportObject(calc, 0);
			Registry registry = LocateRegistry.createRegistry(ICalculator.port);
			registry.bind(ICalculator.calcName, stub);
			System.out.println("RMIServer is running...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("RMIServer started");
		
		//Start sensor server - listens for temp events and stores them on disk.
		sensorServer = new TempListener();
		Thread sensorServerThread = new Thread(sensorServer);
		sensorServerThread.start();
		System.out.println("Sensor Server started");
		
	}
	
}
