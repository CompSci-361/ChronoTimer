package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JRadioButton;
import java.awt.Color;

public class Gui {

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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton buttonPower = new JButton("Power");
		buttonPower.setBounds(6, 6, 117, 29);
		frame.getContentPane().add(buttonPower);
		
		JButton btnPrinterPower = new JButton("Printer Power");
		btnPrinterPower.setBounds(327, 6, 117, 29);
		frame.getContentPane().add(btnPrinterPower);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(new Color(211, 211, 211));
		textArea.setBounds(337, 36, 96, 55);
		frame.getContentPane().add(textArea);
		
		JLabel lblNewLabel = new JLabel("Chronotimer 1009");
		lblNewLabel.setBounds(160, 11, 117, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JButton buttonNum1 = new JButton("1");
		buttonNum1.setBounds(311, 95, 45, 45);
		frame.getContentPane().add(buttonNum1);
		
		JButton buttonNum2 = new JButton("2");
		buttonNum2.setBounds(355, 95, 45, 45);
		frame.getContentPane().add(buttonNum2);
		
		JButton buttonNum3 = new JButton("3");
		buttonNum3.setBounds(399, 95, 45, 45);
		frame.getContentPane().add(buttonNum3);
		
		JButton buttonNum4 = new JButton("4");
		buttonNum4.setBounds(311, 140, 45, 45);
		frame.getContentPane().add(buttonNum4);
		
		JButton buttonNum5 = new JButton("5");
		buttonNum5.setBounds(355, 140, 45, 45);
		frame.getContentPane().add(buttonNum5);
		
		JButton button6 = new JButton("6");
		button6.setBounds(399, 140, 45, 45);
		frame.getContentPane().add(button6);
		
		JButton button7 = new JButton("7");
		button7.setBounds(311, 185, 45, 45);
		frame.getContentPane().add(button7);
		
		JButton button8 = new JButton("8");
		button8.setBounds(355, 185, 45, 45);
		frame.getContentPane().add(button8);
		
		JButton button9 = new JButton("9");
		button9.setBounds(399, 185, 45, 45);
		frame.getContentPane().add(button9);
		
		JButton buttonStar = new JButton("*");
		buttonStar.setBounds(311, 230, 45, 45);
		frame.getContentPane().add(buttonStar);
		
		JButton buttonNum0 = new JButton("0");
		buttonNum0.setBounds(355, 230, 45, 45);
		frame.getContentPane().add(buttonNum0);
		
		JButton buttonPound = new JButton("#");
		buttonPound.setBounds(399, 230, 45, 45);
		frame.getContentPane().add(buttonPound);
		
		JButton buttonSwap = new JButton("Swap");
		buttonSwap.setBounds(6, 230, 117, 29);
		frame.getContentPane().add(buttonSwap);
		
		JButton buttonFunction = new JButton("Function");
		buttonFunction.setBounds(6, 148, 117, 29);
		frame.getContentPane().add(buttonFunction);
		
		JLabel lblNewLabel_1 = new JLabel("Start");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblNewLabel_1.setBounds(147, 37, 26, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblFinish = new JLabel("Finish");
		lblFinish.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblFinish.setBounds(144, 95, 29, 16);
		frame.getContentPane().add(lblFinish);
		
		JLabel lblEnabledisable = new JLabel("Enable/Disable");
		lblEnabledisable.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblEnabledisable.setBounds(100, 65, 73, 16);
		frame.getContentPane().add(lblEnabledisable);
		
		JLabel label = new JLabel("Enable/Disable");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		label.setBounds(100, 123, 73, 16);
		frame.getContentPane().add(label);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBackground(new Color(211, 211, 211));
		textArea_1.setBounds(135, 153, 164, 101);
		frame.getContentPane().add(textArea_1);
		
		JLabel lblNewLabel_2 = new JLabel("Queue / Running / Finish");
		lblNewLabel_2.setBounds(135, 256, 164, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		JRadioButton radioButton = new JRadioButton("");
		radioButton.setBounds(185, 60, 26, 23);
		frame.getContentPane().add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("");
		radioButton_1.setBounds(215, 60, 26, 23);
		frame.getContentPane().add(radioButton_1);
		
		JRadioButton radioButton_2 = new JRadioButton("");
		radioButton_2.setBounds(245, 60, 26, 23);
		frame.getContentPane().add(radioButton_2);
		
		JRadioButton radioButton_3 = new JRadioButton("");
		radioButton_3.setBounds(275, 60, 26, 23);
		frame.getContentPane().add(radioButton_3);
		
		JRadioButton radioButton_4 = new JRadioButton("");
		radioButton_4.setBounds(185, 117, 26, 23);
		frame.getContentPane().add(radioButton_4);
		
		JRadioButton radioButton_5 = new JRadioButton("");
		radioButton_5.setBounds(215, 117, 26, 23);
		frame.getContentPane().add(radioButton_5);
		
		JRadioButton radioButton_6 = new JRadioButton("");
		radioButton_6.setBounds(245, 117, 26, 23);
		frame.getContentPane().add(radioButton_6);
		
		JRadioButton radioButton_7 = new JRadioButton("");
		radioButton_7.setBounds(275, 117, 26, 23);
		frame.getContentPane().add(radioButton_7);
		
		JButton btn1 = new JButton("1");
		btn1.setBounds(185, 31, 26, 29);
		frame.getContentPane().add(btn1);
		
		JButton button = new JButton("3");
		button.setBounds(215, 31, 26, 29);
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("5");
		button_1.setBounds(245, 31, 26, 29);
		frame.getContentPane().add(button_1);
		
		JButton button_2 = new JButton("7");
		button_2.setBounds(275, 31, 26, 29);
		frame.getContentPane().add(button_2);
		
		JButton button_3 = new JButton("2");
		button_3.setBounds(185, 89, 26, 29);
		frame.getContentPane().add(button_3);
		
		JButton button_4 = new JButton("4");
		button_4.setBounds(215, 89, 26, 29);
		frame.getContentPane().add(button_4);
		
		JButton button_5 = new JButton("6");
		button_5.setBounds(245, 89, 26, 29);
		frame.getContentPane().add(button_5);
		
		JButton button_6 = new JButton("8");
		button_6.setBounds(275, 89, 26, 29);
		frame.getContentPane().add(button_6);
	}
}
