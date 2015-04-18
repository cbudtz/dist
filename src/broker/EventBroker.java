package broker;

import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.LinkedList;

public class EventBroker implements Runnable{
	int publisherPort;
	int subscriptionPort;
	private volatile HashMap<String, LinkedList<String>> subScribers; //Topic, subscriberList
	
	//Methods for handling subscribers
	public synchronized HashMap<String, LinkedList<String>> getSubScribers() {
		return subScribers;
	}
	public synchronized void setSubScribers(
			HashMap<String, LinkedList<String>> subScribers) {
		this.subScribers = subScribers;
	}
	private SubscriptionHandler subHandler;
	private EventDispatcher dispatcher;
	
	public EventBroker(int publisherPort, int subscriptionPort) {
		super();
		this.publisherPort = publisherPort;
		this.subscriptionPort = subscriptionPort;
	}
	@Override
	public void run() {
		//Start up subscription Handler
		subHandler = new SubscriptionHandler(subscriptionPort, this);
		Thread subThread = new Thread(subHandler);
		subThread.start();
		//Start up EventDispatcher
		dispatcher = new EventDispatcher(publisherPort,this);

		
	}
	
	
	
	
}
