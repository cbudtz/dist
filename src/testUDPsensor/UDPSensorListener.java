package testUDPsensor;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;


public class UDPSensorListener implements Runnable{
	UDPSensorController ctrl;
	private int port;
	private DatagramSocket socket;
	byte[] buffer = new byte[256]; //Large enough

	UDPSensorListener(UDPSensorController sensorController, int port) throws SocketException{
		this.ctrl = sensorController;
		this.port = port;
		//Find an available port (only needed if you run server on same machine
		while (true){ 
			try {
				this.socket = new DatagramSocket(port);
				socket.setBroadcast(true);//makes it able to listen to broadcasts
				break;
			} catch (BindException e ) {
				port++;
			}
		}
	}

	@Override
	public void run() {		
		while(true){
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			try {
				socket.receive(packet);
				String received= new String(packet.getData());
				String[] command = received.split(";");
				if (command[0]== ctrl.SUB){		
					ctrl.subscribers.add(new Subscriber(packet.getAddress().getHostAddress()));
					System.out.println("Added new subscriber:" + packet.getAddress().getHostAddress());
				} else if (command[0]==ctrl.ACK){
					for (Subscriber	s : ctrl.subscribers) {
						if (s.ip== packet.getAddress().getHostAddress()){
							long ackNo = Integer.getInteger(command[1]);
							if (s.answer<ackNo)	s.answer=ackNo;//update ack
						}
					}
				}
			} catch (SocketTimeoutException e) {
				//No problem - just no data
				System.out.println("No new data - reading on port:" + port);
			} catch (IOException e1){
				e1.printStackTrace();
			}

		}

	}

}

