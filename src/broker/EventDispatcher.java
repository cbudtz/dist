package broker;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.LinkedList;

public class EventDispatcher implements Runnable {

	private EventBroker broker;

	private DatagramSocket inSocket;
	private DatagramSocket outSocket;
	private int subscriberPort;

	public EventDispatcher(int receivePort, int sendPort, int subscriberPort, EventBroker eventBroker) {
		this.broker = eventBroker;
		this.subscriberPort = subscriberPort;
		try {
			this.inSocket= new DatagramSocket(receivePort);
			this.outSocket = new DatagramSocket(sendPort);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	@Override
	public void run() {
		while (true){
			String [] message = receivePacket();//wait for new udp packet
			//check for malformed package
			if(message.length<3){
				broker.getGui().addToPubLog("Malformed package received:" + message);
			} else {
				//handle package - excess data trimmed...

				String topic = message[0]; //extract data
				String value = message[1];
				String sensorID = message[2];
				LinkedList<String> subscribers = broker.getSubscribersFromTopic(topic); //retrive subscriberList
				broker.getGui().addToPubLog("publication received: " + topic +" value:"+ value + "sensorID:" + sensorID);
				if (subscribers!=null){
					for (String subscriber : subscribers) {
						sendPacket(subscriber, topic, value, sensorID);
						broker.getGui().addToPubLog("publication dispatched to: " + subscriber);
					}
				}

			}

		}

	}

	private String[] receivePacket() {
		byte[] buffer = new byte[EventBroker.BUFFER_SIZE];
		DatagramPacket packet = new DatagramPacket(buffer , buffer.length);
		try {
			inSocket.receive(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String data = new String (packet.getData());
		String[] message = data.trim().split(";");
		return message;
	}

	private void sendPacket(String subscriber, String topic, String value,
			String sensorID) {
		byte[] buffer = new byte[EventBroker.BUFFER_SIZE];
		buffer = (topic +";" + value + ";" + sensorID).getBytes();
		SocketAddress socketAddress = new InetSocketAddress(subscriber, subscriberPort);
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, socketAddress);
		try {
			outSocket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
