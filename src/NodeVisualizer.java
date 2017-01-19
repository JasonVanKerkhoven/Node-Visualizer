/**
*Class:             NodeVisualizer.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    18/01/2016                                              
*Version:           0.4.0                                         
*                                                                                   
*Purpose:           Main class and logic for Node Visualizer project.
*					Add nodes, link them, and view.
* 
* 
*Update Log:		v0.4.1
*						- functionality for linking nodes via menu bar implemented
*					v0.4.0
*						- glitch were error pop-up denoting a value must be non "" would 
*						  always display if addNode dialog was closed or canceled patched
*						- removing nodes via menu bar implemented
*						- changing node values via menu bar implemented
*						- Exit/Save dialog boxes made clearer
*						- delinking nodes via menu bar implemented
*					v0.3.1
*						- program can be reset from menubar
*						- program updated to use new ActionEvent identification
*						  using action command Strings from NodeUI
*						- actionPerformed(...) method re-written for improved readability +
*						  efficiency
*					v0.3.0
*						- ui runs on single thread with Node Visualizer now
*						- event handling for multiple ActionEvents implemented
*						- adding new nodes from menu bar implemented
*						- exiting via menu bar implemented
*						- verbose toggling via menu bar implemented
*					v0.2.0
*						- implements new controller for nodes (Network.java)
*						- command line for adding nodes
*						- command line for removing nodes
*						- command line for linking nodes
*						- command line for delinking nodes
*						- command line for reset
*					v0.1.0
*						- basic logic for adding nodes implemented
*/


//import external libraries
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//import internal packages
import network.*;
import ui.*;
import ui.dialogs.*;


public class NodeVisualizer implements ActionListener
{
	//declaring local static class constants
	public static final String NAME = "Node Visualizer";
	public static final String VERSION = " v0.4.0";
	private static final String UNKNOWN_INPUT_MSG = "Unknown Command";
	private static final String OP_ERROR_NODE = "Nodes must be entered as 'node#' or '#'.\nWhere # is is any valid integer";
	
