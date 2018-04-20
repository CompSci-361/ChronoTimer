package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JRadioButton;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import core.Chronotimer;
import core.Printer;
import core.Printer.PrintMessageActionListener;
import core.Printer.PrintMessageActionListenerEventArgs;
import core.RaceType;
import core.Racer;
import core.Run;
import core.Run.RunQueueUpdatedEventArgs;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Icon;

public class Gui extends JPanel implements ActionListener{
	
	static Chronotimer chrono = new Chronotimer();
	private ChronoTimerRunQueueUpdateListener runQueueListener;

	BufferedImage test = null;
	public String racerNumber = "";
	private RaceType selectedRaceType=RaceType.IND;
	private final Timer timer = new Timer(40, this);
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_2;
	private enum state{
		GETTIME,
		GETBIB,
		GETCLEARBIB
	}
	private state ourState = state.GETBIB;
	private JTextField txtRacer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
					
					GuiSensors window2 = new GuiSensors();
					window2.getFrame().setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    this.repaint();
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel chronoLabel = new JLabel("ChronoTimer 1009");
		chronoLabel.setForeground(new Color(128, 0, 0));
		chronoLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		chronoLabel.setBounds(278, 11, 151, 16);
		frame.getContentPane().add(chronoLabel);
		
		
		// ------------ Power button ------------
		JButton buttonPower = new JButton("Power");
		buttonPower.setForeground(new Color(0, 0, 0));
		buttonPower.setBounds(6, 7, 101, 29);
		frame.getContentPane().add(buttonPower);
		buttonPower.setContentAreaFilled(true);
		buttonPower.setOpaque(false);
		buttonPower.setBackground(Color.BLACK);
		buttonPower.setForeground(Color.BLACK);
		
