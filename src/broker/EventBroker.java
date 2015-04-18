package broker;

import java.awt.EventQueue;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.LinkedList;

public class EventBroker implements Runnable{
	//Just some random ports
	public static final int RECEIVE_PORT = 5000;
	public static final int SEND_PORT = 6000;
	public static final int SUBSCRIPTION_PORT = 7000;

	private int subscriptionPort;
	private int receivePort;
	int sendPort;
	//collection of subscriptions
	private volatile HashMap<String, LinkedList<String>> subScribers; //Topic, subscriberList
	//Seperate controllers for subscription and Eventdispatching
	private SubscriptionHandler subHandler;
	private EventDispatcher dispatcher;
	//Gui to show what happens in broker (slight overkill)
	private BrokerGUI gui;

	public EventBroker(int receivePort, int sendPort, int subscriptionPort) {
		super();
		this.subScribers = new HashMap<String,LinkedList<String>>();
		this.receivePort = receivePort;
		this.sendPort = sendPort;
		this.subscriptionPort = subscriptionPort;
		this.gui = new BrokerGUI();
	}
	@Override
	public void run() {
		//Start up Gui
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {				
					gui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		//Start up subscription Handler
		subHandler = new SubscriptionHandler(subscriptionPort, this);
		Thread subThread = new Thread(subHandler);
		subThread.start();
		gui.setSubPort(subscriptionPort);
		//Start up EventDispatcher
		dispatcher = new EventDispatcher(receivePort, sendPort, this);
		Thread dispatcherThread = new Thread(dispatcher);
		dispatcherThread.start();
		gui.setReceivePort(receivePort);
		gui.setSendPort(sendPort);


	}

	public void addSubscription(String ip, String topic){
		LinkedList<String> topicSubs;
		if (subScribers.containsKey(topic)) {
			topicSubs = subScribers.get(topic);			
		}else {
			topicSubs = new LinkedList<String>();
		}
		if (!topicSubs.contains(ip)){
			topicSubs.add(ip);
			subScribers.put(topic, topicSubs);
			gui.addToSubLog("new sub: "+  ip +" topic: " + topic);
		} else {
			gui.addToSubLog("Already subscribing:" +  ip +" topic: " + topic);
		}
		System.out.println(subScribers.toString());
	}
	public void removeSubScription(String topic, String ip){
		if (subScribers.containsKey(topic)) {
			LinkedList<String> topicSubs = subScribers.get(topic);
			topicSubs.remove(ip);
			gui.addToSubLog("removed subscription: " + ip + "topic: " + topic );
		} else {
			gui.addToSubLog("No such subscription:" + ip + topic);
		}

	}

	public static void main(String[] args){
		EventBroker broker = new EventBroker(RECEIVE_PORT, SEND_PORT, SUBSCRIPTION_PORT);
		broker.run();
	}


}
