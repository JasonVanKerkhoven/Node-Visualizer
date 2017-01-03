/**
*Class:             Node.java
*Project:           Node Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    02/01/2016                                              
*Version:           1.1.0                                                      
*                                                                                   
*Purpose:           Has links all incoming and outgoing nodes.
*					Stores a single String.
* 
*Update Log:		v1.1.0
*						- restructured to be doubly linked
*						- contains pointers to all incoming nodes now
*						- link and delink method now modify the local node and the to-be-linked node
*						- toString updated
*						- some accessors added
*					v1.0.1
*						- toString function added for printing
*					v1.0.0
*						- basic functionality
*/
package network;

//import external libraries
import java.util.LinkedList;


public class Node 
{
	//declaring local instance variables
	private LinkedList<Node> outLinks;
	private LinkedList<Node> inLinks;
	private String value;
	private int id;
	
	
	//generic constructor
	public Node(String value, int id)
	{
		this.value = value;
		this.id = id;
		outLinks = new LinkedList<>();
		inLinks = new LinkedList<>();
	}
	
	
	//generic accessors
	public String getValue()
	{
		return value;
	}
	public int getId()
	{
		return id;
	}
	public LinkedList<Node> getOutLinks()
	{
		return outLinks;
	}
	public LinkedList<Node> getInLinks()
	{
		return inLinks;
	}
	
	
	//link a node to this node
	public void link(Node toAdd) throws NetworkException
	{
		if (outLinks.contains(toAdd))
		{
			throw new NetworkException("Node already linked", "Linking Error");
		}
		else
		{
			outLinks.add(toAdd);
			toAdd.getInLinks().add(this);
		}
	}
	
	
	//delink a node from this node
	public void delink(Node toRemove) throws NetworkException
	{
		System.out.println("CALLED");
		boolean nodeFound = outLinks.remove(toRemove);
		if(nodeFound)
		{
			toRemove.getInLinks().remove(this);
		}
		else
		{
			throw new NetworkException("Node not linked", "Linking Error");
		}
	}
	
	
	//print a string representation of this node
	@Override
	public String toString()
	{
		String string = "ID:" + id + "   || Node containing '" + value + "' inlinks from node(s) <" ;
		
		for(int i=0; i < inLinks.size(); i++)
		{
			if(i!=0)
			{
				string += ", ";
			}
			string += inLinks.get(i).getId();
		}
		
		string += "> outlinks to node(s) <";
		for(int i=0; i < outLinks.size(); i++)
		{
			if(i!=0)
			{
				string += ", ";
			}
			string += outLinks.get(i).getId();
		}
		
		return (string + ">");
	}
	
	
	//print basic representation of this node
	public String toStringSimple()
	{
		return ("Node containing: '" + value +"'");
	}
}










