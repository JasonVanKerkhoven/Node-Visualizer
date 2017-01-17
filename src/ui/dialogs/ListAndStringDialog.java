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
import java.util.Collection;

import javax.swing.JButton;


public class ListAndStringDialog<Etype> extends JDialog implements ActionListener
{
	//declaring static class variables
	public static final int WINDOW_CLOSE_OPTION = -1;
	public static final int OK_OPTION = 0;
	public static final int CANCEL_OPTION = 1;
	
	//declaring local instance variables
	private Etype element;
	private String string;
	private int closeMode;
	
	private JTextField inputText;
	private JList list;
	private JButton btnOk, btnCancel;
	
	
	//generic constructor
	public ListAndStringDialog(JFrame callingFrame, String windowName, String msg)
	{
		//set up main window/dialog
		super(callingFrame, true);
		this.setType(Type.POPUP);
		this.setTitle(windowName);
		this.getContentPane().setLayout(null);
		this.setBounds(0, 0, 450, 250);
		
		//add panel to hold all components
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 212);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		//add flavor text (msg)
		JTextField flavorText = new JTextField();
		flavorText.setHorizontalAlignment(SwingConstants.CENTER);
		flavorText.setEditable(false);
		flavorText.setText(msg);
		flavorText.setBackground(UIManager.getColor("Panel.background"));
		flavorText.setBounds(60, 20, 314, 20);
		panel.add(flavorText);
		flavorText.setColumns(10);
		flavorText.setBorder(null);
		
		//add and set up input text field for user string
		inputText = new JTextField();
		inputText.setBounds(224, 100, 200, 20);
		inputText.addActionListener(this);
		inputText.setColumns(10);
		panel.add(inputText);
		
		//add scrollpane to hold collection of Etype elements
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 63, 200, 100);
		panel.add(scrollPane);
		
		//add and initialize list to show collection of Etypes
		//TODO link up nNtwork to this list
		list = new JList();
		scrollPane.add(list);
		
		//add okay button
		btnOk = new JButton("OK");
		btnOk.setBounds(101, 178, 89, 23);
		btnOk.addActionListener(this);
		panel.add(btnOk);
		
		//add cancel button
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(241, 178, 89, 23);
		btnCancel.addActionListener(this);
		panel.add(btnCancel);

		//internal field initialization
		element = null;
		string = null;
		closeMode = WINDOW_CLOSE_OPTION;
		
		//make dialog visible
		this.setLocationRelativeTo(callingFrame);
		this.setVisible(true);
	}
	
	
	//generic accessors
	public String getString()
	{
		return string;
	}
	public Etype getElement()
	{
		return element;
	}
	public int getCloseMode()
	{
		return closeMode;
	}
	
	
	//close window
	public void close()
	{
		this.dispose();
	}


	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		Object src = ae.getSource();
		
		//text field activated OR ok button pressed
		if (src != btnCancel)
		{
			string = inputText.getText();
			//TODO save list to element;
			closeMode = OK_OPTION;
		}
		else
		{
			closeMode = CANCEL_OPTION;
		}
		
		close();
		
	}
}
