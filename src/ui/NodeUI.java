/**
*Class:             NodeException.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    24/12/2016                                              
*Version:           0.2.0                                                      
*                                                                                   
*Purpose:           UI designed to work with NodeVisualizer.
*					Only designed to be accessed by a single thread.
* 
*Update Log:		v0.2.1
*						- "on the fly" list of nodes added
*					v0.2.0
*						- extra spaces in input ignored
*						- input parsing patched
*						- now can have inputs with spaces (surround with quotation marks)
*						- added fancy pop ups for errors
*						- console buffer added
*					v0.1.0
*						- basic template for javax.swing UI objects
*/
package ui;


//import packages
import network.*;

//import external libraries 
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.LinkedList;
import java.awt.Color;


public class NodeUI implements Runnable, KeyListener
{
	//declaring class constants
	private static final String VERSION = "0.1.0 alpha";
	private static final String WINDOW_TITLE = "Node Visualizer " + VERSION ;
	private static final Font DEFAULT_CONSOLE_FONT = new Font("Monospaced", Font.PLAIN, 11);
	private static final int DEFAULT_WINDOW_X = 1500;
	private static final int DEFAULT_WINDOW_Y = 800;
	
	//declaring local instance variables
	private CappedBuffer inputBuffer;
	private JFrame mainFrame;
	private JMenuBar menuBar;
	private JMenu mnFile, mnAdd, mnNode, mnOptions, mnHelp;
	private JTextField consoleInput;
	private JTextArea consoleOutput;
	private JPanel drawSpace; 
	private JTextArea nodeList;
	private Network nodes;
	
	
	//generic constructor
	public NodeUI(ActionListener listener, Network nodes) 
	{
		//initialize things
		inputBuffer = new CappedBuffer(25);
		this.nodes = nodes;
		
		//set up main window frame
		mainFrame = new JFrame(WINDOW_TITLE);
		mainFrame.setResizable(false);
		mainFrame.setBounds(100, 100, DEFAULT_WINDOW_X, DEFAULT_WINDOW_Y);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		
		//set up menu bar
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, DEFAULT_WINDOW_X, 26);
		mainFrame.getContentPane().add(menuBar);
		
		//add items to menu bar
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		mnAdd = new JMenu("Edit");
		menuBar.add(mnAdd);
		mnNode = new JMenu("Node");
		menuBar.add(mnNode);
		mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		//set up split pane for console objects
		JSplitPane consolePane = new JSplitPane();
		consolePane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		consolePane.setBounds(0, 26, DEFAULT_WINDOW_X, 110);
		mainFrame.getContentPane().add(consolePane);
		
		//add JTextArea to consolePane for console output (JTextArea in JScrollPane object w/ word wrap)
		consoleOutput = new JTextArea();
		consoleOutput.setToolTipText("Console Output");
		consoleOutput.setWrapStyleWord(true);
		consoleOutput.setEditable(false);
		consoleOutput.setFont(DEFAULT_CONSOLE_FONT);
		JScrollPane scrollPane = new JScrollPane(consoleOutput);
		consolePane.setLeftComponent(scrollPane);
		
		//add JconsoleInput for console input
		consoleInput = new JTextField();
		consoleInput.setToolTipText("Console Input");
		consoleInput.setFont(DEFAULT_CONSOLE_FONT);
		consoleInput.setColumns(10);
		consoleInput.addActionListener(listener);
		consoleInput.addKeyListener(this);
		consolePane.setRightComponent(consoleInput);
		
		//add JPanel for drawing nodes on
		drawSpace = new JPanel();
		drawSpace.setBackground(Color.WHITE);
		drawSpace.setBounds(177, 136, 1203, 624);
		drawSpace.setToolTipText("Visual Representation of Nodes");
		mainFrame.getContentPane().add(drawSpace);
		
