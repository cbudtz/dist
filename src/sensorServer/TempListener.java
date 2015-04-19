package sensorServer;
import java.net.*;
import java.io.*;

import broker.EventBroker;
import common.PropertyHelper;


public class TempListener implements Runnable{
	private static final String TEMP = "temp";
	private static final String SUB = "sub";
	private static final String UNROLL = "unroll"; 

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
		//TODO set up tcp socket and subscribe...
		try {
			sendMessage(SUB, OwnIP, TEMP);
		} catch (UnknownHostException e1) {
			System.out.println("Not able to connect to broker");
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//DataInputStream inputStream = null;
		//TODO setup listening UDP socket.
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
}
