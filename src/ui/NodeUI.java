/**
*Class:             NodeException.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    24/01/2017                                              
*Version:           0.4.1                                                    
*                                                                                   
*Purpose:           UI designed to work for NodeVisualizer.java.
*					Only designed to be accessed by a single thread (not internally synchronized).
*					Note that while this class points to a Network data type, it NEVER
*					modifies the Network data.
* 
*Update Log:		v0.4.1
*						- cleanup unused imports
*						- removed unnecessary variables in some methods
*						- added "Open" menu item to "File" tab
*					v0.4.0
*						- custom dialog built for selecting a target node, and a node linked to the target node
*						  from a list 
*						  (NodeLinkDialog.java)
*						- custom dialog built for selecting a node and entering a string
*						  (ListAndStringDialog.java)
*						- new dialog types implemented
*						- method getInput2LinkedNodes(...) added and method getInputNodeAndString(...)
*						- node/delink and node/change tabs added functionality
*						- import cleanup
*						- tabs on console use and menubar use added to menubar under "Help" category
*						- method added to return 2 Nodes via dialog box --> getInputTwoNodes(...)
*					v0.3.2
*						- menu tabs are no longer used to ID ActionEvent types
*						  each tab now has a action command that is stored for identification
*						- unneeded accessors removed
*						- method for choosing between multiple options added
*						- method added for selecting 1 node from a list
*						- save and save as items added into "File" tab in menu bar
*					v0.3.1
*						- New tabs added in menu bar
*						- Method for getting boolean value from a dialog box added
*					v0.3.0
*						- printPopUp(...) renamed to printInfoPopUp(...)
*						- menu items added to Node menu bar
*						- action events added for menu items
*						- runs on a single thread with NodeVisualizer instance
*						- basic getters for menu bar items added
*						- method added to handle "Add New" menu bar item press
*						- File tab now is BEFORE Edit tab
*						- Close menu item added to File tab
*						- Verbose toggle added to Options tab
*					v0.2.1
*						- "on the fly" list of nodes added
*						- instances of NodeUI save associated Network as a field
*					v0.2.0
*						- extra spaces in input ignored
*						- input parsing patched
*						- now can have inputs with spaces (surround with quotation marks)
*						- added fancy pop ups for errors
*						- console buffer added
*					v0.1.0
*						- basic template
*/
package ui;


//import packages
import network.*;
import ui.dialogs.*;

//import external libraries 
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Dimension;


public class NodeUI implements KeyListener
{	
	//declaring class constants
	public static final String MENU_NEW = "m/file/new";
	public static final String MENU_EXIT = "m/file/exit";
	public static final String MENU_SAVE = "m/file/save";
	public static final String MENU_SAVEAS = "m/file/saveas";
	public static final String MENU_OPEN = "m/file/open";
	
	public static final String MENU_ADDNEW = "m/node/add";
	public static final String MENU_REMOVE = "m/node/remove";
	public static final String MENU_CHANGE = "m/node/change";
	public static final String MENU_LINK = "m/node/link";
	public static final String MENU_DELINK = "m/node/delink";
	
	public static final String MENU_VERBOSE = "m/options/verbose";
	
	public static final String MENU_CMDHELP = "m/help/cmdhelp";
	public static final String MENU_TOOLBARHELP = "m/help/toolbarhelp";
	
	private static final Font DEFAULT_CONSOLE_FONT = new Font("Monospaced", Font.PLAIN, 11);
	private static final int DEFAULT_WINDOW_X = 1500;
	private static final int DEFAULT_WINDOW_Y = 800;
	
	//declaring local instance variables
	private JFrame mainFrame;
	private JMenuBar menuBar;
	private JTextField consoleInput;
	private JTextArea consoleOutput;
	private JPanel drawSpace; 
	private JTextArea nodeList;
	JCheckBoxMenuItem mntmVerboseMode;
	
