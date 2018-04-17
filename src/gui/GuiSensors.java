package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import core.GrpRun;
import core.IndRun;
import core.ParGrpRun;
import core.ParIndRun;
import core.Printer;
import core.SensorType;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		
		
		JRadioButton radioButton1 = new JRadioButton("");
		radioButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radioButton1.isSelected()) {
					switch((String)comboBox.getSelectedItem()) {
					case "EYE":
						Gui.chrono.setConnect(1, SensorType.EYE);
						break;
					case "GATE":
						Gui.chrono.setConnect(1, SensorType.GATE);
						break;
					case "TRIP":
						Gui.chrono.setConnect(1, SensorType.TRIP);
						break;
					case "NONE":
						Gui.chrono.setConnect(1, SensorType.NONE);
						break;
					case "PAD":
						Gui.chrono.setConnect(1, SensorType.PAD);
						break;
					}
				}else {
					Gui.chrono.setDisconnect(1);
				}
			}
		});
		radioButton1.setBounds(27, 89, 28, 23);
		getFrame().getContentPane().add(radioButton1);
		
		JRadioButton radioButton3 = new JRadioButton("");
		radioButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radioButton3.isSelected()) {
					switch((String)comboBox.getSelectedItem()) {
					case "EYE":
						Gui.chrono.setConnect(3, SensorType.EYE);
						break;
					case "GATE":
						Gui.chrono.setConnect(3, SensorType.GATE);
						break;
					case "TRIP":
						Gui.chrono.setConnect(3, SensorType.TRIP);
						break;
					case "NONE":
						Gui.chrono.setConnect(3, SensorType.NONE);
						break;
					case "PAD":
						Gui.chrono.setConnect(3, SensorType.PAD);
						break;
					}
				}else {
					Gui.chrono.setDisconnect(3);
				}
			}
		});
		radioButton3.setBounds(89, 89, 28, 23);
		getFrame().getContentPane().add(radioButton3);
		
		
		JRadioButton radioButton4 = new JRadioButton("");
		radioButton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radioButton4.isSelected()) {
					switch((String)comboBox.getSelectedItem()) {
					case "EYE":
						Gui.chrono.setConnect(4, SensorType.EYE);
						break;
					case "GATE":
						Gui.chrono.setConnect(4, SensorType.GATE);
						break;
					case "TRIP":
						Gui.chrono.setConnect(4, SensorType.TRIP);
						break;
					case "NONE":
						Gui.chrono.setConnect(4, SensorType.NONE);
						break;
					case "PAD":
						Gui.chrono.setConnect(4, SensorType.PAD);
						break;
					}
				}else {
					Gui.chrono.setDisconnect(4);
				}
			}
		});
		radioButton4.setBounds(89, 173, 28, 23);
		getFrame().getContentPane().add(radioButton4);
		
		
		JRadioButton radioButton5 = new JRadioButton("");
		radioButton5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radioButton5.isSelected()) {	
					switch((String)comboBox.getSelectedItem()) {
					case "EYE":
						Gui.chrono.setConnect(5, SensorType.EYE);
						break;
					case "GATE":
						Gui.chrono.setConnect(5, SensorType.GATE);
						break;
					case "TRIP":
						Gui.chrono.setConnect(5, SensorType.TRIP);
						break;
					case "NONE":
						Gui.chrono.setConnect(5, SensorType.NONE);
						break;
					case "PAD":
						Gui.chrono.setConnect(5, SensorType.PAD);
						break;
					}
				}else {
					Gui.chrono.setDisconnect(5);
				}
			}
		});
		radioButton5.setBounds(151, 89, 28, 23);
		getFrame().getContentPane().add(radioButton5);
		
		
		JRadioButton radioButton2 = new JRadioButton("");
		radioButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radioButton2.isSelected()) {
					switch((String)comboBox.getSelectedItem()) {
					case "EYE":
						Gui.chrono.setConnect(2, SensorType.EYE);
						break;
					case "GATE":
						Gui.chrono.setConnect(2, SensorType.GATE);
						break;
					case "TRIP":
						Gui.chrono.setConnect(2, SensorType.TRIP);
						break;
					case "NONE":
						Gui.chrono.setConnect(2, SensorType.NONE);
						break;
					case "PAD":
						Gui.chrono.setConnect(2, SensorType.PAD);
						break;
					}
				}else {
					Gui.chrono.setDisconnect(2);
				}
			}
		});
		radioButton2.setBounds(27, 173, 28, 23);
		getFrame().getContentPane().add(radioButton2);
		
		
		JRadioButton radioButton7 = new JRadioButton("");
		radioButton7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radioButton7.isSelected()) {
					switch((String)comboBox.getSelectedItem()) {
					case "EYE":
						Gui.chrono.setConnect(7, SensorType.EYE);
						break;
					case "GATE":
						Gui.chrono.setConnect(7, SensorType.GATE);
						break;
					case "TRIP":
						Gui.chrono.setConnect(7, SensorType.TRIP);
						break;
					case "NONE":
						Gui.chrono.setConnect(7, SensorType.NONE);
						break;
					case "PAD":
						Gui.chrono.setConnect(7, SensorType.PAD);
						break;
					}
				}else {
					Gui.chrono.setDisconnect(7);
				}
			}
		});
		radioButton7.setBounds(213, 89, 28, 23);
		getFrame().getContentPane().add(radioButton7);
		
		
		JRadioButton radioButton6 = new JRadioButton("");
		radioButton6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radioButton6.isSelected()) {
					switch((String)comboBox.getSelectedItem()) {
					case "EYE":
						Gui.chrono.setConnect(6, SensorType.EYE);
						break;
					case "GATE":
						Gui.chrono.setConnect(6, SensorType.GATE);
						break;
					case "TRIP":
						Gui.chrono.setConnect(6, SensorType.TRIP);
						break;
					case "NONE":
						Gui.chrono.setConnect(6, SensorType.NONE);
						break;
					case "PAD":
						Gui.chrono.setConnect(6, SensorType.PAD);
						break;
					}
				}else {
					Gui.chrono.setDisconnect(6);
				}
			}
		});
		radioButton6.setBounds(150, 173, 28, 23);
		getFrame().getContentPane().add(radioButton6);
		
		
		JRadioButton radioButton8 = new JRadioButton("");
		radioButton8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radioButton8.isSelected()) {
					switch((String)comboBox.getSelectedItem()) {
					case "EYE":
						Gui.chrono.setConnect(8, SensorType.EYE);
						break;
					case "GATE":
						Gui.chrono.setConnect(8, SensorType.GATE);
						break;
					case "TRIP":
						Gui.chrono.setConnect(8, SensorType.TRIP);
						break;
					case "NONE":
						Gui.chrono.setConnect(8, SensorType.NONE);
						break;
					case "PAD":
						Gui.chrono.setConnect(8, SensorType.PAD);
						break;
					}
				}else {
					Gui.chrono.setDisconnect(8);
				}
			}
		});
		radioButton8.setBounds(217, 173, 28, 23);
		getFrame().getContentPane().add(radioButton8);
		
		
		JLabel lblChannelSensors = new JLabel("Channel Sensors");
		lblChannelSensors.setForeground(new Color(128, 0, 0));
		lblChannelSensors.setBounds(30, 26, 131, 16);
		getFrame().getContentPane().add(lblChannelSensors);
		
		JButton btnExportToUsb = new JButton("Export to USB");
		btnExportToUsb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int max = Gui.chrono.getRunNumber();
				for(int i = 0; i<max;++i) {
					Gui.chrono.exportRun(i);
				}
			}
		});
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
