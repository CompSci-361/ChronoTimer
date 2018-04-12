package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;

public class GuiSensors {

	private JFrame frame;
	String [] sensorType = {"NONE", "GATE", "EYE", "TRIP", "PAD"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiSensors window = new GuiSensors();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GuiSensors() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrame(new JFrame());
		getFrame().setBounds(900, 100, 432, 245);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().getContentPane().setLayout(null);
		
		JComboBox comboBox = new JComboBox(sensorType);
		comboBox.setBounds(139, 22, 106, 27);
		getFrame().getContentPane().add(comboBox);
		
		JRadioButton radioButton = new JRadioButton("");
		radioButton.setBounds(27, 89, 28, 23);
		getFrame().getContentPane().add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("");
		radioButton_1.setBounds(89, 89, 28, 23);
		getFrame().getContentPane().add(radioButton_1);
		
		JRadioButton radioButton_2 = new JRadioButton("");
		radioButton_2.setBounds(89, 173, 28, 23);
		getFrame().getContentPane().add(radioButton_2);
		
		JRadioButton radioButton_3 = new JRadioButton("");
		radioButton_3.setBounds(151, 89, 28, 23);
		getFrame().getContentPane().add(radioButton_3);
		
		JRadioButton radioButton_4 = new JRadioButton("");
		radioButton_4.setBounds(27, 173, 28, 23);
		getFrame().getContentPane().add(radioButton_4);
		
		JRadioButton radioButton_5 = new JRadioButton("");
		radioButton_5.setBounds(213, 89, 28, 23);
		getFrame().getContentPane().add(radioButton_5);
		
		JRadioButton radioButton_6 = new JRadioButton("");
		radioButton_6.setBounds(150, 173, 28, 23);
		getFrame().getContentPane().add(radioButton_6);
		
		JRadioButton radioButton_7 = new JRadioButton("");
		radioButton_7.setBounds(217, 173, 28, 23);
		getFrame().getContentPane().add(radioButton_7);
		
		JLabel lblChannelSensors = new JLabel("Channel Sensors");
		lblChannelSensors.setForeground(new Color(128, 0, 0));
		lblChannelSensors.setBounds(30, 26, 131, 16);
		getFrame().getContentPane().add(lblChannelSensors);
		
		JButton btnExportToUsb = new JButton("Export to USB");
		btnExportToUsb.setBounds(296, 21, 117, 29);
		getFrame().getContentPane().add(btnExportToUsb);
		
		JLabel label = new JLabel("1");
		label.setBounds(35, 54, 19, 27);
		getFrame().getContentPane().add(label);
		
		JLabel label_1 = new JLabel("3");
		label_1.setBounds(98, 54, 19, 27);
		getFrame().getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("5");
		label_2.setBounds(159, 54, 19, 27);
		getFrame().getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("7");
		label_3.setBounds(221, 54, 19, 27);
		getFrame().getContentPane().add(label_3);
		
		JLabel label_4 = new JLabel("2");
		label_4.setBounds(35, 137, 19, 27);
		getFrame().getContentPane().add(label_4);
		
		JLabel label_5 = new JLabel("4");
		label_5.setBounds(98, 134, 19, 27);
		getFrame().getContentPane().add(label_5);
		
		JLabel label_6 = new JLabel("6");
		label_6.setBounds(159, 134, 19, 27);
		getFrame().getContentPane().add(label_6);
		
		JLabel label_7 = new JLabel("8");
		label_7.setBounds(221, 134, 19, 27);
		getFrame().getContentPane().add(label_7);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
