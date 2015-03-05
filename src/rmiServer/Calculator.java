package rmiServer;

import java.rmi.registry.Registry;

import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.ICalculator;
import common.PropertyHelper;

public class Calculator implements ICalculator {

	public Calculator() throws RemoteException {
		super();
	}


	public double calculateMean() throws RemoteException {
		//TODO something meaningful to calculate a mean 
		
		return Double.parseDouble(PropertyHelper.readFromProperty(ICalculator.fileName, "mean"));
	}

	public static void main(String[] args) {
		try {
			Calculator calc = new Calculator();
			ICalculator stub = (ICalculator) UnicastRemoteObject.exportObject(calc, 0);
			Registry registry = LocateRegistry.createRegistry(ICalculator.port);
			registry.bind(ICalculator.calcName, stub);
			System.out.println("RMIServer is running...");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
