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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import core.Chronotimer;
import core.Printer;
import core.Printer.PrintMessageActionListener;
import core.Printer.PrintMessageActionListenerEventArgs;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Icon;

public class Gui extends JPanel implements ActionListener{
	static Chronotimer chrono = new Chronotimer();

	BufferedImage test = null;
	public String racerNumber = "";
	private final Timer timer = new Timer(40, this);
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

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
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel chronoLabel = new JLabel("Chronotimer 1009");
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
				System.out.println("Power is " + (value ? "enabled" : "disabled"));
				if(value)
					buttonPower.setForeground(new Color(0, 255, 0));
				else{
					buttonPower.setForeground(Color.BLACK);
				}	    
			}
		});
		
		// ------------ Power button ------------

		// ------------ Reset button ------------
		
		JButton buttonReset = new JButton("Reset");
		buttonReset.setBounds(107, 7, 101, 29);
		frame.getContentPane().add(buttonReset);
		
		buttonReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chrono.reset();
				System.out.println("Reset Chronotimer.");    
			}
		});
		
		// ------------ Reset button ------------
		
		JButton btnPrinterPower = new JButton("Printer Power");
		btnPrinterPower.setBounds(562, 7, 117, 29);
		frame.getContentPane().add(btnPrinterPower);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setSize(229, 193);
		scroll.setLocation(504, 40);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scroll);
		
		JTextArea printerText = new JTextArea();
		printerText.setBounds(0, 40, 10, 10);
		scroll.setViewportView(printerText);
		
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBackground(new Color(211, 211, 211));
		textArea_1.setBounds(215, 238, 277, 206);
		frame.getContentPane().add(textArea_1);
		
		JButton buttonSwap = new JButton("Swap");
		buttonSwap.setBounds(6, 266, 101, 29);
		frame.getContentPane().add(buttonSwap);
		
		JButton buttonFunction = new JButton("End Run");
		buttonFunction.setBackground(Color.PINK);
		buttonFunction.setBounds(107, 135, 101, 29);
		frame.getContentPane().add(buttonFunction);
		
		JLabel lblNewLabel_2 = new JLabel("Queue / Running / Finish");
		lblNewLabel_2.setForeground(new Color(128, 0, 0));
		lblNewLabel_2.setBounds(272, 456, 164, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		// ------------ Number keys ------------
		
		JPanel panel = new JPanel();
		panel.setBounds(542, 245, 132, 186);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton buttonNum1 = new JButton("1");
		buttonNum1.setBounds(0, 0, 45, 45);
		panel.add(buttonNum1);
		
		buttonNum1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 1;    
			}
		});
		
		JButton buttonNum2 = new JButton("2");
		buttonNum2.setBounds(44, 0, 45, 45);
		panel.add(buttonNum2);
		
		buttonNum2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 2;    
			}
		});
		
		JButton buttonNum3 = new JButton("3");
		buttonNum3.setBounds(88, 0, 45, 45);
		panel.add(buttonNum3);
		
		buttonNum3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 3;    
			}
		});
		
		JButton buttonNum4 = new JButton("4");
		buttonNum4.setBounds(0, 45, 45, 45);
		panel.add(buttonNum4);
		
		buttonNum4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 4;    
			}
		});
		
		JButton buttonNum5 = new JButton("5");
		buttonNum5.setBounds(44, 45, 45, 45);
		panel.add(buttonNum5);
		
		buttonNum5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 5;    
			}
		});
		
		JButton buttonNum6 = new JButton("6");
		buttonNum6.setBounds(88, 45, 45, 45);
		panel.add(buttonNum6);
		
		buttonNum6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 6;    
			}
		});
		
		JButton buttonNum7 = new JButton("7");
		buttonNum7.setBounds(0, 90, 45, 45);
		panel.add(buttonNum7);
		
		buttonNum7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 7;    
			}
		});
		
		JButton buttonNum8 = new JButton("8");
		buttonNum8.setBounds(44, 90, 45, 45);
		panel.add(buttonNum8);
		
		buttonNum8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 8;    
			}
		});
		
		JButton buttonNum9 = new JButton("9");
		buttonNum9.setBounds(88, 90, 45, 45);
		panel.add(buttonNum9);
		
		buttonNum9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 9;    
			}
		});
		
		JButton buttonNum0 = new JButton("0");
		buttonNum0.setBounds(44, 135, 45, 45);
		panel.add(buttonNum0);
		
		buttonNum0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				racerNumber += 0;    
			}
		});
		
		JButton buttonStar = new JButton("*");
		buttonStar.setBounds(0, 135, 45, 45);
		panel.add(buttonStar);
		
		JButton buttonPound = new JButton("#");
		buttonPound.setBounds(88, 135, 45, 45);
		panel.add(buttonPound);
		
		buttonPound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(racerNumber);
				racerNumber = "";
			}
		});
		
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
		
		JPopupMenu menu2 = new JPopupMenu();
		JPopupMenu menu3 = new JPopupMenu();
		JPopupMenu menu4 = new JPopupMenu();
		JPopupMenu menu5 = new JPopupMenu();
		JPopupMenu menu6 = new JPopupMenu();
		JPopupMenu menu7 = new JPopupMenu();
		JPopupMenu menu8 = new JPopupMenu();
		
		JButton trig1 = new JButton("1");
		trig1.setBounds(99, 11, 26, 29);
		panel_1.add(trig1);
		
		JButton trig3 = new JButton("3");
		trig3.setBounds(137, 11, 26, 29);
		panel_1.add(trig3);
		
		JRadioButton tog7 = new JRadioButton("");
		tog7.setBounds(213, 54, 26, 23);
		panel_1.add(tog7);
		
		JButton trig2 = new JButton("2");
		trig2.setBounds(99, 110, 26, 29);
		panel_1.add(trig2);
		
		JRadioButton tog4 = new JRadioButton("");
		tog4.setBounds(137, 151, 26, 23);
		panel_1.add(tog4);
		
		JButton trig4 = new JButton("4");
		trig4.setBounds(137, 110, 26, 29);
		panel_1.add(trig4);
		
		JRadioButton tog6 = new JRadioButton("");
		tog6.setBounds(175, 151, 26, 23);
		panel_1.add(tog6);
		
		JButton trig6 = new JButton("6");
		trig6.setBounds(175, 110, 26, 29);
		panel_1.add(trig6);
		
		JRadioButton tog8 = new JRadioButton("");
		tog8.setBounds(213, 151, 26, 23);
		panel_1.add(tog8);
		
		JButton trig8 = new JButton("8");
		trig8.setBounds(213, 110, 26, 29);
		panel_1.add(trig8);
		
		JRadioButton tog3 = new JRadioButton("");
		tog3.setBounds(137, 54, 26, 23);
		panel_1.add(tog3);
		
		JRadioButton tog5 = new JRadioButton("");
		tog5.setBounds(175, 54, 26, 23);
		panel_1.add(tog5);
		
		JRadioButton tog1 = new JRadioButton("");
		tog1.setBounds(99, 54, 26, 23);
		panel_1.add(tog1);
		
		JButton trig7 = new JButton("7");
		trig7.setBounds(213, 11, 26, 29);
		panel_1.add(trig7);
		
		JButton trig5 = new JButton("5");
		trig5.setBounds(175, 11, 26, 29);
		panel_1.add(trig5);
		
		JRadioButton tog2 = new JRadioButton("");
		tog2.setBounds(99, 151, 26, 23);
		panel_1.add(tog2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(0, 89, 285, 5);
		panel_1.add(panel_2);
		
		JLabel label = new JLabel("Enable/Disable");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		label.setBounds(20, 153, 73, 16);
		panel_1.add(label);
		
		JButton btnNewRun = new JButton("New Run");
		btnNewRun.setBounds(107, 108, 101, 29);
		frame.getContentPane().add(btnNewRun);
		
		JButton btnIndRun = new JButton("Ind Run");
		btnIndRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnIndRun.setBounds(6, 108, 101, 29);
		frame.getContentPane().add(btnIndRun);
		
		JButton btnParindRun = new JButton("ParInd Run");
		btnParindRun.setBounds(6, 135, 101, 29);
		frame.getContentPane().add(btnParindRun);
		
		JButton btnNewButton = new JButton("Grp Run");
		btnNewButton.setBounds(6, 162, 101, 29);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnPargrpRun = new JButton("ParGrp Run");
		btnPargrpRun.setBounds(6, 190, 101, 29);
		frame.getContentPane().add(btnPargrpRun);
		
		JLabel lblRaceType = new JLabel("Race Type");
		lblRaceType.setForeground(new Color(128, 0, 0));
		lblRaceType.setBounds(26, 80, 69, 16);
		frame.getContentPane().add(lblRaceType);
		
		JButton btnTime = new JButton("Time");
		btnTime.setBounds(107, 37, 101, 29);
		frame.getContentPane().add(btnTime);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("00:00:00.0");
		textField.setToolTipText("System Time");
		textField.setBounds(9, 37, 95, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblRun = new JLabel("Run");
		lblRun.setForeground(new Color(128, 0, 0));
		lblRun.setBounds(140, 80, 32, 16);
		frame.getContentPane().add(lblRun);
		
		JLabel lblFunctions = new JLabel("Functions");
		lblFunctions.setForeground(new Color(128, 0, 0));
		lblFunctions.setBounds(74, 238, 69, 16);
		frame.getContentPane().add(lblFunctions);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(107, 327, 101, 29);
		frame.getContentPane().add(btnClear);
		
		JButton btnDnf = new JButton("DNF");
		btnDnf.setBounds(107, 266, 101, 29);
		frame.getContentPane().add(btnDnf);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(107, 295, 101, 29);
		frame.getContentPane().add(btnCancel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(9, 295, 95, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(9, 327, 95, 26);
		frame.getContentPane().add(textField_2);
				
		

		try {
	        test = ImageIO.read(new File("res/juggernot.png")); 
	        JLabel label_2 = new JLabel(new ImageIcon("/Users/scholl/Desktop/361/Sprint1/res/juggernot.png"));
	        label_2.setLocation(100, 100);
	        frame.getContentPane().add( label_2,BorderLayout.CENTER);
	        frame.setIconImage(test);
	        frame.setVisible(true);
	        frame.setTitle("Dank meme");
	    } catch (IOException ex) {
	        Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
	    }
	
		
		Printer.addPrintMessageActionListener(new PrintMessageActionListener(){

			@Override
			public void onPrintMessageReceived(PrintMessageActionListenerEventArgs args) {
				printerText.append(args.getMessage() + "\r\n");			
			}
			
		});
		
		
	}
	public void paint(Graphics g) {
	    super.paint(g);
	    g.drawImage(test, 200, 200, null);
	}
}