	//declaring local instance variables
	private NodeUI ui;
	private Network nodes;
	private boolean verbose;
	private int lastSavedState;
	
	
	//generic constructor
	public NodeVisualizer()
	{
		//initialize things
		nodes = new Network();
		ui = new NodeUI(NAME + VERSION, this, nodes);
		verbose = false;
		lastSavedState = 0;
		
		ui.println("Node Visualizer running...");
	}
	
	
	//total reset
	private void reset()
	{	
		lastSavedState = 0;
		verbose = false;
		ui.clearConsole();
		nodes.clear();
		ui.updateNodeList();
	}
	
	
	//reset requested
	private void reqReset()
	{
		boolean b = ui.getInputBool(NAME, "Are you sure you want to close the current session?");
		if (b)
		{
			reset();
		}
	}
	
	
	//request exit
	private void reqExit()
	{
		//check if file saved
		if(true)
		{
			//prompt user to save or exit
			String options[] = {"Save and Exit", "Exit", "Cancel"};
			int selected = ui.getInputOptions("Unsaved Work", "You have unsaved data", options, 2);
			
			switch(selected)
			{
				//Save and Exit
				case(0):
					save(false);
					System.exit(0);
					break;
					
				//Exit w/out saving
				case(1):
					System.exit(0);
					break;
			}
			
		}	
		//no changes, can exit without confirmation
		else
		{
			System.exit(1);
		}
	}
	
	
	//save the file as a .node
	private void save(boolean saveAsMode)
	{
		/*
		 * TODO
		 * write the data
		 */
		
		
		//update the savestate hashcode
		lastSavedState = nodes.hashCode();
	}
	
	
	//add a new node
	private void addNode()
	{
		String toAdd = ui.getInputString("Add Node", "Enter the contents of the new node.");		
		//check returned string for validity
		if (toAdd != null)
		{
			if (!toAdd.equals(""))
			{
				try
				{
					nodes.add(toAdd);
					ui.updateNodeList();
					return;
				}
				catch (NetworkException e)
				{
					ui.printError(e.msgTitle, e.getMessage());
					return;
				}
			}
			else
			{
				ui.printError("Error", "Value field cannot be left blank");
			}
		}
	}
	
	
	//remove a node
	private void removeNode()
	{
		Node toRemove = ui.getInput1Node("Remove Node", "Select the node you wish to remove");
		if (toRemove != null)
		{
			try
			{
				nodes.remove(toRemove);
				ui.updateNodeList();
			}
			catch (NetworkException e)
			{
				ui.printError(e.msgTitle, e.getMessage());
			}
		}
	}
	
	
	//change a node
	private void changeNode()
	{
		//get information
		ListAndStringDialogStateWrapper wrapper =
		ui.getInputNodeAndString("Change Node Value", "Please select the Node you wish to change, and enter value you wish to change it to.");
	
		//check for validity
		if(wrapper.closeMode == ListAndStringDialog.OK_OPTION)
		{
			try
			{
				nodes.changeNode((Node)wrapper.element, wrapper.string);
				ui.updateNodeList();
			}
			catch (NetworkException e)
			{
				ui.printError(e.msgTitle, e.getMessage());
			}
		}
	}
	
	
	//link a node
	private void linkNode()
	{
		Node[] inputNodes = ui.getInput2Nodes("Link Nodes", "Select the two nodes you wish to link");
		if(inputNodes != null)
		{
			//check there is valid Node for each entry
			if (inputNodes[0] == null || inputNodes[1] == null)
			{
				ui.printError("Link Error", "You must select both a target node and a node to delink");
			}
			else
			{
				try
				{
					nodes.link(inputNodes[0], inputNodes[1]);
					ui.updateNodeList();
				}
				catch (NetworkException e)
				{
					ui.printError(e.msgTitle, e.getMessage());
				}
			}
		}
	}
	
	
	//delink a node
	private void delinkNode()
	{
		Node[] inputNodes = ui.getInput2LinkedNodes("Delink Node", "Select the Nodes you wish to delink");
		if (inputNodes != null)
		{
			//check there is a valid selection for both nodes
			if (inputNodes[0] == null || inputNodes[1] == null)
			{
				ui.printError("Delink Error", "You must select both a target node and a node to delink");
			}
			else
			{
				try
				{
					nodes.delink(inputNodes[0], inputNodes[1]);
					ui.updateNodeList();
				}
				catch (NetworkException e)
				{
					ui.printError(e.msgTitle, e.getMessage());
				}
			}
		}
		
	}
	
	
	//check valid form of node input, return plain Integer
	private Integer toID(String string)
	{
		//remove word "node" from string
		string = string.toLowerCase();
		string = string.replaceAll("node", "");

		//convert number to int, return null if NaN
		try
		{
			return Integer.parseInt(string);
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}
	
	
	//does what the tin says
	private void handleConsoleInput()
	{
		//get and parse input
		String input[] = ui.getParsedInput();
		
		//handle input based on number of words entered
		if (input != null && input.length > 0)
		{		
			switch(input.length)
			{
				case(1):
					//close the program
					if (input[0].equals("close") || input[0].equals("exit"))
					{
						System.exit(0);
					}
				
					//clear console
					else if (input[0].equals("clear"))
					{
						ui.clearConsole();
					}
				
					//reset all nodes
					else if (input[0].equals("reset"))
					{
						if (verbose)
						{
							ui.println("Clearing all nodes...");
							ui.println("Resting ID Dispatcher...");
						}
						nodes.clear();
						ui.updateNodeList();
					}
				
					//print string representation of all nodes
					else if (input[0].equals("printall"))
					{
						ui.println(nodes.toString());
					}
					
					//unknown input
					else
					{
						ui.println(UNKNOWN_INPUT_MSG) ;
					}
				break;
				
				
				case(2):
					//toggle verbose
					if (input[0].equals("verbose"))
					{
						if(input[1].equals("true"))
						{
							verbose = true;
						}
						else if (input[1].equals("false"))
						{
							verbose = false;
						}
						else
						{
							ui.printError("Opperand Error", "Verbose must be either 'true' or 'false'");
						}
						ui.setVerbose(verbose);
					}
					
					//add node
					else if (input[0].equals("add"))
					{
						try
						{
							nodes.add(input[1]);
							ui.updateNodeList();
						}
						catch (NetworkException e)
						{
							ui.printError(e.msgTitle, e.getMessage());
						}
					}
				
					//remove node
					else if (input[0].equals("remove"))
					{
						try
						{
							Integer n = toID(input[1]);
							if (n != null)
							{
								nodes.remove(n);
								ui.updateNodeList();
							}
							else
							{
								ui.printError("Opperand Error", OP_ERROR_NODE);
							}
						}
						catch (NetworkException e)
						{
							ui.printError(e.msgTitle, e.getMessage());
						}
					}
				
					//delink all nodes
					else if (input[0].equals("delink"))
					{
						try
						{
							Integer n = toID(input[1]);
							if ( n != null)
							{
								nodes.delinkAll(n);
								ui.updateNodeList();
							}
							else
							{
								ui.printError("Opperand Error", OP_ERROR_NODE);
							}
						}
						catch (NetworkException e)
						{
							ui.printError(e.msgTitle, e.getMessage());
						}
					}
				
					//unknown
					else
					{
						ui.println(UNKNOWN_INPUT_MSG);
					}
				break;
				
				
				case(4):
					//link OR delink a node
					if (input[0].equals("link") && input[2].equals("to") || input[0].equals("delink") && input[2].equals("from"))
					{
						Integer n1 = toID(input[1]);
						Integer n2 = toID(input[3]);
						
						if (n1 != null && n2 != null)
						{
							try
							{
								if (input[0].equals("link"))
								{
									nodes.link(n1, n2);
								}
								else
								{
									nodes.delink(n2, n1);
								}
								
								ui.updateNodeList();
							}
							catch (NetworkException e)
							{
								ui.printError(e.msgTitle, e.getMessage());
							}
						}
						else
						{
							ui.printError("Opperand Error", OP_ERROR_NODE);
						}
					}
					
					//unknown
					else
					{
						ui.println(UNKNOWN_INPUT_MSG);
					}
				break;
				
				
				default:
					ui.println(UNKNOWN_INPUT_MSG);
				break;
			}
		}
	}
	
	
	@Override
	//user input entered
	public void actionPerformed(ActionEvent ae) 
	{
		//determine source and handle accordingly
		String cmd = ae.getActionCommand();
		switch(cmd)
		{
			//exit program
			case(NodeUI.MENU_EXIT):
				reqExit();
				break;
			
			//restart the program to default state
			case(NodeUI.MENU_NEW):
				reqReset();
				break;	
				
			//save the data
			case(NodeUI.MENU_SAVE):
				save(false);
				break;
			
			//save the data, prompt for filename selection/creation
			case(NodeUI.MENU_SAVEAS):
				save(true);
				break;
			
			//add a new node via menu bar
			case(NodeUI.MENU_ADDNEW):
				addNode();
				break;
			
			//change the value of a node via menu bar
			case(NodeUI.MENU_CHANGE):
				changeNode();
				break;
			
			//delink a node via menu bar
			case(NodeUI.MENU_DELINK):
				delinkNode();
				break;
			
			//link a node via menu bar
			case(NodeUI.MENU_LINK):
				linkNode();
				break;
			
			//remove a node via menu bar
			case(NodeUI.MENU_REMOVE):
				removeNode();
				break;
			
			//toggle verbose
			case(NodeUI.MENU_VERBOSE):
				verbose = ui.getVerbose();
				break;
			
			//show help for using menubar
			case(NodeUI.MENU_TOOLBARHELP):
				//TODO
				break;
			
			//show help for using console
			case(NodeUI.MENU_CMDHELP):
				//TODO
				break;
				
			//otherwise parse cmdline
			default:
				handleConsoleInput();
				break;		
		}
	}
	
	//main
	public static void main(String[] args) 
	{
		NodeVisualizer nv = new NodeVisualizer();
	}
}
