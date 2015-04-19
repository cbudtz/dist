package sensor;

import java.io.IOException;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import broker.EventBroker;

class Client implements Runnable{
	private static final String TEMP = "temp";
	SensorController ctrl;
	private String brokerIP;
	private int port;
	
	private DatagramSocket sendSocket;

	Client(SensorController ctrl, String host, int port){
		this.ctrl = ctrl;
		this.brokerIP = host;
		this.port = port;
		try {
			this.sendSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true){
			while(!ctrl.queue.isEmpty()){
				try{
					pushData(brokerIP, port, ctrl.queue.peek());
					System.out.println("pushed " + ctrl.queue.peek());
					ctrl.queue.pop();
				} catch (ConnectException ce){
					System.out.println("No connection");
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void pushData(String hostname, int port, String data) throws ConnectException {
		byte[] buffer = new byte[EventBroker.BUFFER_SIZE];
		buffer = (TEMP + ";" + data + ";" + "1").getBytes();
		SocketAddress socketAddress = new InetSocketAddress(brokerIP, EventBroker.RECEIVE_PORT);
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, socketAddress);
		try {
			sendSocket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}