package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class MainWindow {

	private JFrame frame;
	private JTextField txtN;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public MainWindow() {
		initialize();
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(10, 200, 255, 232);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnStartSort = new JButton("Start Sort");
		btnStartSort.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnStartSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Test.startSort();
				//Test.refresh();
				BST.startSort();
			}
		});
		btnStartSort.setBounds(10, 10, 115, 35);
		frame.getContentPane().add(btnStartSort);
		
		JButton btnRandomize = new JButton("Randomize");
		btnRandomize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BST.initNums();
			}
		});
		btnRandomize.setBounds(10, 55, 115, 35);
		frame.getContentPane().add(btnRandomize);
		
		txtN = new JTextField();
		txtN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BST.main(null, frame.getBounds().width, frame.getBounds().y, Integer.parseInt(txtN.getText()));
			}
		});
		txtN.setBounds(10, 126, 86, 20);
		frame.getContentPane().add(txtN);
		txtN.setColumns(10);
		
		JLabel lblN = new JLabel("n = ");
		lblN.setBounds(10, 101, 46, 14);
		frame.getContentPane().add(lblN);
		
	}
}
