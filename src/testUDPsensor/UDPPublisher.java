package testUDPsensor;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

public class UDPPublisher implements Runnable {
	private UDPSensorController ctrl;
	private int port;
	private DatagramSocket socket;
	byte[] buffer = new byte[256];
	private long seqNo = 0;
	final int missedRep = 100;
	
	public UDPPublisher(UDPSensorController ctrl, int publishPort) {
		super();
		this.port=publishPort;
		this.ctrl = ctrl;
		while (true){ 
			try {
				this.socket = new DatagramSocket(port);
				break;
			} catch (BindException e ) {
				port++;
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void run() {
		while (true){
			while(!ctrl.queue.isEmpty()){
				pushData();
				System.out.println("pushed " + ctrl.queue.peek());
				ctrl.queue.pop();
			}
			delay(100);
		}
		
	}

	private void pushData() {
		// TODO Auto-generated method stub
		for(Subscriber s : ctrl.subscribers){
			if (s.answer+missedRep<seqNo){
				ctrl.subscribers.remove(s);
			} else {
			sendPacket(s.ip,ctrl.queue.peek(), seqNo);
			}
			
		}
		seqNo++;
		if (seqNo>Long.MAX_VALUE-missedRep) seqNo=0;//wrap numbers
	}

	private void sendPacket(String ip, String data, long seqNo) {
		data = "temp" + ";" + data + ";" + seqNo;
		buffer = data.getBytes();
		SocketAddress socketAddress = new InetSocketAddress(ip, port);
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, socketAddress);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void delay(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
