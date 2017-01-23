/**
\*Class:             ListAndStringDialog.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    16/01/2016                                              
*Version:           1.1.0                                         
*                                                                                   
*Purpose:           Custom pop-op allowing the user to select one item from a 
*					collection, and enter a string.
* s
* 
*Update Log:		v1.1.0
*						- JList replaced with JelementDisplay
*						- flavor text now spans an area, not a line
*						- window resized and made prettier
*					v1.0.0
*						- null
*/
package ui.dialogs;


//various imports
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;


public class ListAndStringDialog extends JDialog implements ActionListener
{
	//declaring static class variables
	public static final int WINDOW_CLOSE_OPTION = -1;
	public static final int OK_OPTION = 0;
	public static final int CANCEL_OPTION = 1;
	
	//declaring local instance variables
	private Object element;
	private String string;
	private int closeMode;
	private boolean elementsEmpty;
	
	private JTextField inputText;
	private JButton btnOk, btnCancel;
	private JComboBox elementDisplay;
	
	
	//generic constructor
	public ListAndStringDialog(JFrame callingFrame, String windowName, String msg, Object[] elements)
	{
		//set up main window/dialog
		super(callingFrame, true);
		this.setType(Type.POPUP);
		this.setTitle(windowName);
		this.getContentPane().setLayout(null);
		this.setBounds(0, 0, 450, 180);

		//internal field initialization
		element = null;
		string = null;
		elementsEmpty = false;
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
		
		//add and set up input text field for user string
		inputText = new JTextField();
		inputText.setBounds(224, 71, 200, 20);
		inputText.addActionListener(this);
		inputText.setColumns(10);
		panel.add(inputText);
		
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
		
		//add and set up elementDisplay
		elementDisplay = new JComboBox();
		elementDisplay.setBounds(10, 71, 200, 20);
		if (elements.length > 0)
		{
			for (Object o : elements)
			{
				elementDisplay.addItem(o);
			}
		}
		else
		{
			elementsEmpty = true;
			elementDisplay.addItem(" ");
		}
		panel.add(elementDisplay);
		
		//make dialog visible
		this.setLocationRelativeTo(callingFrame);
		this.setVisible(true);
	}
	
	
	//generic accessors
	public String getString()
	{
		return string;
	}
	public Object getElement()
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
		if(elementsEmpty)
		{
			element = null;
		}
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
			element = elementDisplay.getSelectedItem();
			closeMode = OK_OPTION;
		}
		else
		{
			closeMode = CANCEL_OPTION;
		}
		
		close();
		
	}
}
