package GuiBuilder;
import java.awt.EventQueue ; 
import java.awt.Font;
import java.util.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextField;
import com.jgoodies.forms.layout.FormLayout;
import com.ibm.log.CompareLogFile;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import processdemo.ProcessBuilderDemo;

public class MethodTraceGUI {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnNewButton;
	private JLabel label;
	private JLabel label1;
	private JLabel label2;
	static String  file1 ;
	static String file2 ;
	 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MethodTraceGUI window = new MethodTraceGUI();
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
	public MethodTraceGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.CYAN);
		frame.setBounds(100, 100, 550, 496);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		label = new JLabel("METHOD TRACE ANALYSER");
		label.setFont(new Font("Arial Black", Font.PLAIN, 30));
		label.setBounds(20, 0, 500, 100);
		frame.add(label);
		
		label1 = new JLabel("failing case:");
		label1.setFont(new Font("Arial Black", Font.PLAIN, 20));
		label1.setBounds(30, 179, 200, 35);
		frame.add(label1);
		
		
		label2 = new JLabel("passing case:");
		label2.setFont(new Font("Arial Black", Font.PLAIN, 20));
		label2.setBounds(30, 92, 200, 35);
		frame.add(label2);
		
		
		
		textField = new JTextField();
		textField.setBounds(219, 179, 300, 35);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(219, 92, 300, 35);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		btnNewButton = new JButton("Compare");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				file2 = textField.getText();
				file1 = textField_1.getText();
				
			    System.out.println("passing class name :- "+file1+".java");
			    System.out.println("failing class name :- "+file2+".java");
			    
			    
			    String[] str = {"jatin"} ;
			    try {
			    	ProcessBuilderDemo.main(str);
			    	CompareLogFile.main(str);
					BarChartExample.main(str);
					
				} catch (IOException | InvocationTargetException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
				
		});
		btnNewButton.setBounds(161, 269, 200, 40);
		frame.getContentPane().add(btnNewButton);
	}
	
	public static String getPass() {
		return file1;
	}
	
	public static String getFail() {
		return file2;
	}
}
