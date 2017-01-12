/**
*Class:             NodeVisualizer.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    12/01/2016                                              
*Version:           0.3.0                                         
*                                                                                   
*Purpose:           Main class and logic for Node Visualizer project.
*					Add nodes, link them, and view.
* 
* 
*Update Log:		v0.3.0
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


public class NodeVisualizer implements ActionListener
{
	//declaring local static class constants
	private static final String WINDOW_NAME = "Node Visualizer v0.3.0";
	private static final String UNKNOWN_INPUT_MSG = "Unknown Command";
	private static final String OP_ERROR_NODE = "Nodes must be entered as 'node#' or '#'.\nWhere # is is any valid integer";
	
	//declaring local instance constants
	private final Object mntmExit;
	private final Object mntmAddNew, mntmRemove, mntmChangeValue, mntmLink, mntmDelink;
	private final Object mntmVerboseMode;
	
	//declaring local instance variables
	private NodeUI ui;
	private Network nodes;
	private boolean verbose;
	
	
	//generic constructor
	public NodeVisualizer()
	{
		//initialize things
		nodes = new Network();
		ui = new NodeUI(WINDOW_NAME, this, nodes);
		verbose = false;
		
		this.mntmExit = ui.getMntmExit();
		
		this.mntmAddNew = ui.getMntmAddNew();
		this.mntmChangeValue = ui.getMntmChangeValue();
		this.mntmDelink = ui.getMntmDelink();
		this.mntmLink = ui.getMntmLink();
		this.mntmRemove = ui.getMntmRemove();
		
		this.mntmVerboseMode = ui.getMntmVerbose();
		
		ui.println("Node Visualizer running...");
	}
	
	
	//exit program
	private void exit()
	{
		if(verbose) ui.println("Shutdown Acknowledged");
		System.exit(0);
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
						exit();
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
		Object o = ae.getSource();
		try
		{
			//exit program
			if (o == this.mntmExit)
			{
				exit();
			}
			
			//add a new node via menu bar
			if (o == this.mntmAddNew)
			{
				if(verbose) ui.println("Node/Add New pressed");
				String toAdd = ui.getInputString("Add Node", "Enter the contents of the new node.");
				nodes.add(toAdd);
				ui.updateNodeList();
			}
			
			//change the value of a node via menu bar
			else if (o == this.mntmChangeValue)
			{
				//TODO
				if(verbose) ui.println("Node/Change Value pressed");
			}
			
			//delink a node via menu bar
			else if (o == this.mntmDelink)
			{
				//TODO
				if(verbose) ui.println("Node/Delink pressed");
			}
			
			//link a node via menu bar
			else if (o == this.mntmLink)
			{
				//TODO
				if(verbose) ui.println("Node/Link pressed");
			}
			
			//remove a node via menu bar
			else if (o == this.mntmRemove)
			{
				//TODO
				if(verbose) ui.println("Node/Remove pressed");
			}
			
			//toggle verbose mode
			else if (o == this.mntmVerboseMode)
			{
				if(verbose) ui.println("Options/Verbose Mode");
				verbose = ui.getVerbose();
			}
			
			//parse command from cmdline
			else
			{
				handleConsoleInput();
			}
		}
		catch (NetworkException e)
		{
			ui.printError(e.msgTitle, e.getMessage());
		}
	}
	
	//main
	public static void main(String[] args) 
	{
		NodeVisualizer nv = new NodeVisualizer();
	}
}
