/**
*Class:             IdDispatcher.java
*Project:           Node Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    28/12/2016                                              
*Version:           1.0.0                                                      
*                                                                                   
*Purpose:           Gives the next valid ID address up to 2^32-1.
*					If you exceed the limit it will throw an exception and god help you.
* 
*Update Log:		v1.0.1
*						- bug patch for retire(int i) method
*					v1.0.0
*						- reset added
*						- ID checkout logic added
*						- ID checkout logic added
*/
package graph;


//import external libraries
import java.util.LinkedList;


public class IdDispatcher 
{
	//declaring local instance variables
	private LinkedList<Integer> retired;
	private int newId;
	private boolean full;
	
	
	//generic constructor
	public IdDispatcher()
	{
		retired = new LinkedList<>();
		newId = 0;
		full = false;
	}
	
	
	//get next available ID
	public int getNextID() throws GraphException
	{
		//there is 1 or more retired IDs available, use the lowest value among them
		if (retired.size() > 0)
		{
			return (int)retired.pop();
		}
		//no retired IDs available, create new ID
		else if (!full)
		{
			int ret = newId;
			newId++;
			if(newId == Integer.MAX_VALUE)
			{
				full = true;
			}
			return ret;
		}
		else
		{
			throw new GraphException("No avalible Node ID\nProgram terminating...", "CRITICAL ERROR", true);
		}
	}
	
	
	//exactly what it says on the tin
	public void reset()
	{
		retired.clear();
		full = false;
		newId = 0;
	}
	
	
	//retire an ID
	public void retireID(int toAdd)
	{
		if(retired.size() == 0)
		{
			retired.addFirst((Integer)toAdd);
		}
		else
		{
			//iterate through list to determine where to put it 
			for(int i=0; i<retired.size(); i++)
			{
				//new int should come before int in list
				if(toAdd < retired.get(i).intValue() )
				{
					retired.add(i, toAdd);
					return;
				}
			}
			//add to end
			retired.addLast((Integer)toAdd);
		}
	}
	
	
	/*
	//used for testing
	public static void main(String[] args)
	{
		//init
		IdDispatcher dispatcher = new IdDispatcher();
		
		//get 5 IDs
		System.out.println("Getting 5 IDs...");
		try
		{
			for(int i=0; i<5; i++)
			{
				System.out.println("ID: " + dispatcher.getNextID());
			}
		}
		catch (IDException ide){}
		System.out.println();
		
		//retire ID values 0, 3
		System.out.println("Retirering IDs: 0, 3...\n");
		dispatcher.retireID(0);
		dispatcher.retireID(3);
		
		//get 5 new IDs
		System.out.println("Getting 5 IDs...");
		try
		{
			for(int i=0; i<5; i++)
			{
				System.out.println("ID: " + dispatcher.getNextID());
			}
		}
		catch (IDException ide){}
		System.out.println();
		
		//reset and get 10 new IDs
		dispatcher.reset();
		System.out.println("Reseting Dispatcher...\nGetting 10 IDs...");
		try
		{
			for(int i=0; i<10; i++)
			{
				System.out.println("ID: " + dispatcher.getNextID());
			}
		}
		catch (IDException ide){}
		System.out.println();
	}
	*/
}






