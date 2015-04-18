package broker;

public class EventDispatcher implements Runnable {

	private int publisherPort;
	private EventBroker broker;

	public EventDispatcher(int publisherPort, EventBroker eventBroker) {
		this.publisherPort = publisherPort;
		this.broker = eventBroker;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
