package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

public class TestUDPClient implements Runnable{
	final int PORT = 13400;
	private DatagramSocket socket;

	public TestUDPClient() throws SocketException {
		super();
		int i=0;
		while (true){
			try {
				this.socket = new DatagramSocket(PORT+i);
				break;
			} catch (Exception e ) {
				i++;
			}
		}
	}
	public static void main(String[] args) {
		//test main
		System.out.println("Starting client");
		TestUDPClient t;
		try {
			t = new TestUDPClient();
			Thread thread = new Thread(t);
			thread.start();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	@Override
	public void run() {
		System.out.println("Running Client");
		byte[] buffer = new byte[256];
		buffer = "Hello".getBytes();
		SocketAddress socketAddress = new InetSocketAddress("localhost", PORT);
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, socketAddress);
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
