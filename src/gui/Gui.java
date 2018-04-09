package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import java.awt.Font;
import javax.swing.JRadioButton;
import java.awt.Color;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.ScrollPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;

import com.sun.prism.Graphics;

import core.Chronotimer;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gui {
	static Chronotimer chrono = new Chronotimer();

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
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
		
		// ------------ Power button ------------
		JButton buttonPower = new JButton("Power");
		buttonPower.setForeground(new Color(0, 0, 0));
		buttonPower.setBounds(6, 6, 117, 29);
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

		
		JButton btnPrinterPower = new JButton("Printer Power");
		btnPrinterPower.setBounds(596, 6, 117, 29);
		frame.getContentPane().add(btnPrinterPower);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setSize(151, 193);
		scroll.setLocation(580, 40);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scroll);
		
		JLabel lblNewLabel = new JLabel("Chronotimer 1009");
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNewLabel.setBounds(278, 11, 151, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JButton buttonSwap = new JButton("Swap");
		buttonSwap.setBounds(6, 400, 117, 29);
		frame.getContentPane().add(buttonSwap);
		
		JButton buttonFunction = new JButton("Function");
		buttonFunction.setBackground(Color.PINK);
		buttonFunction.setBounds(6, 148, 117, 29);
		frame.getContentPane().add(buttonFunction);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBackground(new Color(211, 211, 211));
		textArea_1.setBounds(215, 238, 277, 206);
		frame.getContentPane().add(textArea_1);
		
		JLabel lblNewLabel_2 = new JLabel("Queue / Running / Finish");
		lblNewLabel_2.setBounds(272, 456, 164, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		JPanel panel = new JPanel();
		panel.setBounds(588, 266, 132, 186);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton buttonNum1 = new JButton("1");
		buttonNum1.setBounds(0, 0, 45, 45);
		panel.add(buttonNum1);
		
		JButton buttonNum2 = new JButton("2");
		buttonNum2.setBounds(44, 0, 45, 45);
		panel.add(buttonNum2);
		
		JButton buttonNum3 = new JButton("3");
		buttonNum3.setBounds(88, 0, 45, 45);
		panel.add(buttonNum3);
		
		JButton buttonNum4 = new JButton("4");
		buttonNum4.setBounds(0, 45, 45, 45);
		panel.add(buttonNum4);
		
		JButton buttonNum5 = new JButton("5");
		buttonNum5.setBounds(44, 45, 45, 45);
		panel.add(buttonNum5);
		
		JButton buttonNum6 = new JButton("6");
		buttonNum6.setBounds(88, 45, 45, 45);
		panel.add(buttonNum6);
		
		JButton buttonNum7 = new JButton("7");
		buttonNum7.setBounds(0, 90, 45, 45);
		panel.add(buttonNum7);
		
		JButton buttonNum8 = new JButton("8");
		buttonNum8.setBounds(44, 90, 45, 45);
		panel.add(buttonNum8);
		
		JButton buttonNum9 = new JButton("9");
		buttonNum9.setBounds(88, 90, 45, 45);
		panel.add(buttonNum9);
		
		JButton buttonNum0 = new JButton("0");
		buttonNum0.setBounds(44, 135, 45, 45);
		panel.add(buttonNum0);
		
		JButton buttonStar = new JButton("*");
		buttonStar.setBounds(0, 135, 45, 45);
		panel.add(buttonStar);
		
		JButton buttonPound = new JButton("#");
		buttonPound.setBounds(88, 135, 45, 45);
		panel.add(buttonPound);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		panel_1.setBounds(215, 40, 277, 193);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Start");
		lblNewLabel_1.setBounds(67, 48, 26, 16);
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		JLabel lblEnabledisable = new JLabel("Enable/Disable");
		lblEnabledisable.setBounds(20, 76, 73, 16);
		panel_1.add(lblEnabledisable);
		lblEnabledisable.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		JLabel lblFinish = new JLabel("Finish");
		lblFinish.setBounds(64, 135, 29, 16);
		panel_1.add(lblFinish);
		lblFinish.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		JLabel label = new JLabel("Enable/Disable");
		label.setBounds(20, 171, 73, 16);
		panel_1.add(label);
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		JPopupMenu menu2 = new JPopupMenu();
		JPopupMenu menu3 = new JPopupMenu();
		JPopupMenu menu4 = new JPopupMenu();
		JPopupMenu menu5 = new JPopupMenu();
		JPopupMenu menu6 = new JPopupMenu();
		JPopupMenu menu7 = new JPopupMenu();
		JPopupMenu menu8 = new JPopupMenu();
		
		JButton trig1 = new JButton("1");
		trig1.setBounds(99, 42, 26, 29);
		panel_1.add(trig1);
		
		JButton trig3 = new JButton("3");
		trig3.setBounds(137, 42, 26, 29);
		panel_1.add(trig3);
		
		JRadioButton tog7 = new JRadioButton("");
		tog7.setBounds(213, 73, 26, 23);
		panel_1.add(tog7);
		
		JButton trig2 = new JButton("2");
		trig2.setBounds(99, 129, 26, 29);
		panel_1.add(trig2);
		
		JRadioButton tog4 = new JRadioButton("");
		tog4.setBounds(137, 164, 26, 23);
		panel_1.add(tog4);
		
		JButton trig4 = new JButton("4");
		trig4.setBounds(137, 129, 26, 29);
		panel_1.add(trig4);
		
		JRadioButton tog6 = new JRadioButton("");
		tog6.setBounds(175, 164, 26, 23);
		panel_1.add(tog6);
		
		JButton trig6 = new JButton("6");
		trig6.setBounds(175, 129, 26, 29);
		panel_1.add(trig6);
		
		JRadioButton tog8 = new JRadioButton("");
		tog8.setBounds(213, 164, 26, 23);
		panel_1.add(tog8);
		
		JButton trig8 = new JButton("8");
		trig8.setBounds(213, 129, 26, 29);
		panel_1.add(trig8);
		
		JRadioButton tog3 = new JRadioButton("");
		tog3.setBounds(137, 73, 26, 23);
		panel_1.add(tog3);
		
		JRadioButton tog5 = new JRadioButton("");
		tog5.setBounds(175, 73, 26, 23);
		panel_1.add(tog5);
		
		JRadioButton tog1 = new JRadioButton("");
		tog1.setBounds(99, 73, 26, 23);
		panel_1.add(tog1);
		
		JButton trig7 = new JButton("7");
		trig7.setBounds(213, 42, 26, 29);
		panel_1.add(trig7);
		
		JButton trig5 = new JButton("5");
		trig5.setBounds(175, 42, 26, 29);
		panel_1.add(trig5);
		
		JRadioButton tog2 = new JRadioButton("");
		tog2.setBounds(99, 164, 26, 23);
		panel_1.add(tog2);
		
		JLabel lblConnect = new JLabel("Connect");
		lblConnect.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblConnect.setBounds(53, 20, 40, 16);
		panel_1.add(lblConnect);
		
		JLabel label_1 = new JLabel("Connect");
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		label_1.setBounds(53, 107, 40, 16);
		panel_1.add(label_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(0, 99, 285, 5);
		panel_1.add(panel_2);
		
		JTextArea txtrText = new JTextArea();
		txtrText.setBounds(581, 40, 132, 182);
		frame.getContentPane().add(txtrText);
		
		
		
	}

}
