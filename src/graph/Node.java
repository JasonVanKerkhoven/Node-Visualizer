/**
*Class:             Node.java
*Project:           Node Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    24/12/2016                                              
*Version:           1.1.0                                                      
*                                                                                   
*Purpose:           Has links to other nodes.
*					Stores a single String.
* 
*Update Log:		v1.1.0
*						- toString function added for printing
*					v1.0.0
*						- basic functionality
*/
package graph;

//import external libraries
import java.util.LinkedList;


public class Node 
{
	//declaring local instance variables
	LinkedList<Node> outLinks;
	String value;
	int id;
	
	//generic constructor
	public Node(String value, int id)
	{
		this.value = value;
		this.id = id;
		outLinks = new LinkedList<>();
	}
	
	
	//generic accessors
	public int getId()
	{
		return id;
	}
	public String getValue()
	{
		return value;
	}
	public Node[] getLinks()
	{
		return outLinks.toArray(new Node[0]);
	}
	
	
	//link a node to this node
	public void link(Node toAdd) throws GraphException
	{
		if (outLinks.contains(toAdd))
		{
			throw new GraphException("Node already linked", "Linking Error");
		}
		else
		{
			outLinks.add(toAdd);
		}
	}
	
	
	//delink a node from this node
	public void delink(Node toRemove) throws GraphException
	{
		boolean nodeFound = outLinks.remove(toRemove);
		if(!nodeFound)
		{
			throw new GraphException("Node not linked", "Linking Error");
		}
	}
	
	
	//print a string representation of this node
	@Override
	public String toString()
	{
		String string = "Node " + id + " containing '" + value + "' links to node(s) <" ;
		
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
	public String toStringNoLinks()
	{
		return ("Node" + id + ", containing: " + value);
	}
}










