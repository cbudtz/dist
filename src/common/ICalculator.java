package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICalculator extends Remote {
	public static final String calcName = "Calculator";
	public static final String host = "localhost";
	public static final int port = 1099;
	
	double calculateMean() throws RemoteException;
}