		//add text area for listing nodes wrapped in scrollPane
		JScrollPane nodeScrollPane = new JScrollPane();
		mainFrame.getContentPane().add(nodeScrollPane);
		nodeList = new JTextArea();
		nodeList.setEditable(false);
		nodeList.setFont(DEFAULT_CONSOLE_FONT);
		nodeScrollPane.setBounds(0, 136, 175, 624);
		nodeScrollPane.setViewportView(nodeList);
	}
	
	
	@Override
	//run on new thread
	public void run() 
	{
		//set visible
		try 
		{
			this.mainFrame.setVisible(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		//print thread ID
		println("UI running on <Thread " + Thread.currentThread().getId() + ">");
	}
	
	
	//master method for printing a popup message, prints the same message to console outputArea as well
	private synchronized void printPopUp(String message, String title, int messageType, String consolePrint)
	{
		if (consolePrint != null)
		{
			println(consolePrint);
		}
		JOptionPane.showMessageDialog(mainFrame, message, title, messageType);
	}
	

	//generate an error popup AND print message to console
	public void printError(String title, String message)
	{
		printPopUp(message, title, JOptionPane.ERROR_MESSAGE, title.toUpperCase() + ": " + message);
	}
	
	
	//clear console
	public void clearConsole()
	{
		consoleOutput.setText("");
		consoleOutput.setCaretPosition(0);
	}
	
	
	//print line to console
	public void println(String printable)
	{
		printable = printable.replaceAll("\n", "\n ");
		consoleOutput.append(" " + printable + "\n");
		consoleOutput.setCaretPosition(consoleOutput.getDocument().getLength());
	}
	
	
	//enter key pressed in consoleInput
	public String[] getParsedInput()
	{	
		//Prep local method variables
		//save input and clear input line
		String input =  consoleInput.getText();
		LinkedList<String> stringList = new LinkedList<>();
		consoleInput.setText("");
		
		if(input.length() > 0)
		{
			//push to buffer
			inputBuffer.push(input);
		
			//echo input in proper format
			consoleOutput.append(">" + input + "\n");
			consoleOutput.setCaretPosition(consoleOutput.getDocument().getLength());
			
			//split input at ' ' (returns " " as single string)
			String toAdd = "";
			char[] rawInput = input.toCharArray();
			boolean ignoreSpace = false;
			for(int i=0; i<rawInput.length; i++)
			{
				//no closing quotation found, add character regardless
				if(ignoreSpace)
				{
					//check if char  is closing quotation
					if (rawInput[i] == '"')
					{
						ignoreSpace = false;
						stringList.add(toAdd);
						toAdd = "";
					}
					else
					{
						toAdd += rawInput[i];
					}
				}
				else
				{
					//check if char  is opening quotation
					if (rawInput[i] == '"')
					{
						ignoreSpace = true;
					}
					else if (rawInput[i] == ' ')
					{
						if (toAdd.length() > 0)
						{
							stringList.add(toAdd);
							toAdd = "";
						}
					}
					else
					{
						toAdd += rawInput[i];
					}
				}
			}
			
			//add final string if needed
			if (toAdd.length() > 0)
			{
				stringList.add(toAdd);
			}
			
			//terminating quotation was not found
			if (ignoreSpace)
			{
				printError("Terminating quotation not found.", "Input Error");
				return null;
			}
			else
			{
				return stringList.toArray(new String[0]);
			}
		}
		else
		{
			return null;
		}
	}
	
	
	//draw a visual representation of a node
	private void drawNode()
	{
	}
	
	
	//update the master list of nodes shown to user
	public void updateNodeList()
	{
		final String P_TAB = "   ";
		String string = "";
		Collection<Node> values = nodes.getNodes();
		LinkedList<Node> outLinks;
		
		for (Node node : values)
		{
			string += "Node " + node.getId() + " : ;" + node.getValue() + "'\n";
			outLinks = node.getOutLinks();
			if(outLinks.size() > 0)
			{
				string += P_TAB + "--> ";
				for (Node out : outLinks)
				{
					string += out.getId() + ", ";
				}
				string += "\n";
			}
		}
	}

	
	@Override
	//handle key press
	public void keyPressed(KeyEvent ke)
	{	
		int keyCode = ke.getKeyCode();
		String bufferReturn = null;
		
		//keycode handler logic
		switch(keyCode)
		{
			//up arrow press, navigate BACKWARDS through buffer
			case (KeyEvent.VK_UP):
	        	bufferReturn = inputBuffer.getOlder();
	        	if (bufferReturn != null)
	        	{
	        		consoleInput.setText(bufferReturn);
	        	}
	        	break;
	        
	        //down arrow pressed (go forward)
	        case (KeyEvent.VK_DOWN):
	        	bufferReturn = inputBuffer.getNewer();
	        	if (bufferReturn != null)
	        	{
	        		consoleInput.setText(bufferReturn);
	        	}
	        	break;
		}
	}


	@Override
	public void keyReleased(KeyEvent arg0) {}


	@Override
	public void keyTyped(KeyEvent arg0) {}
}
