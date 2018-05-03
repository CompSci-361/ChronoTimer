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
import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Icon;

public class Gui extends JPanel implements ActionListener{
	
	static Chronotimer chrono = new Chronotimer();
	private ChronoTimerRunQueueUpdateListener runQueueListener;
	private Timer runQueueTimer;

	BufferedImage test = null;
	public String stdIn = "";
	private RaceType selectedRaceType=RaceType.IND;
	private final Timer timer = new Timer(40, this);
	private JFrame frame;
	private JTextField textField;
	private JTextField textClear;
	private enum state{
		GETTIME,
		GETBIB,
		GETCLEARBIB
	}
	private state ourState = state.GETBIB;
	public static GuiSensors window2 = new GuiSensors();
	private JTextField textRacer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
					
					window2 = new GuiSensors();
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

		// ------------ Print button and print text box ------------
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
		
		// ------------ Print button ------------

		// ------------ Text Area for Runners ------------
		
		JScrollPane scrollRunnerText = new JScrollPane();
		scrollRunnerText.setSize(277, 193);
		scrollRunnerText.setLocation(215, 238);
	    scrollRunnerText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scrollRunnerText);
		
		JTextArea runnerText = new JTextArea();
		runnerText.setBackground(new Color(211, 211, 211));
		runnerText.setSize(277, 206);
		runnerText.setEditable(false);
		scrollRunnerText.setViewportView(runnerText);
		
		// ------------ Text Area for Runners ------------

		
		// ------------ Swap Button ------------
		
		JButton buttonSwap = new JButton("Swap");
		buttonSwap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.swap();
			}
		});
		buttonSwap.setBounds(6, 266, 101, 29);
		frame.getContentPane().add(buttonSwap);
		//buttonSwap.setEnabled(false);
		
		// ------------ Swap Button ------------

		
		JButton btnEndRun = new JButton("End Run");
		btnEndRun.setBackground(Color.PINK);
		btnEndRun.setBounds(107, 135, 101, 29);
		frame.getContentPane().add(btnEndRun);
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
		
		textClear = new JTextField();
		textClear.setColumns(10);
		textClear.setBounds(9, 327, 95, 26);
		frame.getContentPane().add(textClear);
		
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
				stdIn += 1; 
				if(ourState == state.GETBIB){
					textRacer.setText(stdIn);
				}
				if(ourState == state.GETCLEARBIB) {
					textClear.setText(stdIn);
				}
				if(ourState == state.GETTIME) {
					textField.setText(stdIn);
				}
			}
		});
		
		JButton buttonNum2 = new JButton("2");
		buttonNum2.setBounds(44, 0, 45, 45);
		panel.add(buttonNum2);
		
		buttonNum2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stdIn += 2; 
				if(ourState == state.GETBIB){
					textRacer.setText(stdIn);
				}
				if(ourState == state.GETCLEARBIB) {
					textClear.setText(stdIn);
				}
				if(ourState == state.GETTIME) {
					textField.setText(stdIn);
				}
			}
		});
		
		JButton buttonNum3 = new JButton("3");
		buttonNum3.setBounds(88, 0, 45, 45);
		panel.add(buttonNum3);
		
		buttonNum3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stdIn += 3;  
				if(ourState == state.GETBIB){
					textRacer.setText(stdIn);
				}
				if(ourState == state.GETCLEARBIB) {
					textClear.setText(stdIn);
				}
				if(ourState == state.GETTIME) {
					textField.setText(stdIn);
				}
			}
		});
		
		JButton buttonNum4 = new JButton("4");
		buttonNum4.setBounds(0, 45, 45, 45);
		panel.add(buttonNum4);
		
		buttonNum4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stdIn += 4;  
				if(ourState == state.GETBIB){
					textRacer.setText(stdIn);
				}
				if(ourState == state.GETCLEARBIB) {
					textClear.setText(stdIn);
				}
				if(ourState == state.GETTIME) {
					textField.setText(stdIn);
				}
			}
		});
		
		JButton buttonNum5 = new JButton("5");
		buttonNum5.setBounds(44, 45, 45, 45);
		panel.add(buttonNum5);
		
		buttonNum5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stdIn += 5; 
				if(ourState == state.GETBIB){
					textRacer.setText(stdIn);
				}
				if(ourState == state.GETCLEARBIB) {
					textClear.setText(stdIn);
				}
				if(ourState == state.GETTIME) {
					textField.setText(stdIn);
				}
			}
		});
		
		JButton buttonNum6 = new JButton("6");
		buttonNum6.setBounds(88, 45, 45, 45);
		panel.add(buttonNum6);
		
		buttonNum6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stdIn += 6; 
				if(ourState == state.GETBIB){
					textRacer.setText(stdIn);
				}
				if(ourState == state.GETCLEARBIB) {
					textClear.setText(stdIn);
				}
				if(ourState == state.GETTIME) {
					textField.setText(stdIn);
				}
			}
		});
		
		JButton buttonNum7 = new JButton("7");
		buttonNum7.setBounds(0, 90, 45, 45);
		panel.add(buttonNum7);
		
		buttonNum7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stdIn += 7;
				if(ourState == state.GETBIB){
					textRacer.setText(stdIn);
				}
				if(ourState == state.GETCLEARBIB) {
					textClear.setText(stdIn);
				}
				if(ourState == state.GETTIME) {
					textField.setText(stdIn);
				}
			}
		});
		
		JButton buttonNum8 = new JButton("8");
		buttonNum8.setBounds(44, 90, 45, 45);
		panel.add(buttonNum8);
		
		buttonNum8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stdIn += 8;
				if(ourState == state.GETBIB){
					textRacer.setText(stdIn);
				}
				if(ourState == state.GETCLEARBIB) {
					textClear.setText(stdIn);
				}
				if(ourState == state.GETTIME) {
					textField.setText(stdIn);
				}
			}
		});
		
		JButton buttonNum9 = new JButton("9");
		buttonNum9.setBounds(88, 90, 45, 45);
		panel.add(buttonNum9);
		
		buttonNum9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stdIn += 9;
				if(ourState == state.GETBIB){
					textRacer.setText(stdIn);
				}
				if(ourState == state.GETCLEARBIB) {
					textClear.setText(stdIn);
				}
				if(ourState == state.GETTIME) {
					textField.setText(stdIn);
				}
			}
		});
		
		JButton buttonNum0 = new JButton("0");
		buttonNum0.setBounds(44, 135, 45, 45);
		panel.add(buttonNum0);
		
		buttonNum0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stdIn += 0; 
				if(ourState == state.GETBIB){
					textRacer.setText(stdIn);
				}
				if(ourState == state.GETCLEARBIB) {
					textClear.setText(stdIn);
				}
				if(ourState == state.GETTIME) {
					textField.setText(stdIn);
				}
			}
		});
		
		JButton buttonColon = new JButton(":");
		buttonColon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ourState == state.GETTIME) {
					stdIn += ":";
					textField.setText(stdIn);
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
		
		buttonMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(stdIn.length() != 0)
					stdIn = stdIn.substring(0, stdIn.length()-1);
				if(ourState == state.GETBIB){
					textRacer.setText(stdIn);
				}
				if(ourState == state.GETCLEARBIB) {
					textClear.setText(stdIn);
				}
				if(ourState == state.GETTIME) {
					textField.setText(stdIn);
				}
			}
		});
		
		JButton buttonPeriod = new JButton(".");
		buttonPeriod.setBounds(69, 180, 45, 45);
		panel.add(buttonPeriod);
		
		buttonPeriod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ourState == state.GETTIME) {
					stdIn += ".";
					textField.setText(stdIn);
				}
			}
		});
		
		//buttonPound.setEnabled(false);
		
		// ------------ Number keys ------------
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		panel_1.setBounds(215, 40, 277, 193);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		
		
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
				tog1.setSelected(chrono.toggleChannel(1)&&tog1.isSelected());
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
				tog2.setSelected(chrono.toggleChannel(2)&&tog2.isSelected());
				window2.refreshChannels();
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
				tog3.setSelected(chrono.toggleChannel(3)&&tog3.isSelected());
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
				tog4.setSelected(chrono.toggleChannel(4)&&tog4.isSelected());
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
				tog5.setSelected(chrono.toggleChannel(5)&&tog5.isSelected());
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
				tog6.setSelected(chrono.toggleChannel(6)&&tog6.isSelected());
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
				tog7.setSelected(chrono.toggleChannel(7)&&tog7.isSelected());
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
				tog8.setSelected(chrono.toggleChannel(8)&&tog8.isSelected());
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
		label.setBounds(20, 154, 73, 16);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("Enable/Disable");
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		label_1.setBounds(20, 57, 73, 16);
		panel_1.add(label_1);
		
		JLabel lblStart = new JLabel("Start");
		lblStart.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblStart.setBounds(20, 15, 73, 16);
		panel_1.add(lblStart);
		
		JLabel lblFinish = new JLabel("Finish");
		lblFinish.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblFinish.setBounds(20, 115, 73, 16);
		panel_1.add(lblFinish);
		
