import broker.EventBroker;


/**
 * @author Christian Budtz
 * Class for initializing EventBroker Middleware
 *
 */
public class BrokerMain {

	public static void main(String[] args) {
		final int RECEIVE_PORT = 5000; //Ingoing Port for publishers
		final int SEND_PORT = 6000; //Outgoing Port to subscribers
		final int SUBSCRIPTION_PORT = 7000; //Port for signing up for publishers
		final int SUBSCRIBER_PORT = 8000; //subscribers incoming port
		

		EventBroker broker = new EventBroker(RECEIVE_PORT, SEND_PORT, SUBSCRIPTION_PORT, SUBSCRIBER_PORT);
		broker.run();


	}

}
