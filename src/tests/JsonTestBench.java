/**
*Class:             JsonTestBench.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    19/01/2017                                              
*Version:           1.0.0                                                     
*                                                                                   
*Purpose:           Test saving and loading Network and objects aggregated by Network to
*					disk as .json files
* 
* 
*Update Log:		v1.0.0
*						- null
*/
package tests;


//import stuff
import java.util.ArrayList;

//imports from local packages
import io.*;
import io.json.JsonFile;
import network.*;


public class JsonTestBench 
{

	
	public static void println(String printable)
	{
		System.out.println(printable);
	}
	
	
	public static void print(String printable)
	{
		System.out.print(printable);
	}
	
	
	public static void printJson(String name, JsonFile json)
	{
		System.out.println("\n------------ " + name + ".json ------------\n" + json.toString() + "\n-----------------------------\n\n");
	}
	
	
	public static void main(String[] args)
	{
		//working instance variables
		String expected;
		IdDispatcher dispatch = new IdDispatcher();
		Network network = new Network();
		final String DIV = "\n==================================================================================================================";
		
		
		//test to .json for IdDispatcher
		////////////////////////////////////////////////////////////////////////////////////////////
		println("Initializing idDispatch value..." + DIV);
		try
		{
			//checkout ids 0 through 9
			print("IDs checked out: ");
			for(int i=0; i<10; i++)
			{
				print(dispatch.getNextID() + ", ");
			}
			println("...");
			
			//check in ids 4 through 9
			print("IDs checked in:  ");
			for(int i=4; i<10; i++)
			{
				dispatch.retireID(i);
				print(i + ", ");
			}
			println("...");
		}
		catch (NetworkException e)
		{
			print("ERROR");
			System.exit(0);
		}
		
		//convert to .json and print
		printJson("idDispatcher", dispatch.toJSON(""));
		
		
		
		//test to .json for IdDispatcher
		////////////////////////////////////////////////////////////////////////////////////////////
		println("Initializing idDispatch value..." + DIV);
		dispatch.reset();
		try
		{
			//checkout ids 0 through 9
			print("IDs checked out: ");
			for(int i=0; i<10; i++)
			{
				print(dispatch.getNextID() + ", ");
			}
			println("...");
			
			//check in ids 4 through 9
			print("IDs checked in:  ");
			for(int i=4; i<10; i++)
			{
				dispatch.retireID(i);
				print(i + ", ");
			}
			println("...");
		}
		catch (NetworkException e)
		{
			print("ERROR");
			System.exit(0);
		}
		
		//convert to .json and print
		print("\n{\nvar : thing\nvar thing\nvar : ");
		println(dispatch.toJSON("\t").toString());
		println("}");
		
		
		//test to .json for IdDispatcher
		////////////////////////////////////////////////////////////////////////////////////////////
		println("Initializing Network values..." + DIV);
		try
		{
			//add nodes
			network.add("the first node");
			network.add("stage 1");
			network.add("stage 2");
			network.add("end node");
			for(int i=0; i<6; i++)
			{
				network.add("garbage");
			}
			
			//remove garbage nodes
			for(int i=4; i<10; i++)
			{
				network.remove(i);
			}
			
			//link
			network.link(0, 1);
			network.link(0, 2);
			network.link(1, 3);
			network.link(2, 3);
			network.link(3, 0);
		}
		catch (NetworkException e)
		{
			print("ERROR");
			System.exit(0);
		}
		
		printJson("network", network.toJSON(null));
	}

}