//Runs and types		
		JButton btnNewRun = new JButton("New Run");
		btnNewRun.setBounds(107, 108, 101, 29);
		frame.getContentPane().add(btnNewRun);
		
		JButton btnIndRun = new JButton("Ind Run");
		btnIndRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedRaceType = RaceType.IND;
				btnNewRun.setEnabled(true);
				Printer.printMessage(selectedRaceType + " selected");
			}
		});
		btnIndRun.setBounds(6, 108, 101, 29);
		frame.getContentPane().add(btnIndRun);
		
		JButton btnParindRun = new JButton("ParInd Run");
		btnParindRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedRaceType = RaceType.PARIND;
				btnNewRun.setEnabled(true);
				Printer.printMessage(selectedRaceType + " selected");
			}
		});
		btnParindRun.setBounds(6, 135, 101, 29);
		frame.getContentPane().add(btnParindRun);
		
		JButton btnGrpRun = new JButton("Grp Run");
		btnGrpRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedRaceType = RaceType.GRP;
				btnNewRun.setEnabled(true);
				Printer.printMessage(selectedRaceType + " selected");
			}
		});
		btnGrpRun.setBounds(6, 162, 101, 29);
		frame.getContentPane().add(btnGrpRun);
		
		JButton btnPargrpRun = new JButton("ParGrp Run");
		btnPargrpRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedRaceType = RaceType.PARGRP;
				btnNewRun.setEnabled(true);
				Printer.printMessage(selectedRaceType + " selected");
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
				if(!chrono.getIsPoweredOn()){
					Printer.printMessage("Power must be enabled to set time");
					return;
				}
				if(ourState == state.GETTIME) {
					//set time to racerNumber
					String times = stdIn;
					String delims = "\\:";
					String[] tokens = times.split(delims);
					int hours = Integer.parseInt(tokens[0]);
					int minutes = Integer.parseInt(tokens[1]);
					double seconds = Double.parseDouble(tokens[2]);
					chrono.setTime(hours, minutes, seconds);
					ourState = state.GETBIB;
					stdIn = "";
					textField.setText("00:00:00.0");
				}else if(ourState == state.GETBIB){
					ourState = state.GETTIME;
					stdIn = "";
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
					String racer = stdIn;
					//System.out.println("Clearing :"+racer+":");
					if(racer.length()>0) {
						//System.out.println("Racer = A"+racer+"A");
						chrono.clear(Integer.parseInt(racer));
					}
					stdIn = "";
					ourState = state.GETBIB;
					textClear.setText("");
				}else if(ourState == state.GETBIB){
					ourState = state.GETCLEARBIB;
					stdIn = "";
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
				for(int i=1; i<9;++i) {
					chrono.setDisconnect(i);
				}
        
				if (runQueueTimer.isRunning())
					runQueueTimer.stop(); 

				chrono.reset();
				if(tog1.isSelected()) {
					chrono.toggleChannel(1);
				}
				if(tog2.isSelected()) {
					chrono.toggleChannel(2);
				}
				if(tog3.isSelected()) {
					chrono.toggleChannel(3);
				}
				if(tog4.isSelected()) {
					chrono.toggleChannel(4);
				}
				if(tog5.isSelected()) {
					chrono.toggleChannel(5);
				}
				if(tog6.isSelected()) {
					chrono.toggleChannel(6);
				}
				if(tog7.isSelected()) {
					chrono.toggleChannel(7);
				}
				if(tog8.isSelected()) {
					chrono.toggleChannel(8);
				}
				
				tog1.setSelected(false);
				tog2.setSelected(false);
				tog3.setSelected(false);
				tog4.setSelected(false);
				tog5.setSelected(false);
				tog6.setSelected(false);
				tog7.setSelected(false);
				tog8.setSelected(false);
				textField.setText("00:00:00.0");
				textClear.setText("");
				stdIn = "";
				runnerText.setText("");
				printerText.setText("");
				textRacer.setText("Racer ###");
				ourState = state.GETBIB;
				btnNewRun.setEnabled(false);
				btnEndRun.setEnabled(false);
				btnIndRun.setEnabled(true);
				btnParindRun.setEnabled(true);
				btnPargrpRun.setEnabled(true);
				btnGrpRun.setEnabled(true);
				//window2.exit();
				tog1.setEnabled(false);
				tog2.setEnabled(false);
				tog3.setEnabled(false);
				tog4.setEnabled(false);
				tog5.setEnabled(false);
				tog6.setEnabled(false);
				tog7.setEnabled(false);
				tog8.setEnabled(false);
				window2 = new GuiSensors();
				window2.getFrame().setVisible(true);
			}
		});	
		btnNewRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chrono.newRun(selectedRaceType);
				chrono.setRaceType(selectedRaceType);
				runnerText.setText("");
				chrono.getCurrentRun().addQueueUpdateEventListener(runQueueListener);
				btnIndRun.setEnabled(false);
				btnParindRun.setEnabled(false);
				btnPargrpRun.setEnabled(false);
				btnGrpRun.setEnabled(false);
				btnEndRun.setEnabled(true);
				tog1.setEnabled(true);
				tog2.setEnabled(true);
				tog3.setEnabled(true);
				tog4.setEnabled(true);
				tog5.setEnabled(true);
				tog6.setEnabled(true);
				tog7.setEnabled(true);
				tog8.setEnabled(true);
			}
		});
		
		// ------------ Reset button ------------
		
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
				buttonReset.doClick();
				if(value == true) {
					for(Object c : frame.getContentPane().getComponents()) {
						if(c instanceof JButton) {
							//if(!((JButton) c).getText().equals("Ind Run")&&!((JButton) c).getText().equals("Grp Run")&&!((JButton) c).getText().equals("ParInd Run")&&!((JButton) c).getText().equals("ParGrp Run") )
							if(!((JButton) c).getText().equals("New Run")&&!((JButton) c).getText().equals("End Run"))
								((JButton) c).setEnabled(value);
						}
					}
				}else {
					for(Object c : frame.getContentPane().getComponents()) {
						if(c instanceof JButton) {
							((JButton) c).setEnabled(value);
						}
					}
				}
				for(Object c : panel.getComponents()) {
					if(c instanceof JButton) {
						((JButton) c).setEnabled(value);
					}
				}
				for(Object c : panel_1.getComponents()) {
					if(c instanceof JButton) {
						((JButton) c).setEnabled(value);
					}
					if(c instanceof JRadioButton) {
						((JRadioButton) c).setEnabled(value);
					}
				}
				tog1.setEnabled(false);
				tog2.setEnabled(false);
				tog3.setEnabled(false);
				tog4.setEnabled(false);
				tog5.setEnabled(false);
				tog6.setEnabled(false);
				tog7.setEnabled(false);
				tog8.setEnabled(false);
				buttonPower.setEnabled(true);
			}
		});
		
		btnEndRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Run currentRun = chrono.getCurrentRun();
				boolean value = chrono.endRun();
				if(value) {
					currentRun.removeQueueUpdateEventListener(runQueueListener);
					if (runQueueTimer.isRunning())
						runQueueTimer.stop(); 
					btnIndRun.setEnabled(value);
					btnParindRun.setEnabled(value);
					btnPargrpRun.setEnabled(value);
					btnGrpRun.setEnabled(value);
					btnNewRun.setEnabled(false);
					btnEndRun.setEnabled(false);
					if(tog1.isSelected()) {
						chrono.toggleChannel(1);
					}
					if(tog2.isSelected()) {
						chrono.toggleChannel(2);
					}
					if(tog3.isSelected()) {
						chrono.toggleChannel(3);
					}
					if(tog4.isSelected()) {
						chrono.toggleChannel(4);
					}
					if(tog5.isSelected()) {
						chrono.toggleChannel(5);
					}
					if(tog6.isSelected()) {
						chrono.toggleChannel(6);
					}
					if(tog7.isSelected()) {
						chrono.toggleChannel(7);
					}
					if(tog8.isSelected()) {
						chrono.toggleChannel(8);
					}
					
					tog1.setSelected(false);
					tog2.setSelected(false);
					tog3.setSelected(false);
					tog4.setSelected(false);
					tog5.setSelected(false);
					tog6.setSelected(false);
					tog7.setSelected(false);
					tog8.setSelected(false);
					
					tog1.setEnabled(false);
					tog2.setEnabled(false);
					tog3.setEnabled(false);
					tog4.setEnabled(false);
					tog5.setEnabled(false);
					tog6.setEnabled(false);
					tog7.setEnabled(false);
					tog8.setEnabled(false);
				}
 			}
 		});
				
				// ------------ Power button ------------
		
		buttonPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(racerNumber);
				if(ourState == state.GETBIB) {
					if(stdIn != "") {
						chrono.addRacer(Integer.parseInt(stdIn));
					}
					stdIn = "";
					textRacer.setText("");
				} else if(ourState == state.GETCLEARBIB) {
					btnClear.doClick();
				} else if (ourState == state.GETTIME) {
					btnTime.doClick();
				}
			}
		});
		
		for(Object c : frame.getContentPane().getComponents()) {
			if(c instanceof JButton) {
				((JButton) c).setEnabled(false);
			}
		}
		for(Object c : panel.getComponents()) {
			if(c instanceof JButton) {
				((JButton) c).setEnabled(false);
			}
		}
			for(Object c : panel_1.getComponents()) {
			if(c instanceof JButton) {
				((JButton) c).setEnabled(false);
			}
			if(c instanceof JRadioButton) {
				((JRadioButton) c).setEnabled(false);
			}
		}
		buttonPower.setEnabled(true);
				
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
		
		runQueueListener = new ChronoTimerRunQueueUpdateListener(runnerText);
		
		runQueueTimer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { 		 
				updateQueueDisplay(runnerText);
			}
		});
		
		runnerText.setText("Ready.");
		
		textRacer = new JTextField();
		textRacer.setText("Racer ###");
		textRacer.setBounds(577, 247, 79, 26);
		frame.getContentPane().add(textRacer);
		textRacer.setColumns(10);
	}
	
	private void updateQueueDisplay(JTextArea textArea) {
		StringBuilder builder = new StringBuilder();
		
		if (runQueueListener.waitQueue != null) {
			builder.append("Wait Queue:\r\n");
			
			if (runQueueListener.waitQueue.length > 0) {
				for(Racer racer : runQueueListener.waitQueue) {
					if(racer != null){
						String msg = MessageFormat.format("Racer {0}\r\n", racer.getBibNumber());
						builder.append(msg);
					}
				}
			} else {
				builder.append("No one is waiting.\r\n");
			}
			builder.append("\r\n");
		}
		
		if (runQueueListener.runningQueue != null) {
			builder.append("Racers running:\r\n");
			
			if (runQueueListener.runningQueue.length > 0) {
				if (!runQueueTimer.isRunning())
					runQueueTimer.start();
				
				for(Racer racer : runQueueListener.runningQueue) {
					if(racer!= null){
	 					String msg = MessageFormat.format("Racer {0} | Start Time: {1}\r\n    -Time: +{2}\r\n",  
							racer.getBibNumber(),
							Chronotimer.ourTimer.formatTime(racer.getStartTime()),
							Chronotimer.ourTimer.formatTime(racer.getCurrentRaceTime()));
	 					
						builder.append(msg);
					}
				}
			} else {
				if (runQueueTimer.isRunning())
					runQueueTimer.stop(); 
				builder.append("No one is running.\r\n");
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
				builder.append("No one has finished.\r\n");
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
