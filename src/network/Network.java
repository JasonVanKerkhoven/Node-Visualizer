/**
*Class:             Network.java
*Project:           Node-Visualizer
*Author:            Jason Van Kerkhoven                                             
*Date of Update:    02/01/2017                                              
*Version:           0.4.0                                                      
*                                                                                   
*Purpose:           Built due to complexities with delinking nodes.
*					Internally support linking n amount of nodes to a node.
*					Additionally supports delinking a node from ALL nodes.
* 
* 
*Update Log:		v0.4.0
*						- renamed to Network.java
*						- toString() rewritten
*					v0.3.0
*						- indirect add, remove, link, and delink modified to use 
*						  a common intermediate method
*						- direct add(...) method removed
*						- toString() method fixed
*						- exception messages that are actually human-readable added
*					v0.2.0
*						- toString() method added
*						- clear() method added
*					v0.1.0
*						- addNode(...) method logic added
*						- removeNode(...) method logic added
*						- linkNode(...) method logic added
*						- delinkNode(...) method logic added
* 
*/
package network;


import java.util.Collection;
//import external libraries
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;


public class Network 
{
	//declaring local instance variables
	IdDispatcher dispatch;
	HashMap<Integer, Node> idMap;
	
	
	//generic constructor
	public Network()
	{
		dispatch = new IdDispatcher();
		idMap = new HashMap<Integer, Node>();
	}
	
	
	//clear graph
	public void clear()
	{
		dispatch.reset();
		idMap.clear();
	}
	
	
	//create new node from a value, add to graph
	public void add(String nodeValue) throws NetworkException
	{
		int id = dispatch.getNextID();
		idMap.put(id, new Node(nodeValue, id));
	}
	
	
	//find and remove a node from the graph based on ID
	public void remove(int id) throws NetworkException
	{
		removeNode(idMap.get(id));
		return;
	}
	
	
	//remove a node from the graph, delink it from all other nodes
	public void removeNode(Node node) throws NetworkException
	{

	}
	
	
	//find and link two nodes, based on id
	public void link(int start, int end) throws NetworkException
	{
		linkNode(idMap.get(start), idMap.get(end));
	}
	
	
	//link a node in graph to another pre-existing node
	public void linkNode(Node start, Node end) throws NetworkException
	{

	}
	
	
	//find and delink two nodes, based on id
	public void delink(int start, int end) throws NetworkException
	{
		delinkNode(idMap.get(start), idMap.get(end));
	}
	
	
	//delink a single path
	public void delinkNode(Node start, Node end) throws NetworkException
	{

	}
	
	
	//return string representation of the hashmap used
	//(used almost 100% for debugging)
	public String toStringHashmap()
	{
		String string = "";
		Set<Integer> keys = idMap.keySet();
		
		if (keys.size() > 0)
		{
			for (int i : keys)
			{
				string += "Key: " + i + " || Value: <" + idMap.get(i).toStringSimple() + ">\n";
			}
		}
		else
		{
			string = "Empty\n";
		}
		
		return string;
	}
	
	
	@Override
	//returns a string of all nodes in network
	public String toString()
	{
		Collection<Node> values = idMap.values();
		String string = "";
		if(values.size() > 0)
		{
			for(Node node : values)
			{
				string += node.toString() + "\n";
			}
		}
		else
		{
			string = "No nodes\n";
		}
		return string;
	}
}