		buttonPower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chrono.togglePower();
				boolean value = chrono.getIsPoweredOn();
				if(value)
					buttonPower.setForeground(new Color(0, 255, 0));
				else{
					buttonPower.setForeground(Color.BLACK);
				}	    
				chrono.reset();
			}
		});
		
		// ------------ Power button ------------

		
		JButton buttonPrintRun = new JButton("Print Run");
		buttonPrintRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.print();
				//chrono.printerPower
			}
		});
		buttonPrintRun.setBounds(562, 7, 117, 29);
		frame.getContentPane().add(buttonPrintRun);
		//btnPrinterPower.setEnabled(false);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setSize(229, 193);
		scroll.setLocation(504, 40);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scroll);
		
		JTextArea printerText = new JTextArea();
		printerText.setBounds(0, 40, 10, 10);
		scroll.setViewportView(printerText);
		
		
		JScrollPane scroll2 = new JScrollPane();
		scroll2.setSize(277, 193);
		scroll2.setLocation(215, 238);
	    scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scroll2);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBackground(new Color(211, 211, 211));
		textArea_1.setSize(277, 206);
		textArea_1.setEditable(false);
		scroll2.setViewportView(textArea_1);
		
		JButton buttonSwap = new JButton("Swap");
		buttonSwap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.swap();
			}
		});
		buttonSwap.setBounds(6, 266, 101, 29);
		frame.getContentPane().add(buttonSwap);
		//buttonSwap.setEnabled(false);
		
		JButton buttonFunction = new JButton("End Run");
		buttonFunction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chrono.getCurrentRun() != null) {
					chrono.getCurrentRun().removeQueueUpdateEventListener(runQueueListener);
				}
				chrono.endRun();
			}
		});
		buttonFunction.setBackground(Color.PINK);
		buttonFunction.setBounds(107, 135, 101, 29);
		frame.getContentPane().add(buttonFunction);
		//buttonFunction.setEnabled(false);
		
		JLabel lblNewLabel_2 = new JLabel("Queue / Running / Finish");
		lblNewLabel_2.setForeground(new Color(128, 0, 0));
		lblNewLabel_2.setBounds(272, 456, 164, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("00:00:00.0");
		textField.setToolTipText("System Time");
		textField.setBounds(9, 37, 95, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(9, 327, 95, 26);
		frame.getContentPane().add(textField_2);
		
		// ------------ Number keys ------------
		
		JPanel panel = new JPanel();
		panel.setBounds(547, 275, 132, 227);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton buttonNum1 = new JButton("1");
		buttonNum1.setBounds(0, 0, 45, 45);
		panel.add(buttonNum1);
		buttonNum1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 1; 
				if(ourState == state.GETCLEARBIB) {
					textField_2.setText(racerNumber);
				}
				if(ourState == state.GETTIME) {
					textField.setText(racerNumber);
				}
			}
		});
		
		JButton buttonNum2 = new JButton("2");
		buttonNum2.setBounds(44, 0, 45, 45);
		panel.add(buttonNum2);
		
		buttonNum2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 2; 
				if(ourState == state.GETCLEARBIB) {
					textField_2.setText(racerNumber);
				}
				if(ourState == state.GETTIME) {
					textField.setText(racerNumber);
				}
			}
		});
		
		JButton buttonNum3 = new JButton("3");
		buttonNum3.setBounds(88, 0, 45, 45);
		panel.add(buttonNum3);
		
		buttonNum3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 3;   
				if(ourState == state.GETCLEARBIB) {
					textField_2.setText(racerNumber);
				}
				if(ourState == state.GETTIME) {
					textField.setText(racerNumber);
				}
			}
		});
		
		JButton buttonNum4 = new JButton("4");
		buttonNum4.setBounds(0, 45, 45, 45);
		panel.add(buttonNum4);
		
		buttonNum4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 4;    
				if(ourState == state.GETCLEARBIB) {
					textField_2.setText(racerNumber);
				}
				if(ourState == state.GETTIME) {
					textField.setText(racerNumber);
				}
			}
		});
		
		JButton buttonNum5 = new JButton("5");
		buttonNum5.setBounds(44, 45, 45, 45);
		panel.add(buttonNum5);
		
		buttonNum5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 5; 
				if(ourState == state.GETCLEARBIB) {
					textField_2.setText(racerNumber);
				}
				if(ourState == state.GETTIME) {
					textField.setText(racerNumber);
				}
			}
		});
		
		JButton buttonNum6 = new JButton("6");
		buttonNum6.setBounds(88, 45, 45, 45);
		panel.add(buttonNum6);
		
		buttonNum6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 6; 
				if(ourState == state.GETCLEARBIB) {
					textField_2.setText(racerNumber);
				}
				if(ourState == state.GETTIME) {
					textField.setText(racerNumber);
				}
			}
		});
		
		JButton buttonNum7 = new JButton("7");
		buttonNum7.setBounds(0, 90, 45, 45);
		panel.add(buttonNum7);
		
		buttonNum7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 7;
				if(ourState == state.GETCLEARBIB) {
					textField_2.setText(racerNumber);
				}
				if(ourState == state.GETTIME) {
					textField.setText(racerNumber);
				}
			}
		});
		
		JButton buttonNum8 = new JButton("8");
		buttonNum8.setBounds(44, 90, 45, 45);
		panel.add(buttonNum8);
		
		buttonNum8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 8;
				if(ourState == state.GETCLEARBIB) {
					textField_2.setText(racerNumber);
				}
				if(ourState == state.GETTIME) {
					textField.setText(racerNumber);
				}
			}
		});
		
		JButton buttonNum9 = new JButton("9");
		buttonNum9.setBounds(88, 90, 45, 45);
		panel.add(buttonNum9);
		
		buttonNum9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 9;
				if(ourState == state.GETCLEARBIB) {
					textField_2.setText(racerNumber);
				}
				if(ourState == state.GETTIME) {
					textField.setText(racerNumber);
				}
			}
		});
		
		JButton buttonNum0 = new JButton("0");
		buttonNum0.setBounds(44, 135, 45, 45);
		panel.add(buttonNum0);
		
		buttonNum0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 0; 
				if(ourState == state.GETCLEARBIB) {
					textField_2.setText(racerNumber);
				}
				if(ourState == state.GETTIME) {
					textField.setText(racerNumber);
				}
			}
		});
		
		JButton buttonColon = new JButton(":");
		buttonColon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ourState == state.GETCLEARBIB) {
					textField_2.setText(racerNumber);
				}
				if(ourState == state.GETTIME) {
					racerNumber += ":";
					textField.setText(racerNumber);
				}
			}
		});
		buttonColon.setBounds(20, 180, 45, 45);
		panel.add(buttonColon);
		
		JButton buttonPlus = new JButton("+");
		buttonPlus.setBounds(88, 135, 45, 45);
		panel.add(buttonPlus);		
		
		JButton buttonMinus = new JButton("-");
		buttonMinus.setBounds(0, 135, 45, 45);
		panel.add(buttonMinus);
		
		JButton buttonPeriod = new JButton(".");
		buttonPeriod.setBounds(69, 180, 45, 45);
		panel.add(buttonPeriod);
		
		//buttonPound.setEnabled(false);
		
		// ------------ Number keys ------------
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		panel_1.setBounds(215, 40, 277, 193);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Start");
		lblNewLabel_1.setBounds(67, 17, 26, 16);
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		JLabel lblEnabledisable = new JLabel("Enable/Disable");
		lblEnabledisable.setBounds(20, 61, 73, 16);
		panel_1.add(lblEnabledisable);
		lblEnabledisable.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		JLabel lblFinish = new JLabel("Finish");
		lblFinish.setBounds(64, 116, 29, 16);
		panel_1.add(lblFinish);
		lblFinish.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
