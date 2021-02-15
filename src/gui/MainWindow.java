package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow {

	private JFrame frmControls;
	private JTextField txtN;
	private JTextField txtSpeed;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmControls.setVisible(true);
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
		frmControls = new JFrame();
		frmControls.setTitle("Controls");
		frmControls.setResizable(false);
		frmControls.setBounds(10, 200, 271, 232);
		frmControls.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmControls.getContentPane().setLayout(null);
		
		JButton btnStartSort = new JButton("Start Sort");
		btnStartSort.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnStartSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = -1;
				try {
					n = Integer.parseInt(txtN.getText());

				} catch(NumberFormatException e1) {}
				
				if(n > 0) {
					ArrayVisualizer.changeN(n);
				}
				ArrayVisualizer.startSort();
			}
		});
		btnStartSort.setBounds(10, 70, 115, 35);
		frmControls.getContentPane().add(btnStartSort);
		
		JButton btnRandomize = new JButton("Randomise");
		btnRandomize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayVisualizer.changeMode(0);
				ArrayVisualizer.initNums();
			}
		});
		btnRandomize.setBounds(129, 70, 115, 35);
		frmControls.getContentPane().add(btnRandomize);

		JLabel lblNewLabel = new JLabel("speed = ");
		lblNewLabel.setBounds(122, 119, 58, 14);
		frmControls.getContentPane().add(lblNewLabel);
		
		txtSpeed = new JTextField("50");
		txtSpeed.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int s = -1;
				try {
					s = Integer.parseInt(txtSpeed.getText());

				} catch(NumberFormatException e1) {}
				
				if(s > 0) {
					ArrayVisualizer.changeSpeed(s);
				}
			}
		});
		txtSpeed.setColumns(10);
		txtSpeed.setBounds(179, 116, 65, 20);
		frmControls.getContentPane().add(txtSpeed);
		
		txtN = new JTextField("40");
		txtN.setBounds(46, 116, 58, 20);
		frmControls.getContentPane().add(txtN);
		txtN.setColumns(10);
		
		JLabel lblN = new JLabel("n = ");
		lblN.setBounds(10, 116, 46, 14);
		frmControls.getContentPane().add(lblN);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Random values");
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayVisualizer.changeMode(0);
				ArrayVisualizer.initNums();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rdbtnNewRadioButton.setBounds(10, 7, 109, 23);
		frmControls.getContentPane().add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnRandomTo = new JRadioButton("Random 1 to n");
		rdbtnRandomTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayVisualizer.changeMode(2);
				ArrayVisualizer.initNums();
			}
		});
		buttonGroup.add(rdbtnRandomTo);
		rdbtnRandomTo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnRandomTo.setBounds(129, 7, 109, 23);
		frmControls.getContentPane().add(rdbtnRandomTo);
		
		JRadioButton rdbtnIdeas = new JRadioButton("Ideas?");
		rdbtnIdeas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayVisualizer.changeMode(0);
				ArrayVisualizer.initNums();
			}
		});
		buttonGroup.add(rdbtnIdeas);
		rdbtnIdeas.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnIdeas.setBounds(131, 33, 109, 23);
		frmControls.getContentPane().add(rdbtnIdeas);
		
		JRadioButton rdbtnNewRadioButton_2_1 = new JRadioButton("1 to n");
		rdbtnNewRadioButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayVisualizer.changeMode(1);
				ArrayVisualizer.initNums();
				
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_2_1);
		rdbtnNewRadioButton_2_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnNewRadioButton_2_1.setBounds(10, 33, 109, 23);
		frmControls.getContentPane().add(rdbtnNewRadioButton_2_1);

		ArrayVisualizer.main(null, frmControls.getBounds().width, frmControls.getBounds().y);

	}
}
