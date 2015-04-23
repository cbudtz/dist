package broker;

import java.util.LinkedList;

public class PingHandler implements Runnable {
	private static final int SUBLEASETIME = 60; //Minutes
	private SubscriptionHandler subHandler;
	private LinkedList<String> pingList = new LinkedList<String>();

	public PingHandler(SubscriptionHandler subscriptionHandler) {
		this.subHandler=subscriptionHandler;
	}

	@Override
	public void run() {
		while (true){
		delay();
		//If your Ip hasn't been removed from the pingList - you're out!
		for (String ip : pingList) {
				subHandler.remove(ip);
		}
		//Getting subscriber list again
		pingList = subHandler.getSubscribers();

		}
	}

	private void delay() {
		try {
			Thread.sleep(SUBLEASETIME*60*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void pingReceived(String ip){
		pingList.remove(ip);
	}

}
