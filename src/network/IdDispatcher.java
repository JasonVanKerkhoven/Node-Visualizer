/**
*Class:             IdDispatcher.java
*Project:           Node Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    22/01/2017                                              
*Version:           1.2.1                                                      
*                                                                                   
*Purpose:           Gives the next valid ID address up to 2^32-1.
*					If you exceed the limit it will throw an exception and god help you.
* 
*Update Log:		v1.2.1
*						- toString method added
*					v1.2.0
*						- objects can now be converted to .json --> toJSON(...)
*						- objects can now be built from .json ----> fromJSON(...)
*						- locally declared class moved to io.json.ToJSON
*					v1.1.0
*						- method added for saving instance information as .json
*						- helper class declared in toJSON() method
*					v1.0.1
*						- bug patch for retire(int i) method
*					v1.0.0
*						- reset added
*						- ID checkout logic added
*						- ID checkout logic added
*/
package network;

//import packages
import io.json.*;

//import external libraries
import java.util.LinkedList;
import java.io.IOException;


public class IdDispatcher implements ToJSONFile
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
	public int getNextID() throws NetworkException
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
			throw new NetworkException("No avalible Node ID\nProgram terminating...", "CRITICAL ERROR", true);
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
	
	
	@Override
	//return as a String
	public String toString()
	{
		String string = "newId: " + newId + ", full: " + full + ", retired: {";
		
		boolean first = true;
		for (int i : retired)
		{
			if(first) 
			{
				first=false;
			}
			else 
			{
				string += ", ";
			}
			
			string += i;
		}
		string += "}";
				
		return string;
	}


	@Override
	//return object in .json format
	public JsonFile toJSON(String baseOffset) 
	{
		//Set up controller and prime data block
		JsonFile file = new JsonFile(baseOffset);
		file.newBlock();
		
		//save newId
		file.addField("newId", newId);
		
		//save full
		file.addField("full", full);
		
		//save block, list of retired IDs
		file.addField("retired", "");
		file.newBlock();
		for(int value : retired)
		{
			file.add(value + "\n");
		}
		file.endBlock();
		
		//end prime data block and return
		file.endBlock();
		return file;
	}


	@Override
	//return a new instance of this object in the from a .json file
	public void fromJSON(String JsonFile) throws JsonException 
	{
		try
		{
			//declaring method variables
			int blockCount = 0;
			int line = 0;
			
			//make sure this instance is in its default state
			this.reset();
			
			//split at newlines, remove all tabs, remove spaces
			JsonFile = JsonFile.replaceAll("\t", "");
			JsonFile = JsonFile.replaceAll(" ", "");
			String[] fileLine = JsonFile.split("\n");
			
			//make sure there is a starting block
			if (!fileLine[line].equals("{"))
			{
				throw new JsonException("No starting block", JsonException.ERR_FORMAT);
			}
			line++;
			
			
			//check newId
			if ( fileLine[line].contains("\"newId\":"))
			{
				//check there is a value after the colon
				String[] newId = fileLine[line].split(":");
				if (newId.length == 2)
				{
					//save value to newId field
					try
					{
						this.newId = Integer.parseInt(newId[1]);
					}
					catch (NumberFormatException e)
					{
						throw new JsonException("newId value not a valid int", JsonException.ERR_BAD_VALUE);
					}
				}
				else
				{
					throw new JsonException("newId value not found", JsonException.ERR_BAD_VALUE);
				}
			}
			else
			{
				throw new JsonException("newId field not found", JsonException.ERR_BAD_FIELD);
			}
			line++;
			
			
			//check full
			if ( fileLine[line].contains("\"full\":") )
			{
				//confirm there is a value after the colon
				String[] full = fileLine[line].split(":");
				if (full.length == 2)
				{
					//save value to full field
					if(full[1].equals("true"))
					{
						this.full = true;
					}
					else if (full[1].equals("false"))
					{
						this.full = false;
					}
					else
					{
						throw new JsonException("full value invalid - must be boolean", JsonException.ERR_BAD_VALUE);
					}
				}
				else
				{
					throw new JsonException("full value not found", JsonException.ERR_BAD_VALUE);
				}
			}
			else
			{
				throw new JsonException("full field not found", JsonException.ERR_BAD_FIELD);
			}
			line++;
			
			
			//check retired block start
			if(!fileLine[line].contains("\"retired\":{"))
			{
				throw new JsonException("retired field not found", JsonException.ERR_BAD_FIELD);
			}
			line++;
			blockCount++;
			
			
			//get data from retired block
			while ( !fileLine[line].contains("}"))
			{
				//add to list of retired IDs
				try
				{
					int toRetire = Integer.parseInt(fileLine[line]);
					this.retireID(toRetire);
				}
				catch (NumberFormatException e)
				{
					throw new JsonException("retired value invalid - must be integer", JsonException.ERR_BAD_VALUE);
				}
				line++;
			}
			blockCount--;
			line++;
			
			//check for termination of prime block
			if (!fileLine[line].equals("}"))
			{
				throw new JsonException("No end block", JsonException.ERR_FORMAT);
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			throw new JsonException("Unknown Error", JsonException.ERR_COULD_NOT_BUILD);
		}
	}


	@Override
	public void fromJSON(byte[] rawBytes) throws JsonException 
	{
		/* TODO there must be a better way to do this
		 * using char array so as i dont need to make a new string each char add (each string is immutable)
		 */

		//cast each byte as a char
		char[] brokenString = new char[rawBytes.length];
		for(int i=0; i < rawBytes.length; i++)
		{
			brokenString[i] = (char)rawBytes[i];
		}
		
		//generate object from json file, now in String form
		fromJSON(new String(brokenString));
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






