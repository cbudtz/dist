import client.MeanClient;


/**
 * @author Christian Budtz
 * Class for initializing an RMI client to get temperature measurements from temperature server
 */
public class ClientMain {

	public static void main(String[] args) {
		MeanClient mc = new MeanClient();
		mc.run();

	}

}
