package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.ICalculator;

public class MeanClient {

	public static void main(String[] args) {

		try
		{
			Registry registry = LocateRegistry.getRegistry("localhost", 1099);
			ICalculator stub = (ICalculator) registry.lookup("Calculator");
			System.out.println(stub.calculateMean());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
