package server;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.Policy;

public class Calculator implements ICalculator {

	public Calculator() throws RemoteException {
		super();
	}


	public double calculateMean() throws RemoteException {
		// TODO Auto-generated method stub
		return 5.5;
	}

	public static void main(String[] args) {
		try {
			Calculator calc = new Calculator();
			ICalculator stub = (ICalculator) UnicastRemoteObject.exportObject(calc, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.bind("Calculator", stub);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
