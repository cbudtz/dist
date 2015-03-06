package client;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.Box;
import javax.swing.border.BevelBorder;

public class ClientWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow window = new ClientWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 460, 109);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		
		JLabel lblCurrentMean = new JLabel("Current mean");
		panel.add(lblCurrentMean);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(Color.BLACK);
		
		JButton btnNewButton = new JButton("Get Mean");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1);
		
		JLabel lblConnectionStatus = new JLabel("connection status");
		panel_1.add(lblConnectionStatus);
		
		JLabel lblNewLabel = new JLabel("Connecting...");
		lblNewLabel.setForeground(Color.ORANGE);
		panel_1.add(lblNewLabel);
	}

}
