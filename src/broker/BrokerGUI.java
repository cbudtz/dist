package broker;

import java.awt.EventQueue;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author Christian Budtz
 * Convenience GUI - to show what happens in broker
 */
public class BrokerGUI {
	private JList<Object> subscriberLog;
	private JList<Object> publicationLog;
	private LinkedList<String> subscriberLogData = new LinkedList<>();
	private LinkedList<String> publicationLogData = new LinkedList<>();
	
	private JFrame frmEventbroker;
	private JTextField subPortTextField;
	private JTextField receivePortTextField;
	private JTextField sendPortTextField;

	/**
	 * Test Main
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrokerGUI window = new BrokerGUI();
					window.frmEventbroker.setVisible(true);
					window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");
					window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");
					window.setSubPort(23);
					window.setReceivePort(24);
					window.setSendPort(25);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BrokerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEventbroker = new JFrame();
		frmEventbroker.setTitle("EventBroker");
		frmEventbroker.setBounds(100, 100, 1178, 442);
		frmEventbroker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEventbroker.getContentPane().setLayout(null);
		//Scroll Pane for subscriptions
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 437, 349);
		frmEventbroker.getContentPane().add(scrollPane);
		//Jlist for subscriptions
		JList<Object> subLog = new JList<Object>();
		this.subscriberLog = subLog;
		scrollPane.setViewportView(subLog);
		//ScrollPane for publications
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(715, 44, 437, 349);
		frmEventbroker.getContentPane().add(scrollPane_1);
		//Jlist for publications
		JList<Object> publog = new JList<Object>();
		this.publicationLog = publog;
		scrollPane_1.setViewportView(publog);
		
		JLabel lblNewLabel = new JLabel("Subscription Log");
		lblNewLabel.setBounds(10, 11, 125, 22);
		frmEventbroker.getContentPane().add(lblNewLabel);
		
		JLabel lblPublicationLog = new JLabel("Publication Log");
		lblPublicationLog.setBounds(715, 11, 125, 22);
		frmEventbroker.getContentPane().add(lblPublicationLog);
		
		JLabel lblSubscriptionPort = new JLabel("Subscription Port");
		lblSubscriptionPort.setBounds(516, 44, 110, 22);
		frmEventbroker.getContentPane().add(lblSubscriptionPort);
		
		JLabel lblReceivePort = new JLabel("Receive Port");
		lblReceivePort.setBounds(516, 72, 110, 22);
		frmEventbroker.getContentPane().add(lblReceivePort);
		
		JLabel lblSendPort = new JLabel("Send Port");
		lblSendPort.setBounds(516, 100, 110, 22);
		frmEventbroker.getContentPane().add(lblSendPort);
		
		subPortTextField = new JTextField();
		subPortTextField.setText("...");
		subPortTextField.setBounds(636, 44, 69, 22);
		frmEventbroker.getContentPane().add(subPortTextField);
		subPortTextField.setColumns(10);
		
		receivePortTextField = new JTextField();
		receivePortTextField.setText("...");
		receivePortTextField.setColumns(10);
		receivePortTextField.setBounds(636, 72, 69, 22);
		frmEventbroker.getContentPane().add(receivePortTextField);
		
		sendPortTextField = new JTextField();
		sendPortTextField.setText("...");
		sendPortTextField.setColumns(10);
		sendPortTextField.setBounds(636, 100, 69, 22);
		frmEventbroker.getContentPane().add(sendPortTextField);
		
		
	}
	
	public void addToSubLog(String text){
		subscriberLogData.add(text);
		Object[] listData = subscriberLogData.toArray();
		
		subscriberLog.setListData(listData);
		subscriberLog.ensureIndexIsVisible(listData.length-1);
		
	}
	
	public void addToPubLog(String text){
		publicationLogData.add(text);
		Object[] listData = publicationLogData.toArray();
		
		publicationLog.setListData(listData);
		publicationLog.ensureIndexIsVisible(listData.length-1);
		
	}
	
	public void setSubPort(int port){
		subPortTextField.setText(String.valueOf(port));
	}
	
	public void setReceivePort(int port){
		receivePortTextField.setText(String.valueOf(port));
	}
	
	public void setSendPort(int port){
		sendPortTextField.setText(String.valueOf(port));
	}
	
	public void setVisible(boolean b){
		frmEventbroker.setVisible(b);
	}

}
