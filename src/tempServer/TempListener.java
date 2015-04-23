package tempServer;
import java.net.*;
import java.io.*;

import broker.EventBroker;
import common.PropertyHelper;


public class TempListener implements Runnable{
	private static final String TEMP = "temp";
	private static final String SUB = "sub";
	private static final String UNROLL = "unroll"; 
	private static final String RENEW = "renew";

	final int BROKER_PORT = EventBroker.SEND_PORT;
	final int OWN_PORT = EventBroker.SUBSCRIBER_PORT;
	final String BrokerIP = "localhost"; //TODO UDP discovery of broker
	final String OwnIP = "localhost";
	final int SUBSCRIPTION_PORT = EventBroker.SUBSCRIPTION_PORT;
	private DatagramSocket incomingSocket;

	public static final String FILE_NAME = "temperature";
	public static final int DATA_SIZE = 5;
	public static void main(String[] args){
		TempListener s = new TempListener();
		s.run();
	}

	public void run(){
		//ServerSocket sensorSocket = null;
		//Socket clientSocket = null;
		//TODO set up subScription and Ping
		ServerPinger s = new ServerPinger();
		new Thread(s).start();

		//Setting up UDP listening socket
		try{
			incomingSocket = new DatagramSocket(OWN_PORT);

			System.out.println("Socket ready");
		} catch(IOException e){
			System.err.println("failed to create socket: " + e.getMessage());
		}
		int index = PropertyHelper.findLastIndex();
		while(true){
			try{
				byte[] buffer = new byte[256];
				DatagramPacket udpPacket = new DatagramPacket(buffer, buffer.length);
				incomingSocket.receive(udpPacket);
				String data = new String(udpPacket.getData());
				String[] message = data.split(";");

				if(writeToProperty(String.valueOf(index), message[1])){
					index++;	
				}


			}catch(IOException e){
				System.err.println("failed to read Packet: " + e.getMessage());
				break;
			}



		}	

		incomingSocket.close();
		try {
			sendMessage(UNROLL, BrokerIP, TEMP);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendMessage(String head, String ip, String topic) throws UnknownHostException, IOException {
		Socket subSocket = new Socket(BrokerIP, SUBSCRIPTION_PORT);
		DataOutputStream out = new DataOutputStream(subSocket.getOutputStream());
		out.writeUTF(head + ";" + ip +";"+topic);
		subSocket.close();
	}

	/**
	 * verifies input and write data to file
	 * @param key should be integer so the index can be used for calculating total and mean
	 * @param val char[] that will be verified, can have the form (\d+((.|,)(\d+))?)
	 * @return if not able to convert to float false will be returned else data will be saved and true returned
	 */
	@SuppressWarnings("unused")
	private boolean writeToProperty(String key, char[] val){
		String value = "";
		for(int i = 0; i<val.length;i++){
			if(val[i] != '\u0000'){
				value = value + val[i];
			}
		}
		return writeToProperty(key, value);
	}
	private boolean writeToProperty(String key, String value){

		value = value.replace(',', '.');
		try{
			Float.valueOf(value);
		}catch(NumberFormatException e){
			return false;
		}		
		PropertyHelper.writeToProperty(FILE_NAME, key, value);
		//		String total = PropertyHelper.readFromProperty(FILE_NAME, "total");
		//		if(total == null){
		//			PropertyHelper.writeToProperty(FILE_NAME, "total", value);
		//		}else{
		//			float newTotal = Float.valueOf(total)+Float.valueOf(value);
		//			PropertyHelper.writeToProperty(FILE_NAME, "total", String.valueOf(newTotal));
		//		}
		return true;
	}

	private class ServerPinger implements Runnable {



		private static final int RENEWINTERVAL = 5;

		@Override
		public void run() {
			while (true) {
				//Try to Subscribe
				while (true){
					try {
						sendMessage(SUB, OwnIP, TEMP);
						// if there is a broker that replies - then go to renew mode...
						break;
					} catch (UnknownHostException e1) {
						System.err.println("Not able to connect to broker");
						e1.printStackTrace();
					} catch (IOException e1) {
						System.err.println("Not able to connect to broker");
					}
				}
				//Keep renewing lease
				while(true){
					
					delay();
					try {
						sendMessage(RENEW, OwnIP, RENEW);
					} catch (UnknownHostException e) {
						//Broker went offline - back to trying to subscribe
						e.printStackTrace();
						break;
					} catch (IOException e) {
						System.err.println("Not able to connect to broker");
						break;
					}
				}
			}

		}

		private void delay() {
			try {
				Thread.sleep(1000*RENEWINTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
