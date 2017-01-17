/**
*Class:             ListAndStringDialog.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    16/01/2016                                              
*Version:           1.0.0                                         
*                                                                                   
*Purpose:           Custom pop-op allowing the user to select one item from a 
*					collection, and enter a string.
* s
* 
*Update Log:		v1.0.0
*						- null
*/
package ui.dialogs;


//various imports
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import java.awt.BorderLayout;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;


public class ListAndStringDialog extends JDialog implements ActionListener
{
	//declaring static class variables
	public static final String BUTTON_OK = "btn/ok";
	public static final String BUTTON_CANCEL = "btn/cancel";
	
	//declaring local instance variables
	private JFrame localFrame;
	private JTextField inputText;
	private JList list;
	
	
	//generic constructor
	public ListAndStringDialog(JFrame mainframe, String windowName, String msg)
	{
		super(mainframe, true);
		setType(Type.POPUP);
		this.setTitle(windowName);
		this.getContentPane().setLayout(null);
		this.setBounds(0, 0, 450, 250);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 212);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JTextField flavorText = new JTextField();
		flavorText.setHorizontalAlignment(SwingConstants.CENTER);
		flavorText.setEditable(false);
		flavorText.setText(msg);
		flavorText.setBackground(UIManager.getColor("Panel.background"));
		flavorText.setBounds(60, 20, 314, 20);
		panel.add(flavorText);
		flavorText.setColumns(10);
		flavorText.setBorder(null);
		
		inputText = new JTextField();
		inputText.setBounds(224, 100, 200, 20);
		panel.add(inputText);
		inputText.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 63, 200, 100);
		panel.add(scrollPane);
		
		//TODO link up nNtwork to this list
		list = new JList();
		scrollPane.add(list);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(101, 178, 89, 23);
		btnOk.setActionCommand(BUTTON_OK);
		btnOk.addActionListener(this);
		panel.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(241, 178, 89, 23);
		btnCancel.setActionCommand(BUTTON_CANCEL);
		btnCancel.addActionListener(this);
		panel.add(btnCancel);

		this.setLocationRelativeTo(mainframe);
		this.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		String src = ae.getActionCommand();
		
		//determine which button pressed
		switch(src)
		{
			//OK button pressed
			case(BUTTON_OK):
				//TODO link this up
				break;
			
			
			//Cancel button pressed:
			case(BUTTON_CANCEL):
				//TODO link this up
				break;
		}
	}
	
	
	//button li
}
