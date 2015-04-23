import client.MeanClient;


public class ClientMain {

	public static void main(String[] args) {
		//Start a client to connect to rmi server for average readings.
		MeanClient mc = new MeanClient();
		mc.run();

	}

}
