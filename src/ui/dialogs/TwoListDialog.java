/**
*Class:             NodeLinkDialog.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    17/01/2016                                              
*Version:           1.0.0                                         
*                                                                                   
*Purpose:           Custom pop-op allowing the user to select a target node, and another node
*					that is linked to the target node
* s
* 
*Update Log			v1.0.0
*						- null
*/
package ui.dialogs;


//import external stuff
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.text.StyledDocument;

//import packages
import network.Node;


public class TwoListDialog extends JDialog implements ActionListener
{
	//declaring static class variables
	public static final int WINDOW_CLOSE_OPTION = -1;
	public static final int OK_OPTION = 0;
	public static final int CANCEL_OPTION = 1;
	
	//declaring local instance variables
	private int closeMode;
	private Object element1, element2;
	private JButton btnOk, btnCancel;
	private JComboBox cbList1, cbList2;
	
	
	//generic constructor
	public TwoListDialog(JFrame callingFrame, String windowName, String msg, Object[] arr1, Object[] arr2)
	{
		//set up main window/dialog
		super(callingFrame, true);
		this.setType(Type.POPUP);
		this.setTitle(windowName);
		this.getContentPane().setLayout(null);
		this.setBounds(0, 0, 450, 180);

		//internal field initialization
		element1 = null;
		element2 = null;
		closeMode = WINDOW_CLOSE_OPTION;
		
		//add panel to hold all components
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 142);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		//add flavor text (msg)
		JTextArea flavorText = new JTextArea();
		flavorText.setLineWrap(true);
		flavorText.setWrapStyleWord(true);
		flavorText.setEditable(false);
		flavorText.setText(msg);
		flavorText.setBackground(UIManager.getColor("Panel.background"));
		flavorText.setBounds(50, 20, 334, 40);
		panel.add(flavorText);
		flavorText.setColumns(10);
		flavorText.setBorder(null);
		
		//add okay button
		btnOk = new JButton("OK");
		btnOk.setBounds(100, 105, 89, 23);
		btnOk.addActionListener(this);
		panel.add(btnOk);
		
		//add cancel button
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(241, 105, 89, 23);
		btnCancel.addActionListener(this);
		panel.add(btnCancel);
		
		//add and set up the two selection boxs
		cbList1 = new JComboBox();
		cbList1.setBounds(10, 71, 200, 20);
		cbList2 = new JComboBox();
		cbList2.setBounds(224, 71, 200, 20);
		
		if (arr1.length > 0)
		{
			for (Object o : arr1)
			{
				cbList1.addItem(o);
			}
		}
		
		if (arr2.length > 0)
		{
			for (Object o : arr2)
			{
				cbList2.addItem(o);
			}
		}

		panel.add(cbList1);
		panel.add(cbList2);	
		
		//make dialog visible
		this.setLocationRelativeTo(callingFrame);
		this.setVisible(true);
	}
	
	
	//generic accessors
	public int getCloseMode()
	{
		return closeMode;
	}
	public Object getElement1()
	{
		return element1;
	}
	public Object getElement2()
	{
		return element2;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object src = e.getSource();
		
		//okay button pressed
		if (src == btnOk)
		{
			//save the state
			element1 = (Node)cbList1.getSelectedItem();
			element2 = (Node)cbList2.getSelectedItem();
			closeMode = this.OK_OPTION;
			
			//close window
			this.dispose();
		}
		
		//cancel button pressed
		else if (src == btnCancel)
		{
			//close and set closeMode
			closeMode = this.CANCEL_OPTION;
			this.dispose();
		}
	}
}






