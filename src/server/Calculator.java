package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class Calculator extends UnicastRemoteObject implements ICalculator {

	public Calculator() throws RemoteException {
		// TODO Auto-generated constructor stub
	}

	public Calculator(int arg0) throws RemoteException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public Calculator(int arg0, RMIClientSocketFactory arg1,
			RMIServerSocketFactory arg2) throws RemoteException {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

	public double calculateMean() throws RemoteException {
		// TODO Auto-generated method stub
		return 5.5;
	}

	public static void main(String[] args) {
		System.setProperty("java.rmi.server.hostname","localhost");
		try {
			Calculator calc = new Calculator();
			Naming.rebind("rmi://localhost:1099/Calculator", calc);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
