package test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import broker.EventBroker;

public class TCPConnectTest {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", EventBroker.SUBSCRIPTION_PORT);
			DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
			stream.writeUTF("sub;localhost;temp");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
