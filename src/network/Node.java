/**
*Class:             Node.java
*Project:           Node Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    17/01/2016                                              
*Version:           1.1.2                                                      
*                                                                                   
*Purpose:           Has links all incoming and outgoing nodes.
*					Stores a single String.
* 
*Update Log:		v1.1.2
*						- method added to change value after instantiation
*					v1.1.1
*						- print statements changed so toString() prints simple, toStringDetails()
*						  prints full
*					v1.1.0
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
	private final int id;
	
	
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
	
	
	//change the value
	public void setValue(String value) throws NetworkException
	{
		if (value != null)
		{
			if (value.length() > 0)
			{
				this.value = value;
				return;
			}
		}
		throw new NetworkException("Invalid Value", "Node Error");
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
	
	
	//print a detailed string representation of this node
	public String toStringDetails()
	{
		String string = "ID:" + id + "   || '" + value + "' inlinks from node(s) <" ;
		
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
	
	
	@Override
	//print basic representation of this node
	public String toString()
	{
		return ("Node " + id + ", [" + value +"]");
	}
	
	
	/*
	public static void main(String[] args) 
	{
		Node node = new Node("Hello World", 0);
		try 
		{
			node.link(node);
		}
		catch (Exception e)
		{
			System.out.println("HELP LINKING");
		}
		System.out.println(node.toString());
		try
		{
			node.delink(node);
		}
		catch (Exception e)
		{
			System.out.println("HELP DELINKING");
		}
		System.out.println(node.toString());
	}
	*/
}










