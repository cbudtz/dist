package rmiServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICalculator extends Remote {

	double calculateMean() throws RemoteException;
}