	private CappedBuffer inputBuffer;
	private Network nodes;

	
	//generic constructor
	public NodeUI(String title, ActionListener listener, Network nodes) 
	{
		//initialize things
		inputBuffer = new CappedBuffer(25);
		this.nodes = nodes;

		
		//set up main window frame
		mainFrame = new JFrame(title);
		mainFrame.setResizable(false);
		mainFrame.setBounds(100, 100, DEFAULT_WINDOW_X, DEFAULT_WINDOW_Y);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		
		
		//set up menu bar
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, DEFAULT_WINDOW_X, 26);
		mainFrame.getContentPane().add(menuBar);
		
		
		//add categories to menu bar
		JMenu mnFile = new JMenu("File");
		JMenu mnEdit = new JMenu("Edit");
		JMenu mnNode = new JMenu("Node");
		JMenu mnOptions = new JMenu("Options");
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnFile);
		menuBar.add(mnEdit);
		menuBar.add(mnNode);
		menuBar.add(mnOptions);
		menuBar.add(mnHelp);
		
		
		//add menu items to "File" category
		JMenuItem mntmExit = new JMenuItem("Exit");
		JMenuItem mntmNew = new JMenuItem("New");
		JMenuItem mntmSave = new JMenuItem("Save");
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmNew);
		mnFile.add(mntmOpen);
		mnFile.add(mntmSave);
		mnFile.add(mntmSaveAs);
		mnFile.add(mntmExit);
		
		mntmExit.setActionCommand(MENU_EXIT);
		mntmNew.setActionCommand(MENU_NEW);
		mntmSave.setActionCommand(MENU_SAVE);
		mntmSaveAs.setActionCommand(MENU_SAVEAS);
		mntmOpen.setActionCommand(MENU_OPEN);
		
		mntmExit.addActionListener(listener);
		mntmNew.addActionListener(listener);
		mntmSave.addActionListener(listener);
		mntmSaveAs.addActionListener(listener);
		mntmOpen.addActionListener(listener);
		
		
		//add menu items to "Node" category
		JMenuItem mntmAddNew = new JMenuItem("Add New");
		JMenuItem mntmRemove = new JMenuItem("Remove");
		JMenuItem mntmChangeValue = new JMenuItem("Change Value");
		JMenuItem mntmLink = new JMenuItem("Link");
		JMenuItem mntmDelink = new JMenuItem("Delink");
		mnNode.add(mntmAddNew);
		mnNode.add(mntmRemove);
		mnNode.add(mntmChangeValue);
		mnNode.add(mntmLink);
		mnNode.add(mntmDelink);

		mntmAddNew.setActionCommand(MENU_ADDNEW);
		mntmRemove.setActionCommand(MENU_REMOVE);
		mntmChangeValue.setActionCommand(MENU_CHANGE);
		mntmLink.setActionCommand(MENU_LINK);
		mntmDelink.setActionCommand(MENU_DELINK);
		
		mntmAddNew.addActionListener(listener);
		mntmRemove.addActionListener(listener);
		mntmChangeValue.addActionListener(listener);
		mntmLink.addActionListener(listener);
		mntmDelink.addActionListener(listener);
		
		
		//add menu items to "Options" category
		mntmVerboseMode = new JCheckBoxMenuItem("Verbose/Debug Mode");
		mnOptions.add(mntmVerboseMode);
		
		mntmVerboseMode.setActionCommand(MENU_VERBOSE);
		
		mntmVerboseMode.addActionListener(listener);
		
		
		//add menu items to the "Help category
		JMenuItem mntmCmdHelp = new JMenuItem("Using the toolbar");
		JMenuItem mntmToolHelp = new JMenuItem("Using the console");
		mnHelp.add(mntmCmdHelp);
		mnHelp.add(mntmToolHelp);
		
		mntmCmdHelp.setActionCommand(MENU_CMDHELP);
		mntmToolHelp.setActionCommand(MENU_TOOLBARHELP);
		
		mntmCmdHelp.addActionListener(listener);
		mntmToolHelp.addActionListener(listener);
		
		
		//set up split pane for console objects
		JSplitPane consolePane = new JSplitPane();
		consolePane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		consolePane.setBounds(10, 26, 1474, 110);
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
		consoleInput.setMaximumSize(new Dimension(DEFAULT_WINDOW_X, 20));
		consoleInput.setPreferredSize(null);
		consoleInput.addActionListener(listener);
		consoleInput.addKeyListener(this);
		consolePane.setRightComponent(consoleInput);
		
		
		//add JPanel for drawing nodes on
		drawSpace = new JPanel();
		drawSpace.setBackground(Color.WHITE);
		drawSpace.setBounds(177, 136, 1307, 624);
		drawSpace.setToolTipText("Visual Representation of Nodes");
		mainFrame.getContentPane().add(drawSpace);
		
		
		//add text area for listing nodes wrapped in scrollPane
		JScrollPane nodeScrollPane = new JScrollPane();
		mainFrame.getContentPane().add(nodeScrollPane);
		nodeList = new JTextArea();
		nodeList.setEditable(false);
		nodeList.setFont(DEFAULT_CONSOLE_FONT);
		nodeScrollPane.setBounds(10, 136, 165, 624);
		nodeScrollPane.setViewportView(nodeList);
		
		
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
	}
	
	
	//set the status of the VerboseMode checkbox
	public void setVerbose(boolean b)
	{
		this.mntmVerboseMode.setSelected(b);
	}
	//get the statue of the VerboseMode checkbox
	public boolean getVerbose()
	{
		return this.mntmVerboseMode.isSelected();
	}

	
	//master method for printing a popup message, prints the same message to console outputArea as well
	private void printInfoPopUp(String message, String title, int messageType, String consolePrint)
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
		printInfoPopUp(message, title, JOptionPane.ERROR_MESSAGE, title.toUpperCase() + ": " + message);
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
			string += "Node " + node.getId() + ": '" + node.getValue() + "'\n";
			outLinks = node.getOutLinks();
			if(outLinks.size() > 0)
			{
				for (Node out : outLinks)
				{
					string += P_TAB + "--> Node " + out.getId() + "\n";
				}
			}
		}
		
		nodeList.setText(string);
	}
	
	
	//prompt user to enter a string
	public String getInputString(String title, String msg)
	{
		return JOptionPane.showInputDialog(mainFrame, msg, title, JOptionPane.QUESTION_MESSAGE);
	}
	
	
	//prompt the user to answer a yes or no question
	public boolean getInputBool(String title, String msg)
	{
		int i = JOptionPane.showConfirmDialog(mainFrame, msg, title, JOptionPane.YES_NO_OPTION);
		
		if (i == JOptionPane.YES_OPTION)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	//prompt the user to choice one of n options
	public int getInputOptions(String title, String msg, String[] options, int defaultOption)
	{
		if (options.length <= 3)
		{
			//create dialog and get option
			int selected = JOptionPane.showOptionDialog
			(
				mainFrame,
				msg,
				title,
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]
			);

			//return index option that was pressed
			/*
			 * While	JOptionPane.YES_OPTION = 0
			 * 			JOptionPane.NO_OPTION = 1
			 * 			JOptionPane.CANCEL_OPTION = 2
			 * And this case statement is (mostly) unneeded, if the values of these constants
			 * are changed in a future update of the .swing framework this method will stop
			 * functioning properly (ie it wont return the index of the option selected)
			 * Therefore this is added in order to make sure the method functions EVEN if
			 * Oracle changes the values for the constants used in JOptionPane
			 */
			switch(selected)
			{
				case(JOptionPane.YES_OPTION):		return 0;
				case(JOptionPane.NO_OPTION):		return 1;
				case(JOptionPane.CANCEL_OPTION):	return 2;
				default:							return defaultOption;
			}
		}
		else
		{
			Object selected = JOptionPane.showInputDialog
			(
				mainFrame, 
				msg, 
				title,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]
			);
			
			//determine which index of options was selected
			if (selected != null)
			{
				String value = (String)selected;
				for(int i=0; i < options.length; i++)
				{
					if(value.equals(options[i]))
					{
						return i;
					}
				}
			}
			return defaultOption;
		}
	}
	
	
	public ListAndStringDialogStateWrapper getInputNodeAndString(String title, String msg)
	{
		//prompt user for input
		Node[] nodesArr = nodes.getNodes().toArray(new Node[0]);
		ListAndStringDialog dialog = new ListAndStringDialog(mainFrame, title, msg, nodesArr);
		
		//save input
		int closeMode = dialog.getCloseMode();
		String string = dialog.getString();
		Node node = (Node)dialog.getElement();
		
		//return in wrapper
		dialog = null;
		return new ListAndStringDialogStateWrapper(closeMode, string, node);
	}
	

	//Prompt the selection of 2 nodes, return those 2 nodes
	public Node[] getInput2LinkedNodes(String title, String msg)
	{
		//prompt user for input
		Node[] nodesArr = nodes.getNodes().toArray(new Node[0]);
		NodeLinkDialog dialog = new NodeLinkDialog(mainFrame, title, msg, nodesArr);
		
		//save input
		Node[] nodes = new Node[2];
		int closeMode = dialog.getCloseMode();
		
		if (closeMode == NodeLinkDialog.OK_OPTION)
		{
			nodes[0] = dialog.getNode1();
			nodes[1] = dialog.getNode2();
		}
		else
		{
			nodes = null;
		}
		
		return nodes;
	}
	
	
	//prompt the selection of 2 independent nodes
	public Node[] getInput2Nodes(String title, String msg)
	{
		//get an array of all Nodes in Network
		Node[] nodeArr = nodes.getNodes().toArray(new Node[0]);;
		
		//call dialog
		TwoListDialog dialog = new TwoListDialog(mainFrame, title, msg, nodeArr, nodeArr);
		
		//get values from dialog and check for validity
		Node[] ret = new Node[2];
		int closeMode = dialog.getCloseMode();
		
		//user entered data
		if(closeMode == TwoListDialog.OK_OPTION)
		{
			ret[0] = (Node)dialog.getElement1();
			ret[1] = (Node)dialog.getElement2();
			return ret;
		}
		//user canceled or exited window
		else
		{
			return null;
		}
	}
	
	
	//prompt the selection of a single node, return it
	//return null if the user exits or cancels or no node to remove
	public Node getInput1Node(String title, String msg)
	{
		//get a Collection of all Nodes from Network
		Collection<Node> values = nodes.getNodes();
		
		//allow the user to select a node if there are nodes in Network
		if (values.size() > 0)
		{
			Node nodeArr[] = values.toArray(new Node[0]);
			return (Node)JOptionPane.showInputDialog(mainFrame, msg, title, JOptionPane.QUESTION_MESSAGE, null, nodeArr, nodeArr[0]);
		}
		//gives user an empty list to select, always return null
		else
		{
			String[] empty = {""};
			JOptionPane.showInputDialog(mainFrame, msg, title, JOptionPane.QUESTION_MESSAGE, null, empty, empty[0]);
			return null;
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
