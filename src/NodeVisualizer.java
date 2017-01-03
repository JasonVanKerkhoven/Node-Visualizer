/**
*Class:             NodeVisualizer.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    24/12/2016                                              
*Version:           0.1.0                                                      
*                                                                                   
*Purpose:           Main class and logic for Node Visualizer project.
*					Add nodes, link them, and view.
* 
* 
*Update Log:		v0.1.0
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
	
	
	@Override
	//user input entered
	public void actionPerformed(ActionEvent ae) 
	{
		//get and parse input
		String input[] = ui.getParsedInput();
		
		//handle input based on number of words entered
		if (input != null)
		{
			for(int i=0; i<input.length; i++)
			{
				System.out.println(i + ": <" + input[i] + ">");
			}
			System.out.println();
			
			
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
				
					//clear all nodes
					else if (input[0].equals("clear"))
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
					//add node
					if (input[0].equals("add"))
					{
						//create node with value equal to 2nd param
						try	
						{
							nodes.add(input[1]);
						}
						catch (NetworkException e)
						{
							ui.printError(e.msgTitle, e.getMessage());
							if(e.criticalFlag)
							{
								System.exit(0);
							}
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
