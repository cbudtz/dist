package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import server.ICalculator;

public class MeanClient {

	public static void main(String[] args) {
		double value = 0;
		System.setSecurityManager(new SecurityManager());

		try
		{
			ICalculator obj = (ICalculator) Naming.lookup("rmi://localhost/Calculator");
			System.out.println(obj.calculateMean());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