//1		
		JButton trig1 = new JButton("1");
		trig1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		trig1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.triggerChannel(1);
			}
		});
		trig1.setBounds(99, 11, 26, 29);
		panel_1.add(trig1);
		
		JRadioButton tog1 = new JRadioButton("");
		tog1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.toggleChannel(1);
			}
		});
		tog1.setBounds(99, 54, 26, 23);
		panel_1.add(tog1);
		
//2		
		JButton trig2 = new JButton("2");
		trig2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.triggerChannel(2);
			}
		});
		trig2.setBounds(99, 110, 26, 29);
		panel_1.add(trig2);
		
		JRadioButton tog2 = new JRadioButton("");
		tog2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.toggleChannel(2);
			}
		});
		tog2.setBounds(99, 151, 26, 23);
		panel_1.add(tog2);
		
//3		
		JButton trig3 = new JButton("3");
		trig3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.triggerChannel(3);
			}
		});
		trig3.setBounds(137, 11, 26, 29);
		panel_1.add(trig3);
		
		JRadioButton tog3 = new JRadioButton("");
		tog3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.toggleChannel(3);
			}
		});
		tog3.setBounds(137, 54, 26, 23);
		panel_1.add(tog3);
		
//4		
		JButton trig4 = new JButton("4");
		trig4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.triggerChannel(4);
			}
		});
		trig4.setBounds(137, 110, 26, 29);
		panel_1.add(trig4);
		
		JRadioButton tog4 = new JRadioButton("");
		tog4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.toggleChannel(4);
			}
		});
		tog4.setBounds(137, 151, 26, 23);
		panel_1.add(tog4);
		
//5		
		JButton trig5 = new JButton("5");
		trig5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.triggerChannel(5);
			}
		});
		trig5.setBounds(175, 11, 26, 29);
		panel_1.add(trig5);
		
		JRadioButton tog5 = new JRadioButton("");
		tog5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.toggleChannel(5);
			}
		});
		tog5.setBounds(175, 54, 26, 23);
		panel_1.add(tog5);
		
//6		
		JButton trig6 = new JButton("6");
		trig6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.triggerChannel(6);
			}
		});
		trig6.setBounds(175, 110, 26, 29);
		panel_1.add(trig6);
		
		JRadioButton tog6 = new JRadioButton("");
		tog6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.toggleChannel(6);
			}
		});
		tog6.setBounds(175, 151, 26, 23);
		panel_1.add(tog6);
		
		
//7		
		JButton trig7 = new JButton("7");
		trig7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.triggerChannel(7);
			}
		});
		trig7.setBounds(213, 11, 26, 29);
		panel_1.add(trig7);
		
		JRadioButton tog7 = new JRadioButton("");
		tog7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.toggleChannel(7);
			}
		});
		tog7.setBounds(213, 54, 26, 23);
		panel_1.add(tog7);
		
//8		
		JButton trig8 = new JButton("8");
		trig8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.triggerChannel(8);
			}
		});
		trig8.setBounds(213, 110, 26, 29);
		panel_1.add(trig8);
		
		JRadioButton tog8 = new JRadioButton("");
		tog8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.toggleChannel(8);
			}
		});
		tog8.setBounds(213, 151, 26, 23);
		panel_1.add(tog8);
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(0, 89, 285, 5);
		panel_1.add(panel_2);
		
		JLabel label = new JLabel("Enable/Disable");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		label.setBounds(20, 153, 73, 16);
		panel_1.add(label);
		
