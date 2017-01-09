/**
*Class:             NodeVisualizer.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    24/12/2016                                              
*Version:           0.2.0                                                      
*                                                                                   
*Purpose:           Main class and logic for Node Visualizer project.
*					Add nodes, link them, and view.
* 
* 
*Update Log:		v0.2.0
*						- implements new controller for nodes (Network.java)
*						- command line for adding nodes
*						- command line for removing nodes
*						- command line for linking nodes
*						- command line for delinking nodes
*						- command line for reset
*					v0.1.0
*						- basic logic for adding nodes implemented
*						- basic logic for 
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
	//declaring static class constants
	private String UNKNOWN_INPUT_MSG = "Unknown Command";
	private String OP_ERROR_NODE = "Nodes must be entered as 'node#' or #.\nWhere # is is any valid integer";

	
	//declaring local instance variables
	private NodeUI ui;
	private Network nodes;
	private boolean verbose;
	
	
	//generic constructor
	public NodeVisualizer()
	{
		//initialize things
		ui = new NodeUI(this);
		nodes = new Network();
		verbose = false;
		
		//start UI on new thread
		Thread uiThread = new Thread(ui);
		uiThread.start();
		
		ui.println("NodeVisualizer running on <Thread " + Thread.currentThread().getId() + ">");
	}
	
	
	//check valid form of node input, return plain Integer
	private Integer toID(String string)
	{
		string = string.toLowerCase();
		string = string.replaceAll("node", "");
		
		System.out.println(string);
		
		try
		{
			return Integer.parseInt(string);
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}
	
	
	@Override
	//user input entered
	public void actionPerformed(ActionEvent ae) 
	{
		//get and parse input
		String input[] = ui.getParsedInput();
		
		//handle input based on number of words entered
		if (input != null)
		{		
			switch(input.length)
			{
				case(1):
					//close the program
					if (input[0].equals("close"))
					{
						if(verbose)
						{
							ui.println("Shutdown Acknowledged");
						}
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
					}
					
					//add node
					else if (input[0].equals("add"))
					{
						try
						{
							nodes.add(input[1]);
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
					//link/delink a node
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
									nodes.delink(n1, n2);
								}
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
	
	//main
	public static void main(String[] args) 
	{
		NodeVisualizer nv = new NodeVisualizer();
	}
}
