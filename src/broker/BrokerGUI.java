package broker;

import java.awt.EventQueue;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class BrokerGUI {
	JList<Object> subscriberLog;
	JList<Object> publicationLog;
	LinkedList<String> subscriberLogData = new LinkedList<>();
	LinkedList<String> publicationLogData = new LinkedList<>();
	
	private JFrame frame;
	private JTextField subPortTextField;
	private JTextField receivePortTextField;
	private JTextField sendPortTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrokerGUI window = new BrokerGUI();
					window.frame.setVisible(true);
					window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");window.addToSubLog("Test");
					window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");window.addToPubLog("Test");
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
		frame = new JFrame();
		frame.setBounds(100, 100, 860, 375);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		//Scroll Pane for subscriptions
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 242, 282);
		frame.getContentPane().add(scrollPane);
		//Jlist for subscriptions
		JList subLog = new JList();
		this.subscriberLog = subLog;
		scrollPane.setViewportView(subLog);
		//ScrollPane for publications
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(592, 44, 242, 282);
		frame.getContentPane().add(scrollPane_1);
		//Jlist for publications
		JList<Object> publog = new JList<Object>();
		this.publicationLog = publog;
		scrollPane_1.setViewportView(publog);
		
		JLabel lblNewLabel = new JLabel("Subscription Log");
		lblNewLabel.setBounds(10, 11, 125, 22);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblPublicationLog = new JLabel("Publication Log");
		lblPublicationLog.setBounds(592, 11, 125, 22);
		frame.getContentPane().add(lblPublicationLog);
		
		JLabel lblSubscriptionPort = new JLabel("Subscription Port");
		lblSubscriptionPort.setBounds(262, 48, 96, 22);
		frame.getContentPane().add(lblSubscriptionPort);
		
		JLabel lblReceivePort = new JLabel("Receive Port");
		lblReceivePort.setBounds(262, 76, 96, 22);
		frame.getContentPane().add(lblReceivePort);
		
		JLabel lblSendPort = new JLabel("Send Port");
		lblSendPort.setBounds(262, 104, 96, 22);
		frame.getContentPane().add(lblSendPort);
		
		subPortTextField = new JTextField();
		subPortTextField.setText("...");
		subPortTextField.setBounds(382, 48, 69, 22);
		frame.getContentPane().add(subPortTextField);
		subPortTextField.setColumns(10);
		
		receivePortTextField = new JTextField();
		receivePortTextField.setText("...");
		receivePortTextField.setColumns(10);
		receivePortTextField.setBounds(382, 76, 69, 22);
		frame.getContentPane().add(receivePortTextField);
		
		sendPortTextField = new JTextField();
		sendPortTextField.setText("...");
		sendPortTextField.setColumns(10);
		sendPortTextField.setBounds(382, 104, 69, 22);
		frame.getContentPane().add(sendPortTextField);
		
		
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

}