//Runs and types		
		JButton btnNewRun = new JButton("New Run");
		btnNewRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.newRun(selectedRaceType);
				if (chrono.getCurrentRun() != null) {
					chrono.getCurrentRun().addQueueUpdateEventListener(runQueueListener);
				}
			}
		});
		btnNewRun.setBounds(107, 108, 101, 29);
		frame.getContentPane().add(btnNewRun);
		
		JButton btnIndRun = new JButton("Ind Run");
		btnIndRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedRaceType = RaceType.IND;
			}
		});
		btnIndRun.setBounds(6, 108, 101, 29);
		frame.getContentPane().add(btnIndRun);
		
		JButton btnParindRun = new JButton("ParInd Run");
		btnParindRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedRaceType = RaceType.PARIND;
			}
		});
		btnParindRun.setBounds(6, 135, 101, 29);
		frame.getContentPane().add(btnParindRun);
		
		JButton btnNewButton = new JButton("Grp Run");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedRaceType = RaceType.GRP;
			}
		});
		btnNewButton.setBounds(6, 162, 101, 29);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnPargrpRun = new JButton("ParGrp Run");
		btnPargrpRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedRaceType = RaceType.PARGRP;
			}
		});
		btnPargrpRun.setBounds(6, 190, 101, 29);
		frame.getContentPane().add(btnPargrpRun);
		
		JLabel lblRaceType = new JLabel("Race Type");
		lblRaceType.setForeground(new Color(128, 0, 0));
		lblRaceType.setBounds(26, 80, 69, 16);
		frame.getContentPane().add(lblRaceType);
		
		
		
		
		
		//gets time from interface in form hours:min:secs
		JButton btnTime = new JButton("Time");
		btnTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ourState == state.GETTIME) {
					//set time to racerNumber
					String times = racerNumber;
					String delims = "\\:";
					String[] tokens = times.split(delims);
					int hours = Integer.parseInt(tokens[0]);
					int minutes = Integer.parseInt(tokens[1]);
					double seconds = Double.parseDouble(tokens[2]);
					chrono.setTime(hours, minutes, seconds);
					ourState = state.GETBIB;
					racerNumber = "";
					textField.setText("00:00:00.0");
				}else if(ourState == state.GETBIB){
					ourState = state.GETTIME;
					racerNumber = "";
				}
				
			}
		});
		btnTime.setBounds(107, 37, 101, 29);
		frame.getContentPane().add(btnTime);
		
		JLabel lblRun = new JLabel("Run");
		lblRun.setForeground(new Color(128, 0, 0));
		lblRun.setBounds(140, 80, 32, 16);
		frame.getContentPane().add(lblRun);
		
		JLabel lblFunctions = new JLabel("Functions");
		lblFunctions.setForeground(new Color(128, 0, 0));
		lblFunctions.setBounds(74, 238, 69, 16);
		frame.getContentPane().add(lblFunctions);
		
		
		JButton btnDnf = new JButton("DNF");
		btnDnf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.dnf();
			}
		});
		btnDnf.setBounds(107, 266, 101, 29);
		frame.getContentPane().add(btnDnf);
		
		
		
		
		
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chrono!=null) {
					chrono.cancel();
				}
				//}
				//else {
				//	chrono.cancel(Integer.parseInt(textField_1.getText()));
			}
		});
		btnCancel.setBounds(107, 295, 101, 29);
		frame.getContentPane().add(btnCancel);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ourState == state.GETCLEARBIB) {
					String racer = racerNumber;
					//System.out.println("Clearing :"+racer+":");
					if(racer.length()>0) {
						//System.out.println("Racer = A"+racer+"A");
						chrono.clear(Integer.parseInt(racer));
					}
					racerNumber = "";
					ourState = state.GETBIB;
					textField_2.setText("");
				}else if(ourState == state.GETBIB){
					ourState = state.GETCLEARBIB;
					racerNumber = "";
				}
			}
		});
		btnClear.setBounds(107, 327, 101, 29);
		frame.getContentPane().add(btnClear);
		
		// ------------ Reset button ------------
		
		JButton buttonReset = new JButton("Reset");
		buttonReset.setBounds(107, 7, 101, 29);
		frame.getContentPane().add(buttonReset);
		buttonReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					chrono.reset();
				System.out.println("Reset Chronotimer."); 
				tog1.setSelected(false);
				tog2.setSelected(false);
				tog3.setSelected(false);
				tog4.setSelected(false);
				tog5.setSelected(false);
				tog6.setSelected(false);
				tog7.setSelected(false);
				tog8.setSelected(false);
				textField.setText("00:00:00.0");
				textField_2.setText("");
				racerNumber = "";
			}
		});	
		buttonPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(racerNumber);
				if(ourState == state.GETBIB) {
					if(racerNumber != "") {
						chrono.addRacer(Integer.parseInt(racerNumber));
					}
					racerNumber = "";
				} else if(ourState == state.GETCLEARBIB) {
					btnClear.doClick();
				} else if (ourState == state.GETTIME) {
					btnTime.doClick();
				}
			}
		});
		
		// ------------ Reset button ------------
		
				
		ImageIcon icon = new ImageIcon("juggernotlogo.png");
		JLabel example = new JLabel();
		example.setIcon(icon);
		example.setBounds(55,365, 100, 100);
		frame.getContentPane().add(example);
		
		Printer.addPrintMessageActionListener(new PrintMessageActionListener(){

			@Override
			public void onPrintMessageReceived(PrintMessageActionListenerEventArgs args) {
				printerText.append(args.getMessage() + "\r\n");			
			}
			
		});
		
		runQueueListener = new ChronoTimerRunQueueUpdateListener(textArea_1);
		
		txtRacer = new JTextField();
		txtRacer.setText("Racer ###");
		txtRacer.setBounds(577, 247, 79, 26);
		frame.getContentPane().add(txtRacer);
		txtRacer.setColumns(10);
	}
	
	private void updateQueueDisplay(JTextArea textArea) {
		StringBuilder builder = new StringBuilder();
		
		if (runQueueListener.waitQueue != null) {
			builder.append("Wait Queue:\r\n");
			
			if (runQueueListener.waitQueue.length > 0) {
				for(Racer racer : runQueueListener.waitQueue) {
					String msg = MessageFormat.format("Racer {0}\r\n", racer.getBibNumber());
					builder.append(msg);
				}
			} else {
				builder.append("No one is waiting yet.\r\n");
			}
			builder.append("\r\n");
		}
		
		if (runQueueListener.runningQueue != null) {
			builder.append("Racers running:\r\n");
			
			if (runQueueListener.runningQueue.length > 0) {
				for(Racer racer : runQueueListener.runningQueue) {
					String msg = MessageFormat.format("Racer {0} | Start Time: {1}\r\n", 
							racer.getBibNumber(), Chronotimer.ourTimer.formatTime(racer.getStartTime()));
					builder.append(msg);
				}
			} else {
				builder.append("No one is running yet.\r\n");
			}
			builder.append("\r\n");
		}
		
		if (runQueueListener.finishQueue != null) {
			builder.append("Finish Queue:\r\n");
			
			if (runQueueListener.finishQueue.length > 0) {
				for(Racer racer : runQueueListener.finishQueue) {
					String msg = MessageFormat.format("Racer {0} | Start Time: {1} | Finish Time: {2}\r\n", 
							racer.getBibNumber(), Chronotimer.ourTimer.formatTime(racer.getStartTime()), Chronotimer.ourTimer.formatTime(racer.getEndTime()));
					builder.append(msg);
				}
			} else {
				builder.append("No one has finished yet.\r\n");
			}
			builder.append("\r\n");
		}
		
		textArea.setText(builder.toString());
	}
	
	class ChronoTimerRunQueueUpdateListener implements Run.IRunQueueUpdatedListener {
		public Racer[] waitQueue;
		public Racer[] runningQueue;
		public Racer[] finishQueue;
		public JTextArea textArea;
		
		public ChronoTimerRunQueueUpdateListener(JTextArea textArea) {
			this.textArea = textArea;
		}
		
		@Override
		public void queueUpdated(RunQueueUpdatedEventArgs args) {
			finishQueue = args.getSource().getFinishedRacers();
			runningQueue = args.getSource().getCurrentRunningRacers();
			waitQueue = args.getSource().getCurrentWaitingRacers();
			
			Gui.this.updateQueueDisplay(textArea);
		}
	}
	
	public void paint(Graphics g) {
	    super.paint(g);
	    g.drawImage(test, 200, 200, null);
	}
}
