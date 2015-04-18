package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import broker.EventBroker;

/**
 * 
 * @author Christian s134000
 *
 */
public class TestUDPServer implements Runnable{
	final int PORT = EventBroker.SEND_PORT;
	private DatagramSocket socket;

	public TestUDPServer() throws SocketException {
		super();
		this.socket = new DatagramSocket(PORT);
	}

	public static void main(String[] args) {
		//test main
		System.out.println("Starting server");
		TestUDPServer t;
		try {
			t = new TestUDPServer();
			Thread thread = new Thread(t);
			thread.start();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		System.out.println("Running server");
		byte[] buffer = new byte[256];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		try {
			socket.receive(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String received= new String(packet.getData());
		System.out.println(received);
	}

}
