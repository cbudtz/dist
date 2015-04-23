import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import tempServer.Calculator;
import tempServer.TempListener;
import tempServer.TempServerController;


public class TempServerMain {

	public static void main(String[] args) {
		TempServerController ctrl = new TempServerController();
		ctrl.run();
		
	}
	

}
